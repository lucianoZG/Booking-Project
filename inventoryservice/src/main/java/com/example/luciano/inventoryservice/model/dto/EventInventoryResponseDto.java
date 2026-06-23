package com.example.luciano.inventoryservice.model.dto;

import com.example.luciano.inventoryservice.model.entity.Venue;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventInventoryResponseDto {
    private Long eventId;
    private String event;
    private Long capacity;
    private Venue venue;
    private BigDecimal ticketPrice;
}
