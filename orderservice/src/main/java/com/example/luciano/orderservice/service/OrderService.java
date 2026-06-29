package com.example.luciano.orderservice.service;

import com.example.luciano.bookingservice.event.BookingEvent;
import com.example.luciano.orderservice.model.entity.Order;
import com.example.luciano.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "booking", groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Received order event:{}", bookingEvent);

        //Create Order object for DB
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);
        //Update inventory
    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .totalPrice(bookingEvent.getTotalPrice())
                .ticketCount(bookingEvent.getTicketCount())
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .build();
    }
}
