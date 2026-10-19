package com.kigali.gallery.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.DisplayedArtwork;
import com.kigali.gallery.model.ExhibitionBooking;
import com.kigali.gallery.repository.DisplayedArtworkRepository;
import com.kigali.gallery.repository.ExhibitionBookingRepository;

@Service 
public class DisplayedArtworkService {
    
    @Autowired
    private DisplayedArtworkRepository displayedArtworkRepo;

    @Autowired
    private ExhibitionBookingRepository exhibitionBookingRepo;


    public String saveDisplayedArtwork(UUID exhibitionBookingId, DisplayedArtwork artwork) {
        Optional<ExhibitionBooking> bookingOptional = exhibitionBookingRepo.findById(exhibitionBookingId);
        if (bookingOptional.isEmpty()) {
            return "The exhibition booking with that id does not exist";
        }

        ExhibitionBooking booking = bookingOptional.get();

        if (booking.getStatus() != BookingStatus.EXHIBITED) {
            return "Displayed artworks are only for EXHIBITED exhibition bookings";
        }

        artwork.setCataloguedDate(LocalDate.now());
        artwork.setExhibitionBooking(booking);

        displayedArtworkRepo.save(artwork);
        return "Displayed artwork saved successfully";
    }
}
