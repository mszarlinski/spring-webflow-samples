package org.springframework.webflow.samples.booking.confirmation;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.samples.booking.Booking;
import org.springframework.webflow.samples.booking.BookingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * @author mszarlinski on 2016-06-08.
 */
@Component
public class BookingConfirmationHandler extends MultiAction {

    private static final Logger log = LoggerFactory.getLogger(BookingConfirmationHandler.class);

    private static final String REVIEW_BOOKING_DATA_REQUEST_PARAM = "reviewBookingData";

    private final BookingService bookingService;

    private final ObjectMapper objectMapper;

    @Autowired
    public BookingConfirmationHandler(final BookingService bookingService, final ObjectMapper objectMapper) {
        this.bookingService = bookingService;
        this.objectMapper = objectMapper;
    }

    /**
     * Handle confirmed booking.
     */
    public Event confirm(final RequestContext context) {
        final Booking booking = deserializeBooking(context);

        log.info("Saving booking: " + booking.getDescription());

        if (booking.isValid()) {
            try {
                bookingService.persistBooking(booking);
                return success();
            } catch (final Exception ex) {
                log.error("Error in confirm", ex);
                return error(context, ex);
            }
        } else {
            return error(context, "Form is invalid. Please fill all required fields");
        }
    }

    private Event error(final RequestContext context, final String message) {
        context.getFlashScope().put("errorMessage", message);
        return error();
    }

    private Event error(final RequestContext context, final Exception ex) {
        final Throwable rootCause = Throwables.getRootCause(ex);

        if (rootCause instanceof ConstraintViolationException) {
            return error(context, ((ConstraintViolationException) rootCause).getConstraintViolations()
                .stream()
                //TODO: method reference, Tomcat issue
                .map(new Function<ConstraintViolation, String>() {
                    @Override
                    public String apply(final ConstraintViolation constraintViolation) {
                        return constraintViolation.getMessage();
                    }
                })
                .collect(joining(", ")));
        } else {
            return error(context, "Internal server error");
        }
    }

    private Booking deserializeBooking(final RequestContext context) {
        final String reviewBookingDataJSON = context.getExternalContext().getRequestParameterMap().get(REVIEW_BOOKING_DATA_REQUEST_PARAM);
        try {
            return objectMapper.readValue(reviewBookingDataJSON, Booking.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

