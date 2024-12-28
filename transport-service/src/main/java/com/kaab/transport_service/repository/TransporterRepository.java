package com.kaab.transport_service.repository;


import com.kaab.transport_service.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransporterRepository extends JpaRepository<Transporter, Integer> {
    // Find a transporter by availability
    Optional<Transporter> findByAvailableTrue();
}