package com.example.luciano.inventoryservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueInventoryResponseDto {
    private Long venueId;
    private String venueName;
    private Long totalCapacity;
}
