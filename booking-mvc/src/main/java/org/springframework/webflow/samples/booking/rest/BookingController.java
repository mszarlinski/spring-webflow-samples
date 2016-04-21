package org.springframework.webflow.samples.booking.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.webflow.samples.booking.Booking;

/**
 * @author mszarlinski@bravurasolutions.com on 2016-04-21.
 */
@RestController
@RequestMapping("/rest/bookings")
public class BookingController {

    public static final String BOOKING_DATA = "booking";

    @RequestMapping
    public Booking loadBooking(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (Booking) session.getAttribute(BOOKING_DATA);
    }
}
