package com.kigali.gallery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.Artist;

@Repository 
public interface ArtistRepository extends JpaRepository<Artist, UUID>{

    boolean existsByArtistCode(String artistCode);
}
