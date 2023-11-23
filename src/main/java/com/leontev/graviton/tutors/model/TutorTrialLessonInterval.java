package com.leontev.graviton.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalTime;

@Embeddable
@Data
public class TutorTrialLessonInterval {

    private int dayOfWeek;
    private int hourStart;
    private int hourEnd;
}
