package com.leontev.graviton.tutors.pojo;

import com.leontev.graviton.bank.pojo.BankAccountPojo;
import com.leontev.graviton.courses.pojo.CoursePojo;
import com.leontev.graviton.courses.pojo.LessonPojo;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.users.pojo.UserPojo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TutorPojo extends UserPojo {

    private List<String> tags = new ArrayList<>();
    private String description;
    private List<TutorPojo.TutorPriceItemPojo> priceList = new ArrayList<>();
    private List<TutorPojo.TutorTrialLessonIntervalPojo> trialLessonsTimeIntervals = new ArrayList<>();
    private List<CoursePojo> courses;
    private BankAccountPojo bankAccount;
    private List<UserPojo> students;
    private List<LessonPojo> closestLessons = new ArrayList<>();

    private boolean shared;

    public TutorPojo(Tutor tutor) {
        super(tutor);
        if (tutor.getTags() != null)
            this.tags = tutor.getTags();
        this.shared = tutor.isShared();
        this.description = tutor.getDescription();

        tutor.getPriceList().keySet()
                .forEach(t -> this.priceList
                        .add(new TutorPriceItemPojo(
                                t, tutor.getPriceList().get(t)
                        )));

        tutor.getTrialLessonsTimeIntervals()
                .forEach(ti -> this.trialLessonsTimeIntervals
                        .add(new TutorTrialLessonIntervalPojo(
                                ti.getDayOfWeek(),
                                ti.getHourStart(),
                                ti.getHourEnd()
                        )));

        this.students = tutor.getInvitedUsers().stream().map(UserPojo::new).toList();

        this.courses = tutor.getCourses().stream().map(CoursePojo::new).toList();

        this.bankAccount = new BankAccountPojo(tutor.getBankAccount());
    }

    @Data
    @AllArgsConstructor
    public static class TutorPriceItemPojo {
        private String title;
        private int price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TutorTrialLessonIntervalPojo {

        private int dayOfWeek;
        private int hourStart;
        private int hourEnd;
    }
}
