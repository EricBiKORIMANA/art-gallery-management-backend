package com.kigali.gallery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.ExhibitionHall;
import com.kigali.gallery.repository.ExhibitionHallRepository;

@Service
public class ExhibitionHallService {
    
    @Autowired
    private ExhibitionHallRepository exhibitionHallRepo;

    public String saveExhibitionHall(ExhibitionHall ExhibitionHall){
        exhibitionHallRepo.save(ExhibitionHall);
        return "Exhibition hall saved successfully";
    }

    public List<ExhibitionHall> findAllExhibitionHalls(){
        return exhibitionHallRepo.findAll();
    }

    public Optional<ExhibitionHall> getExhibitionHallById(UUID id){
        return exhibitionHallRepo.findById(id);
    }

    public String updateExhibitionHall(UUID id, ExhibitionHall updatedExhibitionHall){
        Optional<ExhibitionHall> existingExhibitionHall = exhibitionHallRepo.findById(id);

        if(existingExhibitionHall.isEmpty()){
            return "The exhibition hall with that id does not exist";
        }

        ExhibitionHall exhibitionHall = existingExhibitionHall.get();
        if(updatedExhibitionHall.getName()!= null){
            exhibitionHall.setName(updatedExhibitionHall.getName());
        }
        if (updatedExhibitionHall.getHallNumber()>0) {
            exhibitionHall.setHallNumber(updatedExhibitionHall.getHallNumber());
        }

        exhibitionHallRepo.save(exhibitionHall);
        return "Exhibition hall updated successfully";
    }

    public String deleteExhibitionHall(UUID id) {
        if (!exhibitionHallRepo.existsById(id)) {
            return "The exhibition hall with that id does not exist";
        }

        exhibitionHallRepo.deleteById(id);
        return "Exhibition hall deleted successfully";
    }
}
