package ar.edu.utn.frc.tup.lc.iv._example_classes.clients;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class DummyDtoRestClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private DummyRestClient dummyRestClient;

    @Test
    void getAllDummies() {
        DummyDto dummyDto1 = new DummyDto(1L, "Dummy 1");
        DummyDto dummyDto2 = new DummyDto(2L, "Dummy 2");
        DummyDto[] dummyDtoList = {dummyDto1, dummyDto2};

        when(restTemplate.getForEntity("http://localhost:8080/api/dummies", DummyDto[].class))
                .thenReturn(ResponseEntity.ok(dummyDtoList));

        List<DummyDto> dummies = dummyRestClient.getAllDummies();
        assertEquals(2, dummies.size());
        assertEquals(1L, dummies.get(0).id());
        assertEquals("Dummy 2", dummies.get(1).dummy());
    }

    @Test
    void getDummyById() {
        DummyDto dummyDto = new DummyDto(1L, "Dummy 1");

        when(restTemplate.getForEntity("http://localhost:8080/api/dummies/1", DummyDto.class))
                .thenReturn(ResponseEntity.ok(dummyDto));

        DummyDto dummy = dummyRestClient.getDummyById(1L);
        assertEquals(1L, dummy.id());
        assertEquals("Dummy 1", dummy.dummy());
    }

    @Test
    void createDummy() {
        DummyDto newDummyDto = new DummyDto(null, "New Dummy");
        DummyDto createdDummyDto = new DummyDto(3L, "New Dummy");

        when(restTemplate.postForEntity("http://localhost:8080/api/dummies", newDummyDto, DummyDto.class))
                .thenReturn(ResponseEntity.ok(createdDummyDto));

        DummyDto dummy = dummyRestClient.createDummy(newDummyDto);
        assertEquals(3L, dummy.id());
        assertEquals("New Dummy", dummy.dummy());
    }

    @Test
    void updateDummy() {
        DummyDto updatedDummyDto = new DummyDto(1L, "Updated Dummy");

        /*
        Void response:
        when(restTemplate.exchange(eq("http://localhost:8080/api/dummies/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.ok().build());
         */

        when(restTemplate.exchange(eq("http://localhost:8080/api/dummies/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(DummyDto.class)))
                .thenReturn(ResponseEntity.ok(updatedDummyDto));

        dummyRestClient.updateDummy(1L, updatedDummyDto);

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/api/dummies/1"),
                        eq(HttpMethod.PUT),
                        any(HttpEntity.class),
                        eq(DummyDto.class)
                );
    }

    @Test
    void deleteDummyById() {
        doNothing().when(restTemplate).delete("http://localhost:8080/api/dummies/1");

        dummyRestClient.deleteDummyById(1L);
        verify(restTemplate, times(1)).delete("http://localhost:8080/api/dummies/1");
    }
}