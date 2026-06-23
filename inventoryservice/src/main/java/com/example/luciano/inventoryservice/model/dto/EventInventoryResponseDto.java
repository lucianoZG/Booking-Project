package com.example.luciano.inventoryservice.model.dto;

import com.example.luciano.inventoryservice.model.entity.Venue;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventInventoryResponseDto {
    private String event;
    private Long capacity;
    private Venue venue;
}
