package com.mbsystem.rest.error.handling.repository;

import com.mbsystem.rest.error.handling.domain.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {
}
