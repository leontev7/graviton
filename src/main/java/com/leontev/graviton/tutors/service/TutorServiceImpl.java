package com.leontev.graviton.service;

import com.leontev.graviton.events.TelegramMessageEvent;
import com.leontev.graviton.model.Student;
import com.leontev.graviton.model.Tutor;
import com.leontev.graviton.model.TutorTrialLessonInterval;
import com.leontev.graviton.pojo.TutorPojo;
import com.leontev.graviton.repo.LessonRepository;
import com.leontev.graviton.repo.StudentRepository;
import com.leontev.graviton.repo.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;
    private final LessonRepository lessonRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Tutor getTutor(String id) {
        return repository.findByTelegramId(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Tutor> getTutors() {
        return repository.findAll();
    }

    @Override
    public Tutor updateTutor(TutorPojo tutorPojo) {
        Tutor originalTutor = repository.findByTelegramId(tutorPojo.getTelegramId()).orElseThrow(EntityNotFoundException::new);

        originalTutor.setFirstName(tutorPojo.getFirstName());
        originalTutor.setLastName(tutorPojo.getLastName());
        originalTutor.setDescription(tutorPojo.getDescription());
        originalTutor.setShared(tutorPojo.isShared());

        // Set pricelist
        Map<String, Integer> priceList = new HashMap<>();
        for (var item: tutorPojo.getPriceList()) {
            priceList.put(item.getTitle(), item.getPrice());
        }
        originalTutor.setPriceList(priceList);

        // Set timeIntervals
        List<TutorTrialLessonInterval> timeIntervals = new ArrayList<>();
        for (var i: tutorPojo.getTrialLessonsTimeIntervals()) {
            TutorTrialLessonInterval interval = new TutorTrialLessonInterval();
            interval.setDayOfWeek(i.getDayOfWeek());
            interval.setHourStart(i.getHourStart());
            interval.setHourEnd(i.getHourEnd());
            timeIntervals.add(interval);
        }
        originalTutor.setTrialLessonsTimeIntervals(timeIntervals);

        return save(originalTutor);
    }

    @Override
    public List<LocalDateTime> getTimesForTrialLesson(String tutorId) {
        Tutor tutor = repository.findByTelegramId(tutorId).orElseThrow(EntityNotFoundException::new);
        LocalDateTime now = LocalDateTime.now();

        List<LocalDateTime> result = new ArrayList<>();

        LocalDate currentDate = now.toLocalDate();

        // Ищем даты на месяц
        LocalDate limitDate = currentDate.plusMonths(1);

        while (result.size() < 4 && !currentDate.isAfter(limitDate)) {
            for (var ti : tutor.getTrialLessonsTimeIntervals()) {
                LocalDate closestDate = getClosestDayByDayOfWeek(currentDate, ti.getDayOfWeek());
                LocalDateTime proposedLessonStartTime = LocalDateTime.of(closestDate, LocalTime.of(ti.getHourStart(), 0, 0));
                LocalDateTime proposedLessonEndTime = proposedLessonStartTime.plus(1, ChronoUnit.HOURS);

                // Check if the proposed time does not conflict with existing lessons
                var conflictingLessons = lessonRepository.findByTutorAndDateTimeBetween(tutor, proposedLessonStartTime, proposedLessonEndTime.minusMinutes(1));

                if (conflictingLessons.isEmpty() && proposedLessonStartTime.isAfter(now)) {
                    result.add(proposedLessonStartTime);
                }

                if (result.size() >= 4) {
                    break;
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        result.sort(Comparator.naturalOrder());

        return result;
    }


    private LocalDate getClosestDayByDayOfWeek(LocalDate startDate, int dayOfWeek) {
        while(startDate.getDayOfWeek().getValue() != dayOfWeek) {
            startDate = startDate.plusDays(1);
        }

        return startDate;
    }


    @Transactional
    private Tutor save(Tutor tutor) {
        return repository.save(tutor);
    }
}
