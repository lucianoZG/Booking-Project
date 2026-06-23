package com.example.luciano.inventoryservice.repository;

import com.example.luciano.inventoryservice.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
}
