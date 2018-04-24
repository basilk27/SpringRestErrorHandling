package com.mbsystem.rest.error.handling.assmebler;

import com.mbsystem.rest.error.handling.controller.PollController;
import com.mbsystem.rest.error.handling.domain.Poll;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class PollResourceAssembler extends ResourceAssemblerSupport<Poll, PollResource> {

    public PollResourceAssembler() {
        super( PollController.class, PollResource.class );
    }

    @Override
    public PollResource toResource( Poll poll ) {
        PollResource pollResource = createResourceWithId(poll.getId(), poll);

//bmk        Link invoiceLink = ControllerLinkBuilder.linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");
        return null;
    }
}
