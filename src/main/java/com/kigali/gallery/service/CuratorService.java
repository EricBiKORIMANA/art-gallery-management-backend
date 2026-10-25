package com.kigali.gallery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.gallery.model.ArtStyle;
import com.kigali.gallery.model.Curator;
import com.kigali.gallery.model.ExhibitionHall;
import com.kigali.gallery.repository.ArtStyleRepository;
import com.kigali.gallery.repository.CuratorRepository;
import com.kigali.gallery.repository.ExhibitionHallRepository;

@Service 
public class CuratorService {
    
    @Autowired 
    private CuratorRepository curatorRepo;
    
    @Autowired
    private ArtStyleRepository artStyleRepo;

    @Autowired
    private ExhibitionHallRepository exhibitionHallRepo;

    public String saveCurator(Curator curator){
        if (curator.getExhibitionHall() != null && curator.getExhibitionHall().getId() != null) {
            Optional<ExhibitionHall> hallOptional = exhibitionHallRepo.findById(curator.getExhibitionHall().getId());
            if (hallOptional.isEmpty()) {
                return "The exhibition hall with that id does not exist";
            }
            curator.setExhibitionHall(hallOptional.get());
        }

        if (curator.getArtStyles() != null && !curator.getArtStyles().isEmpty()) {
            List<ArtStyle> artStyles = new ArrayList<>();

            for (ArtStyle incomingStyle : curator.getArtStyles()) {
                if (incomingStyle != null && incomingStyle.getId() != null) {
                    Optional<ArtStyle> styleOptional = artStyleRepo.findById(incomingStyle.getId());

                    if (styleOptional.isEmpty()) {
                        return "Art style with ID does not exist";
                    }

                    artStyles.add(styleOptional.get());
                }
            }
            curator.setArtStyles(artStyles);
        }
        curatorRepo.save(curator);
        return "Curator saved successfully";
    }

    public List<Curator> findAllCurators(){
        return curatorRepo.findAll();
    }

    public Optional<Curator> getCuratorById(UUID id){
        return curatorRepo.findById(id);
    }

    public String updateCurator(UUID id, Curator updatedCurator){
        Optional<Curator> existingCurator = curatorRepo.findById(id);

        if(existingCurator.isEmpty()){
            return "The curator with that id does not exist";
        }

        Curator curator = existingCurator.get();
        if (updatedCurator.getFirstName()!=null) {
            curator.setFirstName(updatedCurator.getFirstName());
        }
        if (updatedCurator.getLastName()!=null) {
            curator.setLastName(updatedCurator.getLastName());
        }
        if (updatedCurator.getEmail()!=null) {
            curator.setEmail(updatedCurator.getEmail());
        }

        curatorRepo.save(curator);
        return "Curator updated successfully";
    }

    public String deleteCurator(UUID id) {
        if (!curatorRepo.existsById(id)) {
            return "The curator with that id does not exist";
        }

        curatorRepo.deleteById(id);
        return "Curator deleted successfully";
    }

    public List<Curator> findCuratorsByArtStyleName(String name) {
        return curatorRepo.findCuratorsByArtStyleName(name);
    }

    public String addArtStyleToCurator(UUID curatorId, UUID artStyleId) {
        Optional<Curator> curatorOptional = curatorRepo.findById(curatorId);
        if (curatorOptional.isEmpty()) {
            return "The curator with that id does not exist";
        }

        Optional<ArtStyle> styleOptional = artStyleRepo.findById(artStyleId);
        if (styleOptional.isEmpty()) {
            return "That art style does not exist";
        }

        Curator curator = curatorOptional.get();
        ArtStyle style = styleOptional.get();

        if (curator.getArtStyles().contains(style)) {
            return "The curator already has that art style";
        }

        curator.getArtStyles().add(style);
        curatorRepo.save(curator);

        return "Art style added to the curator";
    }

    public String updateCuratorExhibitionHall(UUID curatorId, int hallNumber) {
        Optional<Curator> curatorOptional = curatorRepo.findById(curatorId);
        if (curatorOptional.isEmpty()) {
            return "The curator with that id does not exist";
        }

        Optional<ExhibitionHall> hallOptional = exhibitionHallRepo.findByHallNumber(hallNumber);
        if (hallOptional.isEmpty()) {
            return "The exhibition hall with that hallNumber does not exist";
        }

        Curator curator = curatorOptional.get();
        ExhibitionHall newHall = hallOptional.get();

        boolean isTaken = curatorRepo.existsByExhibitionHallAndIdNot(newHall, curatorId);
        if (isTaken) {
            return "That exhibition hall is taken by another curator";
        }

        curator.setExhibitionHall(newHall);
        curatorRepo.save(curator);
        return "Exhibition hall changed successfully";
    }
}
