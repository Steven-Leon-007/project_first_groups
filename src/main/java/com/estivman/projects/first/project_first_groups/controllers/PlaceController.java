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
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.dtos.PlaceDto;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.services.PlaceService;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

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
    public ResponseEntity<Object> postPlace(@RequestBody PlaceDto dto) {
        try {
            PlaceDto.validatePlace(dto);
            Place place = PlaceDto.fromPlaceDto(dto);
            placeService.addPlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putPlace(@RequestBody UptcList<PlaceDto> placesDto) {
        try {
            if (placesDto.size() != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number of places specified");
            }
            PlaceDto.validatePlace(placesDto.get(0));
            PlaceDto.validatePlace(placesDto.get(1));

            Place placeSearched = PlaceDto.fromPlaceDto(placesDto.get(0));
            Place placeUpdated = PlaceDto.fromPlaceDto(placesDto.get(1));
            placeService.updatePlace(placeSearched, placeUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(placeUpdated);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deletePlace(@RequestBody PlaceDto dto) {
        try {
            PlaceDto.validatePlace(dto);
            Place place = PlaceDto.fromPlaceDto(dto);
            placeService.removePlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

}
