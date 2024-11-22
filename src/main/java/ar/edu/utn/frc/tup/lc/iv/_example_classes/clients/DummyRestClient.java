package ar.edu.utn.frc.tup.lc.iv._example_classes.clients;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DummyRestClient {

    // For logging
    private static final Logger logger = LoggerFactory.getLogger(DummyRestClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/dummies";

    private static final String RESILIENCE4J_INSTANCE_NAME = "name";
    private static final String FALLBACK_METHOD_NAME = "fallbackMethod";

    public List<DummyDto> getAllDummies() {
        try {
            ResponseEntity<DummyDto[]> response = restTemplate.getForEntity(baseUrl, DummyDto[].class);
            logger.warn("Dummies found: {}", (Object) response.getBody());
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (HttpClientErrorException e) {
            throw new EntityNotFoundException("No Dummies found");
        }
    }

    public DummyDto getDummyById(Long dummyId) {
        try {
            logger.warn("Getting Dummy with ID: {}", dummyId);
            ResponseEntity<DummyDto> response = restTemplate.getForEntity(baseUrl + "/" + dummyId, DummyDto.class);
            logger.warn("Dummy found: {}", response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new EntityNotFoundException("Dummy not found");
        }
    }

    public DummyDto createDummy(DummyDto dummyDto) {
        try {
            ResponseEntity<DummyDto> response = restTemplate.postForEntity(baseUrl, dummyDto, DummyDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Error creating Dummy");
        }
    }

    public DummyDto updateDummy(Long dummyId, DummyDto dummyDto) {
        try {
            HttpEntity<DummyDto> requestUpdate = new HttpEntity<>(dummyDto);
            ResponseEntity<DummyDto> response = restTemplate.exchange(
                    baseUrl + "/" + dummyId,
                    HttpMethod.PUT,
                    requestUpdate,
                    DummyDto.class
            );

            if (response.getBody() == null) {
                throw new IllegalArgumentException("No response body received");
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Error updating Dummy");
        }
    }

    public void deleteDummyById(Long dummyId) {
        try {
            restTemplate.delete(baseUrl + "/" + dummyId);
        } catch (HttpClientErrorException e) {
            throw new EntityNotFoundException("Dummy not found for deletion");
        }
    }

    public List<DummyDto> fallbackForGetAllDummies(Throwable throwable) {
        return Collections.emptyList();
    }

    public DummyDto fallbackForGetDummyById(Long dummyId, Throwable throwable) {
        return new DummyDto(1L, "Default Dummy");
    }

    public DummyDto fallbackForCreateDummy(DummyDto dummyDto, Throwable throwable) {
        return new DummyDto(1L, "Default Dummy");
    }

    public DummyDto fallbackForUpdateDummy(DummyDto dummyDto, Throwable throwable) {
        return new DummyDto(1L, "Default Dummy");
    }

    public void fallbackForDeleteDummyById(Long dummyId, Throwable throwable) {
        System.out.println("Failed to delete Dummy with ID: " + dummyId + ". Fallback executed.");
    }
}
