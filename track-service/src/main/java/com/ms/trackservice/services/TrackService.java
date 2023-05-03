package com.ms.trackservice.services;

import com.ms.trackservice.dto.TrackResponse;
import com.ms.trackservice.dto.UploadRequest;
import com.ms.trackservice.models.Track;
import com.ms.trackservice.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    private final TrackRepository trackRepository;

    public void createTrack(UploadRequest request) {
        Track track = Track.builder()
                .name(request.getName())
                .author(request.getAuthor())
                .build();
        trackRepository.save(track);
        log.info("track is saved {}", track);
    }

    public List<TrackResponse> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();
        return tracks.stream().map(this::mapToTrackResponse).toList();
    }

    private TrackResponse mapToTrackResponse(Track track) {
        return TrackResponse.builder()
                .id(track.getId())
                .name(track.getName())
                .author(track.getAuthor())
                .build();
    }

    @SneakyThrows
    public TrackResponse getTrackByName(String name) {
        /*log.info("wait started");
            Thread.sleep(10000);
        log.info("wait ended");*/
        return trackRepository.findByName(name);
    }
}
