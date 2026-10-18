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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.model.BookingStatus;
import com.kigali.gallery.model.ExhibitionBooking;
import com.kigali.gallery.service.ExhibitionBookingService;

@RestController
@RequestMapping(value = "/api/exhibition-bookings")
public class ExhibitionBookingController {
    
    @Autowired
    private ExhibitionBookingService exhibitionBookingServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveExhibitionBooking(@RequestBody ExhibitionBooking exhibitionBooking){
        String result = exhibitionBookingServ.saveExhibitionBooking(exhibitionBooking);

        if (result.equals("Exhibition booking saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if(result.equals("The artist with that id does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if(result.equals("The curator with that id does not exist")){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Failed to save Exhibition Booking", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public List<ExhibitionBooking> getAllExhibitionBookings(){
        return exhibitionBookingServ.findAllExhibitionBookings();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<ExhibitionBooking> result = exhibitionBookingServ.getExhibitionBookingById(id);

        if(result.isEmpty()){
            return new ResponseEntity<>("The exhibition booking with that id does not exist", HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateExhibitionBooking(@PathVariable UUID id, @RequestBody ExhibitionBooking exhibitionBooking){
        String result = exhibitionBookingServ.updateExhibitionBooking(id, exhibitionBooking);

        if(result.equals("Exhibition booking updated successfully")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExhibitionBooking(@PathVariable UUID id){
        String result = exhibitionBookingServ.deleteExhibitionBooking(id);

        if (result.equals("Exhibition booking deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ExhibitionBooking>> getByStatus(@RequestParam BookingStatus status) {
        return new ResponseEntity<>(exhibitionBookingServ.findBookingsByStatus(status), HttpStatus.OK);
    }
}
