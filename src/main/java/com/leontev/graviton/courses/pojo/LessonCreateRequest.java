package com.leontev.graviton.lessons.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LessonCreateRequest {
    private Long courseId;
    private String tutorTelegramId;
    private List<String> studentTelegramIds;
    private boolean tryLesson;
    private String dateTime;
    private int duration;
}
