package com.example.luciano.bookingservice.service;

import com.example.luciano.bookingservice.client.InventoryServiceClient;
import com.example.luciano.bookingservice.event.BookingEvent;
import com.example.luciano.bookingservice.exception.BusinessRuleException;
import com.example.luciano.bookingservice.exception.ResourceNotFoundException;
import com.example.luciano.bookingservice.model.dto.BookingRequest;
import com.example.luciano.bookingservice.model.dto.BookingResponse;
import com.example.luciano.bookingservice.model.dto.InventoryResponse;
import com.example.luciano.bookingservice.model.entity.Customer;
import com.example.luciano.bookingservice.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ICustomerRepository customerRepository;
    private final InventoryServiceClient serviceClient;

    public BookingResponse createBooking(final BookingRequest request) {
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        final InventoryResponse inventoryResponse = serviceClient.getInventory(request.getEventId());

        if (inventoryResponse.getCapacity() < request.getTicketCount()) {
            throw new BusinessRuleException("Not enough inventory");
        }

        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventoryResponse);
        return BookingResponse.builder().build();
    }

    private BookingEvent createBookingEvent(final BookingRequest request,
                                            final Customer customer,
                                            final InventoryResponse inventoryResponse) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }
}
