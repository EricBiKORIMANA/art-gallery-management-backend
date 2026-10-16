package com.kigali.gallery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.ExhibitionHall;

@Repository 
public interface ExhibitionHallRepository extends JpaRepository<ExhibitionHall, UUID>{
    
}
