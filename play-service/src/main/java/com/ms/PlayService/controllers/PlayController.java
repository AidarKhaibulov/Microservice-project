package com.ms.PlayService.controllers;
import com.ms.PlayService.services.PlayService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/play")
@RequiredArgsConstructor

public class PlayController {
    private final PlayService playService;

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name="track",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="track")
    @Retry(name="track")
    public CompletableFuture<String> playTrack(@PathVariable("name") String track) {
        return CompletableFuture.supplyAsync(()->playService.playTrack(track));

    }
    public CompletableFuture<String> fallbackMethod(String track,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong!");
    }
}
