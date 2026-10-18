package com.kigali.gallery.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.ExhibitionBooking;

@Repository 
public interface ExhibitionBookingRepository extends JpaRepository<ExhibitionBooking, UUID>{
    
    List<ExhibitionBooking> findByStatusOrderByExhibitionDateAsc(BookingStatus status);
    List<ExhibitionBooking> findByExhibitionDateBetweenOrderByExhibitionDateAsc(LocalDate startDate, LocalDate endDate);
}
