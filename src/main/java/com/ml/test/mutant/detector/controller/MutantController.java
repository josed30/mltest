package com.ml.test.mutant.detector.controller;

import com.ml.test.mutant.detector.dto.DnaDTO;
import com.ml.test.mutant.detector.dto.StatsDTO;
import com.ml.test.mutant.detector.services.MutantDetectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantDetectorService mutantDetectorService;

    public MutantController(final MutantDetectorService mutantDetectorService){
        this.mutantDetectorService = mutantDetectorService;
    }

    @PostMapping
    public ResponseEntity isMutant(@RequestBody DnaDTO dna){

        final ResponseEntity response;
        if(this.mutantDetectorService.verifyAdn(dna.getDna())){
            response = new ResponseEntity(HttpStatus.OK);
        }else{
            response = new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public StatsDTO stats(){
        return this.mutantDetectorService.getStats();
    }


}
