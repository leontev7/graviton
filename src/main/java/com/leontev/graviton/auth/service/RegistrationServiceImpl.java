package com.leontev.graviton.service;

import com.leontev.graviton.bank.model.BankAccount;
import com.leontev.graviton.bank.repo.BankAccountRepository;
import com.leontev.graviton.events.TelegramMessageEvent;
import com.leontev.graviton.model.*;
import com.leontev.graviton.repo.*;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.students.repo.StudentRepository;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.tutors.repo.TutorRepository;
import com.leontev.graviton.users.model.Role;
import com.leontev.graviton.users.model.User;
import com.leontev.graviton.users.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TutorInviteLinkRepository tutorInviteLinkRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User registerUser(TelegramUserPojo userPojo, String referral) {

        Optional<User> optUser = userRepository.findByTelegramId(userPojo.getId());

        if (optUser.isPresent()) {
            applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                    SendMessage.builder()
                            .chatId(optUser.get().getTelegramId())
                            .text(String.format("Привет, %s. \nДля доступа к школе перейди в личный кабинет.", optUser.get().getFirstName()))
                            .build()
            ));
            return optUser.get();
        }

        if (referral == null) {
            Student student = new Student();
            student.setTelegramId(userPojo.getId());
            student.setFirstName(userPojo.getFirstName());
            student.setLastName(userPojo.getLastName());
            student.setUsername(userPojo.getUsername());
            student.setRole(Role.ROLE_STUDENT);
            student.setPoints(0);

            BankAccount bankAccount = new BankAccount();
            bankAccount = save(bankAccount);
            student.setBankAccount(bankAccount);

            student = save(student);

            applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                    SendMessage.builder()
                            .chatId(student.getTelegramId())
                            .text(String.format(
                                    "Привет, <b>%s</b>! 🎉\n" +
                                            "Добро пожаловать в Graviton. Для начала обучения перейди в <i>личный кабинет</i>.\n",
                                    student.getFirstName()
                            ))
                            .parseMode("HTML")
                            .build()
            ));

            log.info("new student has been successfully registered");

            return save(student);
        }

        // Referral
        Optional<User> optReferralUser = userRepository.findByTelegramId(referral);

        // Если пригласил студент
        if (optReferralUser.isPresent()) {
            User referralUser = optReferralUser.get();

            if (referralUser.getRole() == Role.ROLE_STUDENT) {
                Optional<Student> optReferralStudent = studentRepository.findByTelegramId(referral);
                if (optReferralStudent.isPresent()) {
                    Student student = new Student();
                    student.setTelegramId(userPojo.getId());
                    student.setFirstName(userPojo.getFirstName());
                    student.setLastName(userPojo.getLastName());
                    student.setUsername(userPojo.getUsername());
                    student.setRole(Role.ROLE_STUDENT);
                    student.setPoints(0);

                    BankAccount bankAccount = new BankAccount();
                    bankAccount.setBalance(500);
                    bankAccount = save(bankAccount);
                    student.setBankAccount(bankAccount);

                    student = save(student);

                    Student referralStudent = optReferralStudent.get();
                    referralStudent.getInvitedStudents().add(student);
                    save(referralStudent);

                    student.setInvitedBy(referralStudent);
                    save(student);

                    applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                            SendMessage.builder()
                                    .chatId(student.getTelegramId())
                                    .text(String.format(
                                            "Привет, <b>%s</b>! 🎉\n" +
                                                    "Добро пожаловать в Graviton. Для начала обучения перейди в <i>личный кабинет</i>.\n\n" +
                                                    "🎁 Подарок для тебя: за регистрацию по приглашению твой баланс пополнен на <b>500 ₽</b>.",
                                            student.getFirstName()
                                    ))
                                    .parseMode("HTML")
                                    .build()
                    ));

                    applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                            SendMessage.builder()
                                    .chatId(referralStudent.getTelegramId())
                                    .text(String.format(
                                            "Ура! 🥳\n" +
                                                    "Твой друг <b>%s</b> присоединился к нам по твоей ссылке. Как только он оплатит первое занятие, твой баланс увеличится на <b>500 ₽</b>.",
                                            student.getFirstName()
                                    ))
                                    .parseMode("HTML")
                                    .build()
                    ));


                    log.info("new student has been successfully registered by link");

                    return save(student);
                }
            }
        }

        // Если пригласил админ
        else {
            Optional<TutorInviteLink> optInviteLink = tutorInviteLinkRepository.findByLink(referral);
            if (optInviteLink.isPresent()) {
                TutorInviteLink tutorInviteLink = optInviteLink.get();
                tutorInviteLinkRepository.delete(tutorInviteLink);

                Tutor tutor = new Tutor();
                tutor.setTelegramId(userPojo.getId());
                tutor.setFirstName(userPojo.getFirstName());
                tutor.setLastName(userPojo.getLastName());
                tutor.setUsername(userPojo.getUsername());
                tutor.setRole(Role.ROLE_TUTOR);

                BankAccount bankAccount = new BankAccount();
                bankAccount = save(bankAccount);
                tutor.setBankAccount(bankAccount);
                tutor.setBankAccount(bankAccount);

                tutor = save(tutor);

                applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                        SendMessage.builder().chatId(tutor.getTelegramId())
                                .text(String.format("Привет, %s. Вы зарегистрировались как преподаватель.", tutor.getFirstName()))
                                .build()
                ));

                applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                        SendMessage.builder().chatId("6318052907")
                                .text(String.format("Преподаватель %s зарегистрировался по ссылке", tutor.getFirstName()))
                                .build()
                ));

                log.info("new tutor has been successfully registered");

                return save(tutor);
            } else {
                applicationEventPublisher.publishEvent(new TelegramMessageEvent(
                        SendMessage.builder().chatId(userPojo.getId())
                                .text("Ссылка неверная или истек срок её действия. Обратитесь к админу.")
                                .build()
                ));
                return null;
            }
        }

        return null;
    }


    @Transactional
    private Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    private Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    @Transactional
    private BankAccount save(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }
}
