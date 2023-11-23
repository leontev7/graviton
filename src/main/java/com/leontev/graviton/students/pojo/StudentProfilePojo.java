package com.leontev.graviton.students.pojo;

import com.leontev.graviton.bank.pojo.BankAccountPojo;
import com.leontev.graviton.courses.model.Course;
import com.leontev.graviton.courses.model.Lesson;
import com.leontev.graviton.courses.pojo.CoursePojo;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.students.service.StudentLevelService;
import com.leontev.graviton.users.pojo.UserPojo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentPojo extends UserPojo {
    private int points;
    private int grade;
    private String level;
    private BankAccountPojo bankAccount;
    private List<StudentCoursePojo> courses = new ArrayList<>();
    private List<ClosestLessonPojo> closestLessons;
    private List<UserPojo> referralStudents = new ArrayList<>();

    public StudentPojo(Student student) {
        super(student);
        this.points = student.getPoints();
        this.grade = student.getGrade();
        this.level = StudentLevelService.getLevel(student.getPoints());
        if (student.getBankAccount() != null)
            this.bankAccount = new BankAccountPojo(student.getBankAccount());
        if (student.getInvitedUsers() != null && student.getInvitedUsers().size() > 0) {
            student.getInvitedUsers().forEach(s -> this.referralStudents.add(new UserPojo(s)));
        }

        this.courses = student.getCourses().stream().map(StudentCoursePojo::new).toList();

        List<Lesson> lessons = new ArrayList<>();
        student.getCourses().forEach(c -> c.getModules().forEach(m -> lessons.addAll(m.getLessons())));
        this.closestLessons = lessons.stream().map(ClosestLessonPojo::new).toList();
    }

    @Data
    private static class StudentCoursePojo {
        private long id;
        private String title;

        public StudentCoursePojo(Course course) {
            this.id = course.getId();
            this.title = course.getTitle();
        }
    }

    @Data
    private static class ClosestLessonPojo {
        private long id;
        private String title;

        public ClosestLessonPojo(Lesson lesson) {
            this.id = lesson.getId();
            this.title = lesson.getTitle();
        }
    }
}
