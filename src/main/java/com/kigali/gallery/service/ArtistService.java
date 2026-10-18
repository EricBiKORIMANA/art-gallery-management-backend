package com.kigali.gallery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.Artist;
import com.kigali.gallery.repository.ArtistRepository;

@Service 
public class ArtistService {
    
    @Autowired
    private ArtistRepository artistRepo;

    public String saveArtist(Artist artist) {
        if (artist.getArtistCode() == null) {
            artist.setArtistCode(generateArtistCode());
        }
        artistRepo.save(artist);
        return "Artist saved successfully";
    }

    
    public String generateArtistCode() {
        long totalArtists = artistRepo.count();
        String artistCode;
        boolean exists;

        do {
            totalArtists++;
            artistCode = String.format("ART-%04d", totalArtists);
            exists = artistRepo.existsByArtistCode(artistCode);
        } while (exists);

        return artistCode;
    }

    public List<Artist> getAllArtists() {
        return artistRepo.findAll();
    }

    public Optional<Artist> getArtistById(UUID id) {
        return artistRepo.findById(id);
    }

    public String updateArtist(UUID id, Artist updatedArtist) {
        Optional<Artist> existingArtist = artistRepo.findById(id);

        if (existingArtist.isEmpty()) {
            return "The artist with that id does not exist";
        }

        Artist artist = existingArtist.get();

        if (updatedArtist.getFirstName() != null) {
            artist.setFirstName(updatedArtist.getFirstName());
        }
        if (updatedArtist.getLastName() != null) {
            artist.setLastName(updatedArtist.getLastName());
        }
        if (updatedArtist.getArtistCode() != null) {
            artist.setArtistCode(updatedArtist.getArtistCode());
        }
        if (updatedArtist.getEmail() != null) {
            artist.setEmail(updatedArtist.getEmail());
        }
        if (updatedArtist.getPhone() != null) {
            artist.setPhone(updatedArtist.getPhone());
        }

        artistRepo.save(artist);
        return "Artist updated successfully";
    }

    public String deleteArtist(UUID id) {
        if (!artistRepo.existsById(id)) {
            return "The artist with that id does not exist";
        }

        artistRepo.deleteById(id);
        return "Artist deleted successfully";
    }

    public List<Artist> findArtistsByLastName(String lastName) {
        return artistRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}
