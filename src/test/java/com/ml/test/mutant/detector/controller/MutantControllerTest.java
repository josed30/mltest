package com.ml.test.mutant.detector.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.test.mutant.detector.dto.DnaDTO;
import com.ml.test.mutant.detector.dto.StatsDTO;
import com.ml.test.mutant.detector.services.MutantDetectorService;
import com.ml.test.mutant.detector.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantDetectorService mutantDetectorService;

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    public void isMutantTest_200() throws Exception {
        when(mutantDetectorService.verifyAdn(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .content(jsonMapper.writeValueAsBytes(DnaDTO.builder().dna(TestConstants.DNA_TEST_OK).build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void isMutantTest_403() throws Exception {
        when(mutantDetectorService.verifyAdn(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .content(jsonMapper.writeValueAsBytes(DnaDTO.builder().dna(TestConstants.DNA_TEST_OK).build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void stats_200() throws Exception {
        when(mutantDetectorService.getStats()).thenReturn(StatsDTO
                        .builder()
                        .countHumanDna(TestConstants.HUMAN_COUNT_TEST_CASE1)
                        .countMutantDna(TestConstants.MUTANT_COUNT_TEST_CASE1)
                        .build());
        mockMvc.perform(MockMvcRequestBuilders.get("/mutant/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.count_mutant_dna").value(TestConstants.MUTANT_COUNT_TEST_CASE1))
                .andExpect(jsonPath("$.count_human_dna").value(TestConstants.HUMAN_COUNT_TEST_CASE1))
                .andExpect(jsonPath("$.ratio").value(TestConstants.RATIO_TEST_CASE1));

    }


}
