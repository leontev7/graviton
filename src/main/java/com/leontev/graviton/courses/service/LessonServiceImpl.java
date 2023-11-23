package com.leontev.graviton.lessons.service;

import com.leontev.graviton.bank.model.Transaction;
import com.leontev.graviton.bank.service.InvoiceService;
import com.leontev.graviton.bots.GravitonSchoolBotService;
import com.leontev.graviton.lessons.model.*;
import com.leontev.graviton.lessons.pojo.LessonCancellationRequest;
import com.leontev.graviton.lessons.pojo.LessonCreateRequest;
import com.leontev.graviton.lessons.pojo.LessonPojo;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.students.service.StudentService;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.lessons.repo.LessonRepository;
import com.leontev.graviton.tutors.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;
    private final TutorService tutorService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final InvoiceService invoiceService;
    private final GravitonSchoolBotService gravitonSchoolBotService;


    @Override
    public Lesson createLesson(LessonCreateRequest request) {

        //`TODO

        Course course;

        // Tutor
        Tutor tutor = tutorService.getTutor(request.getTutorTelegramId());

        // Students
        final List<Student> students = new ArrayList<>();
        request.getStudentTelegramIds().forEach(id -> students.add(studentService.getStudent(id)));

        // Lesson
        Lesson lesson = new Lesson();
        lesson.setTutor(tutor);

        final List<Student> studentsInLesson = new ArrayList<>();

        if (request.isTryLesson()) {
            studentsInLesson.addAll(students);
        } else {
            final BigDecimal lessonPrice = new BigDecimal(0);

            List<Transaction> transactions = new ArrayList<>();

            /*
            students.forEach(s -> {
                Transaction transaction = bankAccountService.createTrannsaction(s.getBankAccount().getId(), tutor.getBankAccount().getId(), lessonPrice);
                transactions.add(transaction);
                if (transaction.getStatus().equals(TransactionStatus.IN_PROGRESS))
                    studentsInLesson.add(s);
            });

            lesson.getInvoices().addAll(transactions);
            */
        }

        if (studentsInLesson.size() < 1)
            return null;

        lesson.getStudents().addAll(studentsInLesson);
        lesson.setStatus(LessonStatus.SCHEDULED);

        try {
            course = courseService.getCourse(request.getCourseId());
        } catch (EntityNotFoundException e) {
            course = courseService.createCourse("Course");
        }


        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getDateTime());
        // Change the time zone to UTC+3
        ZonedDateTime adjustedZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Etc/GMT-3"));
        lesson.setDateTime(adjustedZonedDateTime.toLocalDateTime());
        lesson.setDuration(LessonDuration.getDuration(request.getDuration()));
        lesson.setTitle(String.format("Занятие #%s", lesson.getId()));

        lesson = save(lesson);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'в' HH:mm", new Locale("ru"));
        String formattedDateTime = adjustedZonedDateTime.format(formatter);


        // String tutorMessage = String.format("%s, хорошие новости! Ученик %s записался на занятие с вами.\nДата и время: %s.\n\nПожалуйста, проверьте свой личный кабинет для деталей. Желаем успешного занятия!", tutor.getFirstName(), student.getFirstName(), formattedDateTime);
        // gravitonSchoolBotService.sendMessage(tutor.getTelegramId(), tutorMessage);

        students.forEach(s -> {
            String studentMessage = String.format("%s, вы успешно записались на занятие с преподавателем %s.\nДата и время: %s.\n\nДля дополнительной информации перейдите в свой личный кабинет. Удачного обучения!", s.getFirstName(), tutor.getFirstName() + " " + tutor.getLastName(), formattedDateTime);
            gravitonSchoolBotService.sendMessage(s.getTelegramId(), studentMessage);
        }
        );

        course.getLessons().add(lesson);
        courseService.saveCourse(course);

        return lesson;
    }

    @Override
    public Lesson getLesson(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Lesson> getLessons(String telegramId) {
        final List<Lesson> lessons = new ArrayList<>();

        try {
            Student student = studentService.getStudent(telegramId);
            student.getCourses().forEach(c -> lessons.addAll(c.getLessons()));
        } catch (EntityNotFoundException e) {
            Tutor tutor = tutorService.getTutor(telegramId);
            tutor.getCourses().forEach(c -> lessons.addAll(c.getLessons()));
        }

        return lessons;
    }

    @Override
    public Lesson updateLesson(LessonPojo lessonPojo) {
        Lesson lesson = getLesson(lessonPojo.getId());

        // TODO

        return save(lesson);
    }

    @Override
    public Lesson cancelLesson(LessonCancellationRequest request) {
        Lesson lesson = getLesson(request.getLessonId());
        lesson.setStatus(LessonStatus.CANCELED);
        lesson.setCancellationReason(LessonCancellationReason.getReason(request.getCancellationReason()));
        lesson.getInvoices().forEach(i -> invoiceService.cancelInvoice(i.getId()));
        return save(lesson);
    }

    // Start lessons
    @Scheduled(fixedRate = 300000)
    public void startLessons() {
        repository.findByStatus(LessonStatus.SCHEDULED).forEach(l -> {

            LocalDateTime now = LocalDateTime.now();

            if (l.getDateTime().isAfter(now)) {
                l.setStatus(LessonStatus.IN_PROGRESS);
                save(l);
            }
        });

        log.info("all scheduled lessons started");
    }

    // Complete lessons
    @Scheduled(fixedRate = 300000)
    public void completeLessons() {
        repository.findByStatus(LessonStatus.IN_PROGRESS).forEach(l -> {

            LocalDateTime now = LocalDateTime.now();

            if (l.getDateTime().plusMinutes(l.getDuration().getMinutes()).isBefore(now)) {

                l.setStatus(LessonStatus.COMPLETED);
                l.getInvoices().forEach(i -> invoiceService.pay(i.getId()));
                save(l);

                l.getStudents().forEach(s -> {
                    if (!l.getStudentRating().containsKey(s))
                        gravitonSchoolBotService.sendMessage(getLessonRatingMessage(SendMessage.builder().chatId(s.getTelegramId()).text("Занятие завершено. Оцените занятие").build(), l.getId()));
                });

                if (l.getTutorRating() == 0)
                    gravitonSchoolBotService.sendMessage(getLessonRatingMessage(SendMessage.builder().chatId(l.getTutor().getTelegramId()).text("Занятие завершено. Оцените занятие").build(), l.getId()));
            }

        });

        log.info("all in progress lessons completed");
    }

    private SendMessage getLessonRatingMessage(SendMessage sendMessage, Long lessonId) {
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        String[] emojis = {"\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE10", "\uD83D\uDE42", "\uD83D\uDE04"};

        for (int i = 0; i < emojis.length; ++i) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(emojis[i]);
            button.setCallbackData("lesson-rate_" + lessonId + "_" + i);
            keyboardRow.add(button);
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Transactional
    private Lesson save(Lesson lesson) {
        return repository.save(lesson);
    }
}
