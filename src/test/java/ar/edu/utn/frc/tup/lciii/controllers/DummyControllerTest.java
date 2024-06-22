package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.dummy.SaveDummyDTO;
import ar.edu.utn.frc.tup.lciii.models.Dummy;
import ar.edu.utn.frc.tup.lciii.services.DummyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(DummyController.class)
class DummyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @MockBean
    private DummyService dummyService;

    @Test
    void getDummyList() throws Exception {
        Dummy firstDummy = new Dummy();
        Dummy secondDummy = new Dummy();

        firstDummy.setDummy("firstDummy");
        secondDummy.setDummy("secondDummy");

        List<Dummy> dummyList = new ArrayList<>();
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
        Dummy dummyToReturn = new Dummy();
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

        Dummy dummyCreated = new Dummy();
        dummyCreated.setDummy("createdDummy");

        when(dummyService.createDummy(any(Dummy.class))).thenReturn(dummyCreated);

        mockMvc.perform(post("/dummy")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dummyToCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dummy").value("createdDummy"));
    }

    @Test
    void updateDummy() throws Exception {
        SaveDummyDTO dummyToUpdate = new SaveDummyDTO();
        dummyToUpdate.setDummy("dummyToUpdate");

        Dummy dummyUpdated = new Dummy();
        dummyUpdated.setDummy("updatedDummy");

        when(dummyService.updateDummy(any(Dummy.class))).thenReturn(dummyUpdated);

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