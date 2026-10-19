package com.kigali.gallery.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.model.ArtStyle;
import com.kigali.gallery.model.Curator;
import com.kigali.gallery.service.CuratorService;

@RestController 
@RequestMapping(value = "/api/curators")
public class CuratorController {
    
    @Autowired
    private CuratorService curatorServ;


    @PostMapping("/save")
    public ResponseEntity<?> saveCurator(@RequestBody Curator artStyle){
        String result = curatorServ.saveCurator(artStyle);

        if (result.equals("Curator saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if(result.equals("Art style with ID does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if(result.equals("The exhibition hall with that id does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Failed to save art style", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<Curator> getAllCurator(){
        return curatorServ.findAllCurators();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<Curator> result = curatorServ.getCuratorById(id);

        if(result.isEmpty()){
            return new ResponseEntity<>("The art style with that id does not exist", HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCurator(@PathVariable UUID id, @RequestBody Curator curator){
        String result = curatorServ.updateCurator(id, curator);

        if(result.equals("Curator updated successfully")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCurator(@PathVariable UUID id){
        String result = curatorServ.deleteCurator(id);

        if (result.equals("Curator deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }

    @GetMapping("/by-art-style")
    public ResponseEntity<?> getByArtStyle(@RequestParam String name) {
        List<Curator> result = curatorServ.findCuratorsByArtStyleName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{curatorId}/art-styles/{artStyleId}")
    public ResponseEntity<?> addArtStyle(@PathVariable UUID curatorId, @PathVariable UUID artStyleId) {
        String result = curatorServ.addArtStyleToCurator(curatorId, artStyleId);

        if (result.equals("The curator with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if(result.equals("That art style does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.equals("The curator already has that art style")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PatchMapping("/{curatorId}/exhibition-hall")
    public ResponseEntity<?> changeHall(@PathVariable UUID curatorId, @RequestParam int hallNumber) {
        String result = curatorServ.updateCuratorExhibitionHall(curatorId, hallNumber);

        if (result.equals("The curator with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if(result.equals("The exhibition hall with that hallNumber does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.equals("That exhibition hall is taken by another curator")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
