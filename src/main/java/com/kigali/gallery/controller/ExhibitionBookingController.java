package com.kigali.gallery.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        } else if(result.equals("Curator already hosts an exhibition on that date")){
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else if(result.equals("Exhibition booking date cannot be in the past")){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam BookingStatus status) {
        String result = exhibitionBookingServ.updateBookingStatus(id, status);

        if (result.equals("The exhibition booking with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.startsWith("Cannot change status")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExhibitionBooking(@PathVariable UUID id){
        String result = exhibitionBookingServ.deleteExhibitionBooking(id);

        if (result.equals("Exhibition booking deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if(result.equals("That exhibition booking has displayed artworks, delete them first")){
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } 
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ExhibitionBooking>> getByStatus(@RequestParam BookingStatus status) {
        return new ResponseEntity<>(exhibitionBookingServ.findBookingsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<ExhibitionBooking>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return new ResponseEntity<>(exhibitionBookingServ.findBookingsBetweenDates(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<?> getStatsByStatus() {
        List<Object[]> result = exhibitionBookingServ.getBookingStatsByStatus();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam UUID curatorId, @RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        String result = exhibitionBookingServ.cancelDayForCurator(curatorId, parsedDate);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ExhibitionBooking>> getBookingsPaginated(
            @PageableDefault(page = 0, size = 10, sort = "exhibitionDate") Pageable pageable) {
        Page<ExhibitionBooking> pageResult = exhibitionBookingServ.getBookingsPaginated(pageable);
        return ResponseEntity.ok(pageResult);
    }
}
