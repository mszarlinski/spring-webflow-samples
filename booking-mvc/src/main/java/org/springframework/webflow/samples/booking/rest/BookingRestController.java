package org.springframework.webflow.samples.booking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.webflow.samples.booking.Booking;
import org.springframework.webflow.samples.booking.BookingService;
import org.ytoh.webflow.Flow;

/**
 * @author mszarlinski on 2016-04-21.
 */
@RestController
@RequestMapping("/rest/bookings")
public class BookingRestController {

    private final BookingService bookingService;

    @Autowired
    public BookingRestController(final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping
    public Booking loadBooking(@Flow final Booking booking) {
        return booking;
    }
}
