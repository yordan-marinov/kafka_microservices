package com.yordanm.event_producer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.services.LibraryEventProducerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/v1/libraryEvent")
public class LibraryProducerController {

    private LibraryEventProducerService producerService;

    @PostMapping
    public ResponseEntity<LibraryEvent> postLibraryEvents(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        producerService.produce(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
