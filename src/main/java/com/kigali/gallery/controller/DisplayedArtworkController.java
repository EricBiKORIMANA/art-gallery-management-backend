package com.kigali.gallery.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.model.DisplayedArtwork;
import com.kigali.gallery.service.DisplayedArtworkService;

@RestController
@RequestMapping(value = "/api/displayed-artworks")
public class DisplayedArtworkController {
    
    @Autowired
    private DisplayedArtworkService displayedArtworkServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveDisplayedArtwork(@RequestParam UUID exhibitionBookingId, @RequestBody DisplayedArtwork artwork) {
        String result = displayedArtworkServ.saveDisplayedArtwork(exhibitionBookingId, artwork);

        if (result.equals("The exhibition booking with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.equals("Displayed artworks are only for EXHIBITED exhibition bookings")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @GetMapping("/artist/{artistCode}")
    public ResponseEntity<List<DisplayedArtwork>> getArtworksByArtistCode(@PathVariable String artistCode) {
        List<DisplayedArtwork> artworks = displayedArtworkServ.getArtworksByArtistCode(artistCode);
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }
}
