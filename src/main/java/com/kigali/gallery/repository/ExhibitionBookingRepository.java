package com.kigali.gallery.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.ExhibitionBooking;

@Repository 
public interface ExhibitionBookingRepository extends JpaRepository<ExhibitionBooking, UUID>{
    
    List<ExhibitionBooking> findByStatusOrderByExhibitionDateAsc(BookingStatus status);
    List<ExhibitionBooking> findByExhibitionDateBetweenOrderByExhibitionDateAsc(LocalDate startDate, LocalDate endDate);
    boolean existsByCuratorIdAndExhibitionDateAndStatusNot(UUID curatorId, LocalDate exhibitionDate, BookingStatus status);
    @Query("SELECT b.status, COUNT(b) FROM ExhibitionBooking b GROUP BY b.status")
    List<Object[]> countBookingsByStatus();

    @Modifying
    @Query("UPDATE ExhibitionBooking e SET e.status = :declinedStatus WHERE e.curator.id = :curatorId AND e.exhibitionDate = :date AND e.status <> :exhibitedStatus")
    int cancelBookingsForCuratorOnDate(@Param("curatorId") UUID curatorId, @Param("date") LocalDate date, @Param("declinedStatus") BookingStatus declinedStatus, @Param("exhibitedStatus") BookingStatus exhibitedStatus);

    long countByExhibitionDate(LocalDate exhibitionDate);
    long countByStatus(BookingStatus status);

    Page<ExhibitionBooking> findAll(Pageable pageable);
}
