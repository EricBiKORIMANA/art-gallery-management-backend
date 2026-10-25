package com.kigali.gallery.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.ArtStyle;
import com.kigali.gallery.repository.ArtStyleRepository;

@Service 
public class ArtStyleService {
    
    @Autowired 
    private ArtStyleRepository artStyleRepo;

    public String saveArtStyle(ArtStyle artStyle){
        artStyleRepo.save(artStyle);
        return "Art style saved successfully";
    }

    public List<ArtStyle> findAllArtStyles(){
        return artStyleRepo.findAll();
    }

    public Optional<ArtStyle> getArtStyleById(UUID id){
        return artStyleRepo.findById(id);
    }

    public String updateArtStyle(UUID id, ArtStyle updatedArtStyle){
        Optional<ArtStyle> existingArtStyle = artStyleRepo.findById(id);

        if(existingArtStyle.isEmpty()){
            return "The art style with that id does not exist";
        }

        ArtStyle artStyle = existingArtStyle.get();
        artStyle.setName(updatedArtStyle.getName());

        artStyleRepo.save(artStyle);
        return "Art style updated successfully";
    }

    public String deleteArtStyle(UUID id) {
        if (!artStyleRepo.existsById(id)) {
            return "The art style with that id does not exist";
        }

        artStyleRepo.deleteById(id);
        return "Art style deleted successfully";
    }

    public List<ArtStyle> findUnusedArtStyles() {
        return artStyleRepo.findUnusedArtStyles();
    }
}
