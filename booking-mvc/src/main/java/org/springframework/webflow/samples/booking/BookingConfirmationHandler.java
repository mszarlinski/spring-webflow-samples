package org.springframework.webflow.samples.booking;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mszarlinski.com on 2016-04-27.
 */
@Component
public class BookingConfirmationHandler extends MultiAction {

    private static final String REVIEW_BOOKING_DATA_REQUEST_PARAM = "reviewBookingData";

    private final BookingService bookingService;

    private final ObjectMapper objectMapper;

    @Autowired
    public BookingConfirmationHandler(final BookingService bookingService, final ObjectMapper objectMapper) {
        this.bookingService = bookingService;
        this.objectMapper = objectMapper;
    }

    public Event handle(final RequestContext context) {
        final Booking booking = deserializeBooking(context);
        if (booking.isValid()) {
            bookingService.persistBooking(booking);
            return success();
        } else {
            return error();
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
