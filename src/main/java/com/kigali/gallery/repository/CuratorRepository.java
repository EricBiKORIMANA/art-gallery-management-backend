package com.kigali.gallery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.gallery.model.Curator;
import com.kigali.gallery.model.ExhibitionHall;

@Repository 
public interface CuratorRepository extends JpaRepository<Curator, UUID>{
    
    @Query("SELECT c FROM Curator c JOIN c.artStyles s WHERE LOWER(s.name) = LOWER(:name)")
    List<Curator> findCuratorsByArtStyleName(@Param("name") String name);

    boolean existsByExhibitionHallAndIdNot(ExhibitionHall exhibitionHall, UUID curatorId);
}
