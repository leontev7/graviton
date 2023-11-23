package com.leontev.graviton.repo;

import com.leontev.graviton.model.TutorInviteLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorInviteLinkRepository extends JpaRepository<TutorInviteLink, Long> {
    Optional<TutorInviteLink> findByLink(String link);
}
