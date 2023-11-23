package com.leontev.graviton.lessons.pojo;

import com.leontev.graviton.lessons.model.Lesson;
import com.leontev.graviton.tasks.pojo.HomeworkPojo;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.tutors.pojo.TutorPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LessonPojo {

    private long id;
    @Schema(description = "optional, if null new course will be created")
    private long courseId;
    private String title;
    private String dateTime;
    @Schema(description = "value from 0 to 2")
    private int duration;
    private TutorLessonPojo tutor;
    @Schema(description = "value from 0 to 3")
    private int status;
    @Schema(description = "value from 0 to 7")
    private int cancellationReason;
    private HomeworkPojo homework;
    private BigDecimal price;
    private BigDecimal perStudentPrice;
    private BigDecimal tutorEarnings;

    public LessonPojo(Lesson lesson) {
        this.id = lesson.getId();
        this.courseId = lesson.getCourse().getId();
        this.title = lesson.getTitle();
        this.dateTime = lesson.getDateTime().toString();
        this.duration = lesson.getDuration().getMinutes();
        this.status = lesson.getStatus().ordinal();
        this.tutor = new TutorLessonPojo(lesson.getTutor());
        this.homework = new HomeworkPojo(lesson.getHomework());
        this.cancellationReason = lesson.getCancellationReason().ordinal();
        this.price = lesson.getPrice();
        this.perStudentPrice = lesson.getPerStudentPrice();
        this.tutorEarnings = lesson.getTutorEarnings();
    }

    @Data
    public static class TutorLessonPojo {
        private long id;
        private String telegramId;
        private String firstName;
        private String lastName;

        public TutorLessonPojo(Tutor tutor) {
            this.id = tutor.getId();
            this.telegramId = tutor.getTelegramId();
            this.firstName = tutor.getFirstName();
            this.lastName = tutor.getLastName();
        }
    }
}
