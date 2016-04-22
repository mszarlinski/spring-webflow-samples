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
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping
    public Booking loadBooking(@Flow final Booking booking) {
        return booking;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Booking saveBooking(@RequestBody final Booking booking) {
        bookingService.persistBooking(booking);
        return booking;
    }
}
