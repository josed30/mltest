package com.ml.test.mutant.detector.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ml.test.mutant.detector.util.MutantConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StatsDTO {

    @JsonProperty("count_mutant_dna")
    private long countMutantDna;
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @JsonProperty("ratio")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Double getRatio(){
        if(countHumanDna > 0){
            double humanData =  countHumanDna;
            double mutantData =  countMutantDna;
            return Math.round((mutantData / humanData) * MutantConstants.RATIO_SCALE) / MutantConstants.RATIO_SCALE;
        }
        return null;
    }

}
