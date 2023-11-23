package com.leontev.graviton.lessons.model;

import com.leontev.graviton.exceptions.EnumConstantNotFoundException;
import com.leontev.graviton.tasks.model.TaskType;

import java.util.Arrays;

public enum LessonCancellationReason {

    BY_TUTOR,
    BY_STUDENT,
    TECHNICAL_ISSUES,
    EMERGENCY,
    ILLNESS,
    SCHEDULE_CONFLICT,
    NO_SHOW,
    OTHER;

    public static LessonCancellationReason getReason(int reason) {
        return Arrays.stream(LessonCancellationReason.values())
                .filter(r -> r.ordinal() == reason)
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotFoundException("value should be from 0 to " + (LessonCancellationReason.values().length - 1)));
    }
}
