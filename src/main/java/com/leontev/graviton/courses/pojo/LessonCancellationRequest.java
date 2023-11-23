package com.leontev.graviton.lessons.pojo;

import lombok.Data;

@Data
public class LessonCancellationRequest {
    private long lessonId;
    private int cancellationReason;
}
