package com.kigali.gallery.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.repository.ArtistRepository;
import com.kigali.gallery.repository.CuratorRepository;
import com.kigali.gallery.repository.DisplayedArtworkRepository;
import com.kigali.gallery.repository.ExhibitionBookingRepository;
import com.kigali.gallery.repository.ExhibitionHallRepository;

@Service 
public class DashboardService {
    
    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private CuratorRepository curatorRepo;

    @Autowired
    private ExhibitionHallRepository exhibitionHallRepo;

    @Autowired
    private ExhibitionBookingRepository exhibitionBookingRepo;

    @Autowired
    private DisplayedArtworkRepository displayedArtworkRepo;

    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("artists", artistRepo.count());
        summary.put("curators", curatorRepo.count());
        summary.put("freeexhibitionHalls", (long) exhibitionHallRepo.findFreeExhibitionHalls().size());
        summary.put("bookingsToday", exhibitionBookingRepo.countByExhibitionDate(LocalDate.now()));
        summary.put("proposed", exhibitionBookingRepo.countByStatus(BookingStatus.PROPOSED));
        summary.put("displayedArtworks", displayedArtworkRepo.count());

        return summary;
    }
}
