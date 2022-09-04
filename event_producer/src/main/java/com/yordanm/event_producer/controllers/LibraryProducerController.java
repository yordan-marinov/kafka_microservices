package com.yordanm.event_producer.controllers;

import com.yordanm.event_producer.domain.LibraryEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/libraryEvent")
public class LibraryProducerController {

    @PostMapping
    public ResponseEntity<LibraryEvent> postLibraryEvents(@RequestBody LibraryEvent libraryEvent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
