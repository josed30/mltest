package com.ml.test.mutant.detector.services;

import com.ml.test.mutant.detector.dto.StatsDTO;

public interface MutantDetectorService {

    boolean verifyAdn(String[] dna);
    StatsDTO getStats();

}
