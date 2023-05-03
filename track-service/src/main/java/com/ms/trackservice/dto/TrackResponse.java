package com.ms.trackservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackResponse {
    private String id;
    private String name;
    private String author;
}
