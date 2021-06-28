package com.ml.test.mutant.detector.services;

import com.ml.test.mutant.detector.domain.AdnVerification;
import com.ml.test.mutant.detector.domain.VerificationStats;
import com.ml.test.mutant.detector.dto.StatsDTO;
import com.ml.test.mutant.detector.repository.AdnVerificationRepository;
import com.ml.test.mutant.detector.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:/application.properties")
public class MutantDetectorServiceTest {


    @MockBean
    private AdnVerificationRepository repository;
    @Autowired
    private MutantDetectorService service;

    @Test
    public void verifyAdn_true(){
        when(repository.findByDna(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(AdnVerification.builder().build());
        boolean valid = service.verifyAdn(TestConstants.DNA_TEST_OK);
        assertTrue(valid);
    }

    @Test
    public void verifyAdn_false(){
        when(repository.findByDna(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(AdnVerification.builder().build());
        boolean valid = service.verifyAdn(TestConstants.DNA_TEST_NOK);
        assertFalse(valid);
    }

    @Test
    public void verifyAdn_getOK(){
        when(repository.findByDna(TestConstants.DNA_TEST_OK)).thenReturn(Optional.of(AdnVerification.builder().id(1l).isMutant(true).build()));
        when(repository.save(any())).thenReturn(AdnVerification.builder().build());
        boolean valid = service.verifyAdn(TestConstants.DNA_TEST_OK);
        assertTrue(valid);
    }

    @Test
    public void verifyAdn_true4(){
        when(repository.findByDna(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(AdnVerification.builder().build());
        boolean valid = service.verifyAdn(TestConstants.DNA_MIN_OK);
        assertTrue(valid);
    }

    @Test
    public void getStats_Empty(){
        when(repository.generateStats()).thenReturn(Collections.emptyList());
        StatsDTO result = service.getStats();
        assertEquals(result.getCountHumanDna(), 0);
        assertEquals(result.getCountMutantDna(), 0);
        assertNull(result.getRatio());
    }

    @Test
    public void getStats_Ok(){
        when(repository.generateStats()).thenReturn(TestConstants.getVerificationStats(100, 40));
        StatsDTO result = service.getStats();
        assertEquals(result.getCountHumanDna(), 100);
        assertEquals(result.getCountMutantDna(), 40);
        assertEquals(result.getRatio().doubleValue(), 0.4d);
    }

}
