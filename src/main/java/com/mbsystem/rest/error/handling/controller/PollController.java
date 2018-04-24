package com.mbsystem.rest.error.handling.controller;

import com.mbsystem.rest.error.handling.domain.Poll;
import com.mbsystem.rest.error.handling.exception.ResourceNotFoundException;
import com.mbsystem.rest.error.handling.repository.PollRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@Slf4j
public class PollController {

    private PollRepository pollRepository;

    @Autowired
    public PollController( PollRepository pollRepository ) {
        this.pollRepository = pollRepository;
    }

    @PostMapping(value="/polls")
    public ResponseEntity<?> createPoll( @Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();

        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(poll.getId()).toUri();

        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value="/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @GetMapping(value="/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);

        Optional<Poll> optionalPoll = pollRepository.findById( pollId);

        Poll poll = null;

        if (optionalPoll.isPresent())
            poll = optionalPoll.get();

        return new ResponseEntity<> (poll, HttpStatus.OK);
    }

    @PostMapping(value="/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll p = pollRepository.save(poll);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @DeleteMapping(value="/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void verifyPoll( Long pollId ) {
        Optional<Poll> optionalPoll = pollRepository.findById( pollId);
        if (!optionalPoll.isPresent()) {
            throw new ResourceNotFoundException( "Poll with id: " + pollId + " not found." );
        }
    }
}
