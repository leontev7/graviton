package com.leontev.graviton.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class FilesController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {

    }
}
