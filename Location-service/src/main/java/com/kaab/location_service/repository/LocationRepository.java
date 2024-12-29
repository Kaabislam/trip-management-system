package com.kaab.location_service.repository;

import com.kaab.location_service.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findByTransporterId(Integer transporterId);
}