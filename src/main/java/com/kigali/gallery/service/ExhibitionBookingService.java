package com.kigali.gallery.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.Artist;
import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.Curator;
import com.kigali.gallery.model.ExhibitionBooking;
import com.kigali.gallery.repository.ArtistRepository;
import com.kigali.gallery.repository.CuratorRepository;
import com.kigali.gallery.repository.DisplayedArtworkRepository;
import com.kigali.gallery.repository.ExhibitionBookingRepository;

import jakarta.transaction.Transactional;

@Service 
public class ExhibitionBookingService {
    
    @Autowired
    private ExhibitionBookingRepository exhibitionBookingRepo;

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private CuratorRepository curatorRepo;

    @Autowired
    private DisplayedArtworkRepository displayedArtworkRepo;

    public String saveExhibitionBooking(ExhibitionBooking booking) {
        if (booking.getExhibitionDate() == null) {
            return "Exhibition date is required";
        }
        if (booking.getExhibitionDate() != null && booking.getExhibitionDate().isBefore(LocalDate.now())) {
            return "Exhibition booking date cannot be in the past";
        }

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

            boolean isConflict = exhibitionBookingRepo.existsByCuratorIdAndExhibitionDateAndStatusNot(curatorOptional.get().getId(), booking.getExhibitionDate(), BookingStatus.DECLINED);

            if (isConflict) {
                return "Curator already hosts an exhibition on that date";
            }
        }

        booking.setStatus(BookingStatus.PROPOSED);
        
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

    public String updateBookingStatus(UUID id, BookingStatus newStatus) {
        Optional<ExhibitionBooking> bookingOptional = exhibitionBookingRepo.findById(id);
        if (bookingOptional.isEmpty()) {
            return "The exhibition booking with that id does not exist";
        }

        ExhibitionBooking booking = bookingOptional.get();
        BookingStatus currentStatus = booking.getStatus();

        boolean isValid = false;
        if (currentStatus == BookingStatus.PROPOSED) {
            if (newStatus == BookingStatus.ACCEPTED || newStatus == BookingStatus.DECLINED) {
                isValid = true;
            }
        } else if (currentStatus == BookingStatus.ACCEPTED) {
            if (newStatus == BookingStatus.EXHIBITED || newStatus == BookingStatus.DECLINED) {
                isValid = true;
            }
        }

        if (!isValid) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        booking.setStatus(newStatus);
        exhibitionBookingRepo.save(booking);
        return "Exhibition booking status updated successfully";
    }

    public String deleteExhibitionBooking(UUID id) {
        if (!exhibitionBookingRepo.existsById(id)) {
            return "The exhibition booking with that id does not exist";
        }
        boolean hasArtworks = displayedArtworkRepo.existsByExhibitionBookingId(id);
        if (hasArtworks) {
            return "That exhibition booking has displayed artworks, delete them first";
        }

        exhibitionBookingRepo.deleteById(id);
        return "Exhibition booking deleted successfully";
    }

    public List<ExhibitionBooking> findBookingsByStatus(BookingStatus status) {
        return exhibitionBookingRepo.findByStatusOrderByExhibitionDateAsc(status);
    }

    public List<ExhibitionBooking> findBookingsBetweenDates(LocalDate start, LocalDate end) {
        return exhibitionBookingRepo.findByExhibitionDateBetweenOrderByExhibitionDateAsc(start, end);
    }

    public List<Object[]> getBookingStatsByStatus() {
        return exhibitionBookingRepo.countBookingsByStatus();
    }

    @Transactional
    public String cancelDayForCurator(UUID curatorId, LocalDate date) {
        int updatedCount = exhibitionBookingRepo.cancelBookingsForCuratorOnDate(curatorId, date, BookingStatus.DECLINED, BookingStatus.EXHIBITED);
        return updatedCount + " exhibition bookings cancelled";
    }

    public Page<ExhibitionBooking> getBookingsPaginated(Pageable pageable) {
        return exhibitionBookingRepo.findAll(pageable); 
    }
}
