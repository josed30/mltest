package com.ml.test.mutant.detector.util;

import com.ml.test.mutant.detector.domain.VerificationStats;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Arrays;
import java.util.List;

public class TestConstants {

    public static final String[] DNA_TEST_OK = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    public static final String[] DNA_TEST_NOK = {"TTGCGA","CAGTGC","TTATGT","AGAAGG","CCCTTA","TCACTG"};
    public static final String[] DNA_MIN_OK = {"ATGT","AAGT","ATAT","ATTA"};
    public static final long HUMAN_COUNT_TEST_CASE1 = 100;
    public static final long MUTANT_COUNT_TEST_CASE1 = 100;
    public static final double RATIO_TEST_CASE1 = 1.0;


    public static List<VerificationStats> getVerificationStats(int humans, int mutants){

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        final VerificationStats verificationTrue = factory.createProjection(VerificationStats.class);
        final VerificationStats verificationFalse = factory.createProjection(VerificationStats.class);

        verificationTrue.setVerification(true);
        verificationTrue.setCount(humans);
        verificationFalse.setVerification(false);
        verificationFalse.setCount(mutants);


        return Arrays.asList(verificationFalse, verificationTrue);

    }

}
