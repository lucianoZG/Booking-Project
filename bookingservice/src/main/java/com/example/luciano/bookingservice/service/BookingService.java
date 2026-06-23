package com.example.luciano.bookingservice.service;

import com.example.luciano.bookingservice.exception.ResourceNotFoundException;
import com.example.luciano.bookingservice.model.dto.BookingRequest;
import com.example.luciano.bookingservice.model.dto.BookingResponse;
import com.example.luciano.bookingservice.model.entity.Customer;
import com.example.luciano.bookingservice.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {

    private final ICustomerRepository customerRepository;

    public BookingResponse createBooking(final BookingRequest request) {
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return BookingResponse.builder().build();
    }
}
