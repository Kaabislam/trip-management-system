package com.kaab.trip_service.repository;

import com.kaab.trip_service.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}