package com.kigali.gallery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.ExhibitionHall;

@Repository 
public interface ExhibitionHallRepository extends JpaRepository<ExhibitionHall, UUID>{
    
    @Query("SELECT h FROM ExhibitionHall h WHERE NOT EXISTS (SELECT c FROM Curator c WHERE c.exhibitionHall = h) ORDER BY h.hallNumber ASC")
    List<ExhibitionHall> findFreeExhibitionHalls();
}
