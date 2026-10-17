package com.kigali.gallery.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.model.ArtStyle;
import com.kigali.gallery.service.ArtStyleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping(value = "/api/art-styles")
public class ArtStyleController {
    
    @Autowired
    private ArtStyleService artStyleServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveArtStyle(@RequestBody ArtStyle artStyle){
        String result = artStyleServ.saveArtStyle(artStyle);

        if (result.equals("Art style saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("Failed to save art style", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<ArtStyle> getAllArtStyles(){
        return artStyleServ.findAllArtStyles();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<ArtStyle> result = artStyleServ.getArtStyleById(id);

        if(result.isEmpty()){
            return new ResponseEntity<>("The art style with that id does not exist", HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateArtStyle(@PathVariable UUID id, @RequestBody ArtStyle artStyle){
        String result = artStyleServ.updateArtStyle(id, artStyle);

        if(result.equals("Art style updated successfully")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArtStyle(@PathVariable UUID id){
        String result = artStyleServ.deleteArtStyle(id);

        if (result.equals("Art style deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }
}
