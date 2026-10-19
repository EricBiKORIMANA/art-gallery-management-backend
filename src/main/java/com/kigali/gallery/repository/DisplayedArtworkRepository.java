package com.kigali.gallery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.DisplayedArtwork;

@Repository 
public interface DisplayedArtworkRepository extends JpaRepository<DisplayedArtwork, UUID>{
    
    @Query("SELECT d FROM DisplayedArtwork d WHERE d.exhibitionBooking.artist.artistCode = :artistCode ORDER BY d.cataloguedDate DESC") 
    List<DisplayedArtwork> findByArtistCode(@Param("artistCode") String artistCode);
}
