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

import com.kigali.gallery.model.ExhibitionHall;
import com.kigali.gallery.service.ExhibitionHallService;

@RestController
@RequestMapping(value = "/api/exhibition-halls")
public class ExhibitionHallController {
    
    @Autowired 
    private ExhibitionHallService exhibitionHallServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveExhibitionHall(@RequestBody ExhibitionHall exhibitionHall){
        String result = exhibitionHallServ.saveExhibitionHall(exhibitionHall);

        if (result.equals("Exhibition hall saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("Failed to save Exhibition hall", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<ExhibitionHall> getAllExhibitionHalls(){
        return exhibitionHallServ.findAllExhibitionHalls();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<ExhibitionHall> result = exhibitionHallServ.getExhibitionHallById(id);

        if(result.isEmpty()){
            return new ResponseEntity<>("The exhibition hall with that id does not exist", HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateExhibitionBooking(@PathVariable UUID id, @RequestBody ExhibitionHall exhibitionHall){
        String result = exhibitionHallServ.updateExhibitionHall(id, exhibitionHall);

        if(result.equals("Exhibition hall updated successfully")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExhibitionHall(@PathVariable UUID id){
        String result = exhibitionHallServ.deleteExhibitionHall(id);

        if (result.equals("Exhibition hall deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }

    @GetMapping("/free")
    public ResponseEntity<?> getFreeHalls() {
        List<ExhibitionHall> result = exhibitionHallServ.findFreeExhibitionHalls();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
