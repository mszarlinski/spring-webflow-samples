package org.springframework.webflow.samples.booking.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.webflow.samples.booking.Booking;
import org.ytoh.webflow.Flow;

/**
 * @author mszarlinski on 2016-06-07.
 */
@RestController
@RequestMapping("/rest/bookings")
public class BookingRestController {

    private static final Logger log = LoggerFactory.getLogger(BookingRestController.class);

    @RequestMapping
    public Booking loadBooking(@Flow final Booking booking) {
        log.info("Loading booking");
        return booking;
    }
}