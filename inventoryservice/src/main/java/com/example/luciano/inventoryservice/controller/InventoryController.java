package com.example.luciano.inventoryservice.controller;

import com.example.luciano.inventoryservice.model.dto.EventInventoryResponseDto;
import com.example.luciano.inventoryservice.model.dto.VenueInventoryResponseDto;
import com.example.luciano.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/events")
    public ResponseEntity<List<EventInventoryResponseDto>> inventoryGetAllEvents() {
        return ResponseEntity.ok(inventoryService.getAllEvents());
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<VenueInventoryResponseDto> inventoryByVenueId(@PathVariable Long venueId) {
        return ResponseEntity.ok(inventoryService.getVenueInformation(venueId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventInventoryResponseDto> inventoryForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(inventoryService.getEventInventory(eventId));
    }
}
