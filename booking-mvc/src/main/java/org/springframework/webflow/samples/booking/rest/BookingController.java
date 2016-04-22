package org.springframework.webflow.samples.booking.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.webflow.samples.booking.Booking;
import org.ytoh.webflow.Flow;

/**
 * @author mszarlinski@bravurasolutions.com on 2016-04-21.
 */
@RestController
@RequestMapping("/rest/bookings")
public class BookingController {

    @RequestMapping
    public Booking loadBooking(@Flow final Booking booking) {
        return booking;
    }
}
