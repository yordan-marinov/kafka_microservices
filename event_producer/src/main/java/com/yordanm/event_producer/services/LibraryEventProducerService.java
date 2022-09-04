package com.yordanm.event_producer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.producer.LibraryEventProducer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEventProducerService {

    private LibraryEventProducer producer;

    public void produce(LibraryEvent libraryEvent) throws JsonProcessingException {
        producer.sendLibraryEvent(libraryEvent);
    }
}
