package com.ms.trackservice.controllers;

import com.ms.trackservice.dto.TrackResponse;
import com.ms.trackservice.dto.UploadRequest;
import com.ms.trackservice.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrack(@RequestBody UploadRequest request) {
        trackService.createTrack(request);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public TrackResponse getTrackByName(@PathVariable("name") String name) {
        return trackService.getTrackByName(name);
    }
    @GetMapping("/demo")
    public String demo(){
        return "It's ok bro!";
    }
}
