package com.estivman.projects.first.project_first_groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.services.LoaderService;

@RestController
@RequestMapping("/load")
public class LoaderController {

    @Autowired
    private LoaderService loaderService;

    @GetMapping()
    public ResponseEntity<Object> loadData() {
        try {
            loaderService.loadAllServices();
            return ResponseEntity.status(HttpStatus.OK).body("All data loaded successfully");
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }
}
