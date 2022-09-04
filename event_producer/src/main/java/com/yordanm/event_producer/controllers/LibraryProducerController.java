package com.yordanm.event_producer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.services.LibraryEventProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/libraryEvent")
@Slf4j
public class LibraryProducerController {

    private final LibraryEventProducerService producerService;

    @Autowired
    public LibraryProducerController(LibraryEventProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<LibraryEvent> postLibraryEvents(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        log.info(">>>>>> Before sending Event <<<<<<<<");
        producerService.produce(libraryEvent);
        log.info(">>>>>> After sending Event <<<<<<<<");

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
