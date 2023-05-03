package com.ms.PlayService.services;

import com.ms.PlayService.dto.TrackResponse;
import com.ms.events.PlayingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayService {
    private final RestTemplate restTemplate;
    private final Tracer tracer;
    private final KafkaTemplate<String, String > kafkaTemplate;

    public String playTrack(String track) {
        Span psLookup =tracer.nextSpan().name("playServiceLookup");
        try(Tracer.SpanInScope spanInScope= tracer.withSpan(psLookup.start())){
            TrackResponse tr = restTemplate.getForObject(
                    "http://track/tracks/{name}",
                    TrackResponse.class,
                    track
            );
            log.info(tr.getName());
            kafkaTemplate.send("notificationTopic",tr.getName());

            return "now playing " + tr.getName();
        }finally{
            psLookup.end();
        }

    }
}