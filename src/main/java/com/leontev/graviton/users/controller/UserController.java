package com.leontev.graviton.controller;

import com.leontev.graviton.pojo.UserPojo;
import com.leontev.graviton.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/user")
    public ResponseEntity<UserPojo> getUser(@RequestBody String telegramId) {
        return ResponseEntity.ok(new UserPojo(service.getUser(telegramId)));
    }

    @PostMapping("/user/toggle-theme")
    public ResponseEntity<UserPojo> toggleTheme(@RequestBody String telegramId) {
        log.info(telegramId);
        return ResponseEntity.ok(new UserPojo(service.toggleTheme(telegramId)));
    }
}
