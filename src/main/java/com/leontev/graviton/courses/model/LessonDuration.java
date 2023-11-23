package com.leontev.graviton.lessons.model;

import com.leontev.graviton.exceptions.EnumConstantNotFoundException;
import com.leontev.graviton.tasks.model.TaskComplexity;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LessonDuration {
    MINUTES_60(60),
    MINUTES_90(90),
    MINUTES_120(120);

    private final int minutes;

    LessonDuration(int minutes) {
        this.minutes = minutes;
    }

    public static LessonDuration getDuration(int duration) {
        return Arrays.stream(LessonDuration.values())
                .filter(ld -> ld.ordinal() == duration).findFirst().orElseThrow(() -> new EnumConstantNotFoundException("value should be from 0 to " + (LessonDuration.values().length - 1)));
    }
}
