package com.estivman.projects.first.project_first_groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.services.PlaceService;
import com.estivman.secondproject.DynamicMemory.UptcList;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping()
    public ResponseEntity<Object> getPlaces() {
        UptcList<Place> places;
        try {
            places = placeService.getPlaces();
            return ResponseEntity.status(HttpStatus.OK).body(places);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> postPlace(@RequestBody Place place) {
        try {
            placeService.addPlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putPlace(@RequestBody Place place) {
        try {
            placeService.updatePlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @PutMapping("/putPlaceParam")
    public ResponseEntity<Object> putPlaceParam(@RequestParam String searchField, @RequestParam String searchValue,
            @RequestBody Place place) {
        try {
            placeService.updatePlaceThroughParam(searchField, searchValue, place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deletePlace(@RequestBody Place place) {
        try {
            placeService.removePlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping("/deletePlaceParam")
    public ResponseEntity<Object> deletePlaceParam(@RequestParam String searchField, @RequestParam String searchValue) {
        try {
            placeService.deletePlaceThroughParam(searchField, searchValue);
            return ResponseEntity.status(HttpStatus.OK).body("Element deleted successfully");
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }
}
