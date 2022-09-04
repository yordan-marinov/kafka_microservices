package com.yordanm.event_producer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.producer.LibraryEventProducer;
import com.yordanm.event_producer.services.LibraryEventProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/libraryEvent")
public class LibraryProducerController {

    private final LibraryEventProducerService producerService;
    private final LibraryEventProducer producer;

    @Autowired
    public LibraryProducerController(LibraryEventProducerService producerService, LibraryEventProducer producer) {
        this.producerService = producerService;
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<LibraryEvent> postLibraryEvents(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        producerService.produce(libraryEvent);
        producer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
