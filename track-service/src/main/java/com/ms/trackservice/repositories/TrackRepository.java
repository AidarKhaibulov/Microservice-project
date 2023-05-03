package com.ms.trackservice.repositories;

import com.ms.trackservice.dto.TrackResponse;
import com.ms.trackservice.models.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track,String > {
    TrackResponse findByName(String name);
}
