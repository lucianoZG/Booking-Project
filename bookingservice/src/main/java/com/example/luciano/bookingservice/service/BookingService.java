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
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final ICustomerRepository customerRepository;
    private final InventoryServiceClient serviceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingResponse createBooking(final BookingRequest request) {
        // Check if the customer exists
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        final InventoryResponse inventoryResponse = serviceClient.getInventory(request.getEventId());

        log.info("Inventory Response: {}", inventoryResponse);

        // Check if there is enough inventory
        if (inventoryResponse.getCapacity() < request.getTicketCount()) {
            throw new BusinessRuleException("Not enough inventory");
        }

        // Create booking
        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventoryResponse);

        // Send booking to Order Service on a Kafka topic
        kafkaTemplate.send(
                "booking", bookingEvent
        );
        log.info("Booking sent to Kafka: {}", bookingEvent);
        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
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
