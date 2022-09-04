package com.yordanm.event_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanm.event_producer.domains.LibraryEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
@AllArgsConstructor
public class LibraryEventProducer {

    private KafkaTemplate<Integer, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private final String topic = "library-events";

    public void sendLibraryEventDefaultTopic(LibraryEvent libraryEvent) throws JsonProcessingException {
        int key = libraryEvent.getEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        int key = libraryEvent.getEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }


    public SendResult<Integer, String> sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        int key = libraryEvent.getEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer, String> sendResult;

        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
            log.info("<><><><> SendResult is: {}", sendResult.toString());
            log.info("Message send successfully for the key: {} with the value: {}, partition is: {}",
                    key, value, sendResult.getRecordMetadata().partition());
        } catch (InterruptedException | ExecutionException e) {
            log.error("InterruptedException|ExecutionException Error Sending Message with exception: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception Error Sending Message with exception: {}", e.getMessage());
            throw e;
        }
        return sendResult;
    }

    // ------ Private methods ----------
    private ProducerRecord<Integer, String> buildProducerRecord(int key, String value, String topic) {

        List<Header> headers = List.of(
                new RecordHeader("event-source", "scanner".getBytes(StandardCharsets.UTF_8)),
                new RecordHeader("event-source-id", "123456789".getBytes(StandardCharsets.UTF_8))
        );

        return new ProducerRecord<>(topic, null, key, value, headers);
    }

    private void handleSuccess(int key, String value, SendResult<Integer, String> result) {
        log.info("Message send successfully for the key: {} with the value: {}, partition is: {}",
                key, value, result.getRecordMetadata().partition());
    }

    private void handleFailure(Throwable ex) {
        log.error("Error Sending Message with exception: {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }
}
