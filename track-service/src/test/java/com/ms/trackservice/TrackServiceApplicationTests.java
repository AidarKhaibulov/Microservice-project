package com.ms.trackservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.trackservice.dto.UploadRequest;
import com.ms.trackservice.repositories.TrackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class TrackServiceApplicationTests {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TrackRepository trackRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateTrack() throws Exception {
        UploadRequest uploadRequest = getUploadRequest();
        String uploadRequestString = objectMapper.writeValueAsString(uploadRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(uploadRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, trackRepository.findAll().size());
    }
    @Test
    void getTracks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tracks")).andExpect(status().isOk());
    }


    private UploadRequest getUploadRequest() {
        return UploadRequest.builder()
                .name("Crystal Mountain")
                .author("Death")
                .build();
    }


}
