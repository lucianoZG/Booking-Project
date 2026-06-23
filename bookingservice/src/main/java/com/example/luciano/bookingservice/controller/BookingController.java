package com.example.luciano.bookingservice.controller;

import com.example.luciano.bookingservice.model.dto.BookingRequest;
import com.example.luciano.bookingservice.model.dto.BookingResponse;
import com.example.luciano.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(final BookingService bookingService) {this.bookingService = bookingService;}

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }
}
