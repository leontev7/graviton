package com.leontev.graviton.feed;

import com.leontev.graviton.tutors.pojo.TutorPojo;
import lombok.Data;

import java.util.List;

@Data
public class Feed {
    private List<TutorPojo> tutors;
}
