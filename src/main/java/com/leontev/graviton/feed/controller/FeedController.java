package com.leontev.graviton.feed;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class FeedController {

    private final FeedService service;

    @Operation(summary = "Get feed for user by user telegram id")
    @GetMapping("/feed")
    public ResponseEntity<Feed> getFeed(@RequestParam String telegramId) {
        return ResponseEntity.ok(service.getFeed(telegramId));
    }

}
