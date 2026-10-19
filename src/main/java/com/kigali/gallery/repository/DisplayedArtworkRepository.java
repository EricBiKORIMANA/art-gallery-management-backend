package com.kigali.gallery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.DisplayedArtwork;

@Repository 
public interface DisplayedArtworkRepository extends JpaRepository<DisplayedArtwork, UUID>{
    
}
