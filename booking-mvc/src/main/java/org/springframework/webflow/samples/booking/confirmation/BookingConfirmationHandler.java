package org.springframework.webflow.samples.booking.confirmation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.samples.booking.Booking;
import org.springframework.webflow.samples.booking.BookingService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mszarlinski on 2016-06-08.
 */
@Component
public class BookingConfirmationHandler {

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
    public void confirm(final RequestContext context) {
        final Booking booking = deserializeBooking(context);
        log.info("Saving booking: " + booking.getDescription());
        bookingService.persistBooking(booking);
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

