package com.mbsystem.rest.error.handling.repository;

import com.mbsystem.rest.error.handling.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
