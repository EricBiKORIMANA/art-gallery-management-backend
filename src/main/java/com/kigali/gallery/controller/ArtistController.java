package com.kigali.gallery.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.model.Artist;
import com.kigali.gallery.service.ArtistService;

@RestController
@RequestMapping(value = "/api/artists")
public class ArtistController {
    
    @Autowired
    private ArtistService artistServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveArtist(@RequestBody Artist artist){
        String result = artistServ.saveArtist(artist);

        if (result.equals("Artist saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("Failed to save art style", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<Artist> getAllArtists(){
        return artistServ.getAllArtists();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<Artist> result = artistServ.getArtistById(id);

        if(result.isEmpty()){
            return new ResponseEntity<>("The artist with that id does not exist", HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable UUID id, @RequestBody Artist artist){
        String result = artistServ.updateArtist(id, artist);

        if(result.equals("Artist updated successfully")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable UUID id){
        String result = artistServ.deleteArtist(id);

        if (result.equals("Artist deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }
}
