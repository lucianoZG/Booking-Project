package com.example.luciano.inventoryservice.repository;

import com.example.luciano.inventoryservice.model.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVenueRepository extends JpaRepository<Venue, Long> {
}
