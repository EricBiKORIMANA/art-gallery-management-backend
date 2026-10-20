package com.kigali.gallery.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.gallery.service.DashboardService;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardServ;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary() {
        return new ResponseEntity<>(dashboardServ.getDashboardSummary(),HttpStatus.OK);
    }
    
}
