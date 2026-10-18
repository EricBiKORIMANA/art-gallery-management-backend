package com.kigali.gallery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.Artist;
import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.Curator;
import com.kigali.gallery.model.ExhibitionBooking;
import com.kigali.gallery.repository.ArtistRepository;
import com.kigali.gallery.repository.CuratorRepository;
import com.kigali.gallery.repository.ExhibitionBookingRepository;

@Service 
public class ExhibitionBookingService {
    
    @Autowired
    private ExhibitionBookingRepository exhibitionBookingRepo;

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private CuratorRepository curatorRepo;

    public String saveExhibitionBooking(ExhibitionBooking booking) {
        if (booking.getArtist() != null && booking.getArtist().getId() != null) {
            Optional<Artist> artistOptional = artistRepo.findById(booking.getArtist().getId());
            if (artistOptional.isEmpty()) {
                return "The artist with that id does not exist";
            }
            booking.setArtist(artistOptional.get());
        }

        if (booking.getCurator() != null && booking.getCurator().getId() != null) {
            Optional<Curator> curatorOptional = curatorRepo.findById(booking.getCurator().getId());
            if (curatorOptional.isEmpty()) {
                return "The curator with that id does not exist";
            }
            booking.setCurator(curatorOptional.get());
        }

        exhibitionBookingRepo.save(booking);
        return "Exhibition booking saved successfully";
    }

    public List<ExhibitionBooking> findAllExhibitionBookings() {
        return exhibitionBookingRepo.findAll();
    }

    public Optional<ExhibitionBooking> getExhibitionBookingById(UUID id) {
        return exhibitionBookingRepo.findById(id);
    }

    public String updateExhibitionBooking(UUID id, ExhibitionBooking updatedBooking) {
        Optional<ExhibitionBooking> bookingOptional = exhibitionBookingRepo.findById(id);

        if (bookingOptional.isEmpty()) {
            return "The exhibition booking with that id does not exist";
        }

        ExhibitionBooking existing = bookingOptional.get();

        if (updatedBooking.getExhibitionDate() != null) {
            existing.setExhibitionDate(updatedBooking.getExhibitionDate());
        }
        if (updatedBooking.getTheme() != null) {
            existing.setTheme(updatedBooking.getTheme());
        }

        if (updatedBooking.getArtist() != null && updatedBooking.getArtist().getId() != null) {
            Optional<Artist> artistOptional = artistRepo.findById(updatedBooking.getArtist().getId());
            if (artistOptional.isEmpty()) {
                return "The artist with that id does not exist";
            }
            existing.setArtist(artistOptional.get());
        }

        if (updatedBooking.getCurator() != null && updatedBooking.getCurator().getId() != null) {
            Optional<Curator> curatorOptional = curatorRepo.findById(updatedBooking.getCurator().getId());
            if (curatorOptional.isEmpty()) {
                return "The curator with that id does not exist";
            }
            existing.setCurator(curatorOptional.get());
        }

        exhibitionBookingRepo.save(existing);
        return "Exhibition booking updated successfully";
    }

    public String deleteExhibitionBooking(UUID id) {
        if (!exhibitionBookingRepo.existsById(id)) {
            return "The exhibition booking with that id does not exist";
        }
        exhibitionBookingRepo.deleteById(id);
        return "Exhibition booking deleted successfully";
    }

    public List<ExhibitionBooking> findBookingsByStatus(BookingStatus status) {
        return exhibitionBookingRepo.findByStatusOrderByExhibitionDateAsc(status);
    }
}
