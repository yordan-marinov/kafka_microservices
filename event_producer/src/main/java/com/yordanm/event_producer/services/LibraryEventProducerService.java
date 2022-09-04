package com.yordanm.event_producer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanm.event_producer.domains.LibraryEvent;
import com.yordanm.event_producer.producer.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class LibraryEventProducerService {

    private final LibraryEventProducer producer;

    public LibraryEventProducerService(LibraryEventProducer producer) {
        this.producer = producer;
    }

    public void produce(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        producer.sendLibraryEvent(libraryEvent);
//        SendResult<Integer, String> result = producer.sendLibraryEventSynchronous(libraryEvent);

    }
}
