package org.springframework.webflow.samples.booking.rest;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.webflow.samples.booking.Booking;
import org.springframework.webflow.samples.booking.BookingService;
import org.springframework.webflow.samples.booking.Hotel;
import org.springframework.webflow.samples.booking.User;

/**
 * @author mszarlinski on 2016-06-07.
 */
@RestController
@RequestMapping("/rest/bookings")
public class BookingRestController {

    private static final Logger log = LoggerFactory.getLogger(BookingRestController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingRestController(final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping
    public Booking loadBooking() {
        log.info("Loading booking");
        // TODO:
        return emptyBooking();
    }

    private static Booking emptyBooking() {
        Hotel hotel = new Hotel();
        hotel.setPrice(BigDecimal.ZERO);

        User user = new User();

        return new Booking(hotel, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveBooking(@RequestBody final Booking booking) {
        log.info("Saving booking: " + booking.getDescription());
        bookingService.persistBooking(booking);
    }
}