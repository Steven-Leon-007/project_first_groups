package com.estivman.projects.first.project_first_groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.projects.first.project_first_groups.services.QueriesService;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

@RestController
@RequestMapping("/queries")
public class QueriesController {

    @Autowired
    private QueriesService loaderService;


    @GetMapping("/subject-places")
    public ResponseEntity<Object> subjectsWithSamePlace() {
        try {
            UptcList<Subject> subjectsFiltered;

            subjectsFiltered = loaderService.subjectsWithSamePlace();
            return ResponseEntity.status(HttpStatus.OK).body(subjectsFiltered);

        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @GetMapping("/search-subject-place")
    public ResponseEntity<Object> searchSubjectsWithSamePlace(@RequestParam("placeId") String placeId,
            @RequestParam("placeName") String placeName,
            @RequestParam("placeAddress") String placeAddress) {
        try {

            UptcList<Subject> subjectsFiltered;
            Place place = new Place(placeId, placeName, placeAddress);
            subjectsFiltered = loaderService.searchSubjectsWithSamePlace(place);
            return ResponseEntity.status(HttpStatus.OK).body(subjectsFiltered);

        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @GetMapping("/groups-subjects")
    public ResponseEntity<Object> subjectsWithMoreThanOneGroup() {
        try {
            UptcList<Subject> subjectsFiltered;
            subjectsFiltered = loaderService.subjectsWithMultipleGroups();
            return ResponseEntity.status(HttpStatus.OK).body(subjectsFiltered);

        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @GetMapping("/subjects-schedules")
    public ResponseEntity<Object> subjectsWithSameSchedules() {
        try {
            UptcList<Subject> subjectsFiltered;
            subjectsFiltered = loaderService.subjectsWithSameSchedule();
            return ResponseEntity.status(HttpStatus.OK).body(subjectsFiltered);

        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

}
