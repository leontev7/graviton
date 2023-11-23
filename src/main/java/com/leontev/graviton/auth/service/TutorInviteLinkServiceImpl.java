package com.leontev.graviton.service;

import com.leontev.graviton.model.TutorInviteLink;
import com.leontev.graviton.repo.TutorInviteLinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TutorInviteLinkServiceImpl implements TutorInviteLinkService {

    private final TutorInviteLinkRepository repository;

    @Override
    @Transactional
    public TutorInviteLink generateLink() {
        TutorInviteLink link = new TutorInviteLink();
        link.setLink(UUID.randomUUID().toString());
        return repository.save(link);
    }

    @Scheduled(fixedRate = 300000)
    public void scheduleFixedRateTask() {
        repository.findAll().forEach(tl -> {
            if (tl.getCreationTime().plus(1, ChronoUnit.HOURS)
                    .isAfter(LocalDateTime.now()))
                repository.delete(tl);
        });

        log.info("all expired tutor links has been deleted");
    }
}
