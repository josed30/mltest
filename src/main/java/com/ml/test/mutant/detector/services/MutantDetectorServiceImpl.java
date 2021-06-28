package com.ml.test.mutant.detector.services;

import com.ml.test.mutant.detector.domain.AdnVerification;
import com.ml.test.mutant.detector.domain.VerificationStats;
import com.ml.test.mutant.detector.dto.StatsDTO;
import com.ml.test.mutant.detector.repository.AdnVerificationRepository;
import com.ml.test.mutant.detector.util.MutantConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

@Service
public class MutantDetectorServiceImpl implements MutantDetectorService{

    private final AdnVerificationRepository adnVerificationRepository;

    public MutantDetectorServiceImpl(final AdnVerificationRepository adnVerificationRepository){
        this.adnVerificationRepository = adnVerificationRepository;
    }

    @Override
    public boolean verifyAdn(final String[] dna) {

        AdnVerification verification = adnVerificationRepository
                .findByDna(dna)
                .orElse(AdnVerification
                        .builder()
                        .dna(dna)
                        .build());

        if(Objects.isNull(verification.getId())){
            verification.setMutant(isMutant(dna));
            adnVerificationRepository.save(verification);
        }

       return verification.isMutant();
    }

    @Override
    public StatsDTO getStats() {

        final List<VerificationStats> stats = adnVerificationRepository.generateStats();
        final StatsDTO result = StatsDTO.builder().build();

        for(VerificationStats verification: stats){
            if(verification.getVerification()){
                result.setCountHumanDna(verification.getCount());
            }else{
                result.setCountMutantDna(verification.getCount());
            }
        }

        return result;
    }

    public boolean isMutant(final String[] dna) {

        final List<String> sequences = new ArrayList<>( );
        sequences.addAll(Arrays.asList(dna));
        sequences.addAll(getVerticalSequences(dna));
        sequences.addAll(getDiagonalSequences(dna));

        int count = 0;
        boolean isMutant = false;
        for(String sequence: sequences){
            count += matches(sequence);
            if(count >= MutantConstants.MIN_MUTANT_SEQUENCE){
                isMutant = true;
                break;
            }
        }

        return isMutant;


    }




    private int matches(final String sequence){

        final Matcher countEmailMatcher = MutantConstants.PATTERN.matcher(sequence);

        int count = 0;
        while (countEmailMatcher.find()) {
            count++;
        }
        return count;

    }


    private List<String> getVerticalSequences(final String dna[]){

        final List<String> vertical = new ArrayList<>();
        final int n = dna.length;

        for(int i = 0; i < n; i++){
            final StringBuilder column = new StringBuilder();
            for(int j = 0; j < n; j++){
                column.append(dna[j].charAt(i));
            }
            vertical.add(column.toString());
        }

        return vertical;

    }

    private List<String> getDiagonalSequences(final String dna[]){

        final int n = dna.length;

        final List<String> diagonal = new ArrayList<>();
        final int total = 2*(n - MutantConstants.MIN_N) + 1;

        final StringBuilder main = new StringBuilder();
        //main
        for(int i = 0; i < n; i++){
            main.append(dna[i].charAt(i));
        }
        diagonal.add(main.toString());

        if(total > 1){
            final int max = (total - 1) / 2;
            for(int i = 1; i <= max; i++){
                final StringBuilder upper = new StringBuilder();
                final StringBuilder lower = new StringBuilder();
                for(int j = i, x = 0; j < n; j++, x++){
                    upper.append(dna[j].charAt(x));
                    lower.append(dna[x].charAt(j));
                }
                diagonal.add(upper.toString());
                diagonal.add(lower.toString());
            }

        }

        return diagonal;

    }

}
