package com.ml.test.mutant.detector.repository;

import com.ml.test.mutant.detector.domain.AdnVerification;
import com.ml.test.mutant.detector.domain.VerificationStats;
import com.ml.test.mutant.detector.dto.StatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdnVerificationRepository extends JpaRepository<AdnVerification, Long> {

    Optional<AdnVerification> findByDna(String[] dna);

    @Query(" select v.isMutant as verification, count(1) as count " +
            "FROM AdnVerification v " +
            "GROUP BY v.isMutant ")
    List<VerificationStats> generateStats();


}
