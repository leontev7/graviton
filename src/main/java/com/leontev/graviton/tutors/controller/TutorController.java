package com.leontev.graviton.controller;

import com.leontev.graviton.pojo.TutorInviteLinkPojo;
import com.leontev.graviton.pojo.TutorPojo;
import com.leontev.graviton.service.TutorInviteLinkService;
import com.leontev.graviton.service.TutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class TutorController {

    private final TutorService service;
    private final TutorInviteLinkService linkService;

    @PostMapping("/tutor")
    public ResponseEntity<TutorPojo> getTutor(@RequestBody String telegramId) {
        return ResponseEntity.ok(new TutorPojo(service.getTutor(telegramId)));
    }

    @PostMapping("/update-tutor")
    public ResponseEntity<TutorPojo> updateTutor(@RequestBody TutorPojo tutorPojo) {
        return ResponseEntity.ok(new TutorPojo(service.updateTutor(tutorPojo)));
    }

    @PostMapping("/tutors")
    public ResponseEntity<List<TutorPojo>> getTutors(@RequestBody String telegramId) {
        return ResponseEntity.ok(service.getTutors().stream().map(TutorPojo::new).collect(Collectors.toList()));
    }

    @PostMapping("/tutor/invite-link")
    public ResponseEntity<TutorInviteLinkPojo> generateLink(@RequestBody String telegramId) {
        return ResponseEntity.ok(new TutorInviteLinkPojo(linkService.generateLink()));
    }

    @PostMapping("/tutor/lesson-times")
    public ResponseEntity<List<LocalDateTime>> getTutorTimeForLesson(@RequestBody String tutorTelegramId) {
        return ResponseEntity.ok(service.getTimesForTrialLesson(tutorTelegramId));
    }
}
