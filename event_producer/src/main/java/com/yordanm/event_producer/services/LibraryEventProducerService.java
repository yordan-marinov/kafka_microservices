package com.yordanm.event_producer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.producer.LibraryEventProducer;
import org.springframework.stereotype.Service;

@Service
public class LibraryEventProducerService {

    private final LibraryEventProducer producer;

    public LibraryEventProducerService(LibraryEventProducer producer) {
        this.producer = producer;
    }

    public void produce(LibraryEvent libraryEvent) throws JsonProcessingException {
        producer.sendLibraryEvent(libraryEvent);
    }
}
