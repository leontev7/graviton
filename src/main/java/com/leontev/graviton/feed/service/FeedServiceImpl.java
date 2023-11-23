package com.leontev.graviton.feed;

import com.leontev.graviton.feed.FeedService;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.students.service.StudentService;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.feed.Feed;
import com.leontev.graviton.tutors.pojo.TutorPojo;
import com.leontev.graviton.tutors.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final StudentService studentService;
    private final TutorService tutorService;

    @Override
    public Feed getFeed(String telegramId) {

        Feed feed = new Feed();

        try {
            Student student = studentService.getStudent(telegramId);

            // Алгоритм подбора преподавателей под конкретного ученика
            feed.setTutors(tutorService.getTutors().stream().filter(Tutor::isShared).map(TutorPojo::new).toList());

        } catch (EntityNotFoundException e) {

            // Если пользователь не авторизован или не ученик

            feed.setTutors(tutorService.getTutors().stream().filter(Tutor::isShared).map(TutorPojo::new).toList());

        }

        return feed;
    }
}
