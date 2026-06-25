package com.example.luciano.inventoryservice.service;

import com.example.luciano.inventoryservice.model.dto.EventInventoryResponseDto;
import com.example.luciano.inventoryservice.model.dto.VenueInventoryResponseDto;
import com.example.luciano.inventoryservice.model.entity.Event;
import com.example.luciano.inventoryservice.model.entity.Venue;
import com.example.luciano.inventoryservice.repository.IEventRepository;
import com.example.luciano.inventoryservice.repository.IVenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final IEventRepository eventRepository;
    private final IVenueRepository venueRepository;


    public List<EventInventoryResponseDto> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponseDto.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).toList();
    }

    public VenueInventoryResponseDto getVenueInformation(Long venueId) {
        final Venue venue = venueRepository.findById(venueId)
                .orElse(null);

        return VenueInventoryResponseDto.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    public EventInventoryResponseDto getEventInventory(Long eventId) {
        final Event event = eventRepository.findById(eventId)
                .orElse(null);

        return EventInventoryResponseDto.builder()
                .eventId(event.getId())
                .event(event.getName())
                .ticketPrice(event.getTicketPrice())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build();
    }
}
