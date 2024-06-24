package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.dummy.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lciii.dtos.dummy.SaveDummyDTO;
import ar.edu.utn.frc.tup.lciii.models.Dummy;
import ar.edu.utn.frc.tup.lciii.services.DummyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DummyController.class)
class DummyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DummyService dummyService;

    @Test
    void getDummyList() throws Exception {
        ResponseDummyDTO firstDummy = new ResponseDummyDTO();
        ResponseDummyDTO secondDummy = new ResponseDummyDTO();

        firstDummy.setDummy("firstDummy");
        secondDummy.setDummy("secondDummy");

        List<ResponseDummyDTO> dummyList = new ArrayList<>();
        dummyList.add(firstDummy);
        dummyList.add(secondDummy);

        when(dummyService.getDummyList()).thenReturn(dummyList);

        mockMvc.perform(get("/dummy"))
                .andDo(print())
                .andExpect(status().isOk())
                // First element
                .andExpect(jsonPath("$[0].dummy").value("firstDummy"))
                // Second element
                .andExpect(jsonPath("$[1].dummy").value("secondDummy"));
    }

    @Test
    void getDummyById() throws Exception {
        ResponseDummyDTO dummyToReturn = new ResponseDummyDTO();
        dummyToReturn.setDummy("something");

        when(dummyService.getDummyById(1L)).thenReturn(dummyToReturn);

        // First way to test
        mockMvc.perform(get("/dummy/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummy").value("something"));
        //.andExpect(jsonPath("$.anotherAttribute").value("attribute value"));
        //.andExpect(jsonPath("$.anotherAttribute").value("attribute value"));

        // Second way to test
        MvcResult mvcResult = mockMvc.perform(get("/dummy/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Dummy dummyResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Dummy.class);

        assertEquals("something", dummyResult.getDummy());
        // assertEquals("attribute value", dummyResult.getAnotherAttribute());
        // assertEquals("attribute value", dummyResult.getAnotherAttribute());
    }

    @Test
    void createDummy() throws Exception {
        SaveDummyDTO dummyToCreate = new SaveDummyDTO();
        dummyToCreate.setDummy("dummyToCreate");

        ResponseDummyDTO dummyCreated = new ResponseDummyDTO();
        dummyCreated.setDummy("createdDummy");

        when(dummyService.createDummy(any(SaveDummyDTO.class))).thenReturn(dummyCreated);

        mockMvc.perform(post("/dummy")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dummyToCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummy").value("createdDummy"));
    }

    @Test
    void createDummyWithWrongDummy() throws Exception {
        SaveDummyDTO dummyToCreate = new SaveDummyDTO();
        dummyToCreate.setDummy("dummyToCreate");

        when(dummyService.createDummy(any(SaveDummyDTO.class))).thenThrow(new IllegalArgumentException("Dummy already exists"));

        mockMvc.perform(post("/dummy")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dummyToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Dummy already exists"));
    }

    @Test
    void updateDummy() throws Exception {
        SaveDummyDTO dummyToUpdate = new SaveDummyDTO();
        dummyToUpdate.setDummy("dummyToUpdate");

        ResponseDummyDTO dummyUpdated = new ResponseDummyDTO();
        dummyUpdated.setDummy("updatedDummy");

        when(dummyService.updateDummy(any(Long.class), any(SaveDummyDTO.class))).thenReturn(dummyUpdated);

        mockMvc.perform(put("/dummy/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dummyToUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummy").value("updatedDummy"));
    }

    @Test
    void deleteDummyById() throws Exception {
        mockMvc.perform(delete("/dummy/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}