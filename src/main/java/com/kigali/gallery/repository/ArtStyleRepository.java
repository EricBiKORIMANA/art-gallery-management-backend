package com.kigali.gallery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.ArtStyle;

@Repository 
public interface ArtStyleRepository extends JpaRepository<ArtStyle, UUID>{
    
}
