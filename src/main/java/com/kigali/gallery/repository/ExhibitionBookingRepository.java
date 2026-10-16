package com.kigali.gallery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.ExhibitionBooking;

@Repository 
public interface ExhibitionBookingRepository extends JpaRepository<ExhibitionBooking, UUID>{
    
}
