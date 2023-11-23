package com.leontev.graviton.service;

import com.leontev.graviton.model.Student;
import com.leontev.graviton.model.Tutor;
import com.leontev.graviton.pojo.TutorPojo;

import java.time.LocalDateTime;
import java.util.List;

public interface TutorService {
    Tutor getTutor(String telegramId);
    List<Tutor> getTutors();
    Tutor updateTutor(TutorPojo tutorPojo);
    List<LocalDateTime> getTimesForTrialLesson(String tutorId);
}
