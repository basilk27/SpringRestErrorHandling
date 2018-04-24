package com.mbsystem.rest.error.handling.controller;

import com.mbsystem.rest.error.handling.domain.Vote;
import com.mbsystem.rest.error.handling.dto.OptionCount;
import com.mbsystem.rest.error.handling.dto.VoteResult;
import com.mbsystem.rest.error.handling.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ComputeResultController {

    private VoteRepository voteRepository;

    @Autowired
    public ComputeResultController( VoteRepository voteRepository ) {
        this.voteRepository = voteRepository;
    }

    @GetMapping(value="/computeresult")
    public ResponseEntity<?> computeResult( @RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote > allVotes = voteRepository.findByPoll(pollId);

        // Algorithm to count votes
        int totalVotes = 0;
        Map<Long, OptionCount > tempMap = new HashMap<Long, OptionCount>();
        for(Vote v : allVotes) {
            totalVotes ++;
            // Get the OptionCount corresponding to this Option
            OptionCount optionCount = tempMap.get(v.getOption().getId());
            if(optionCount == null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount()+1);
        }

        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());

        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}
