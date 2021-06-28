package com.ml.test.mutant.detector.domain;

public interface VerificationStats {


    void setVerification(Boolean verification);
    void setCount(int count);

    boolean getVerification();
    int getCount();


}
