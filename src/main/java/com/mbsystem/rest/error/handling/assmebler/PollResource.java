package com.mbsystem.rest.error.handling.assmebler;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mbsystem.rest.error.handling.domain.Poll;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class PollResource extends ResourceSupport {

    @JsonUnwrapped
    private Poll poll;
}
