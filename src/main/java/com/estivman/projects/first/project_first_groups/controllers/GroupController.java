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

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.services.GroupService;
import com.estivman.secondproject.DynamicMemory.UptcList;

@RestController
@RequestMapping("/group")
public class GroupController {
    
    @Autowired
    private GroupService groupService;

    @GetMapping()
    public ResponseEntity<Object> getGroups() {
        UptcList<Group> groups;
        try {
            groups = groupService.getGroups();
            return ResponseEntity.status(HttpStatus.OK).body(groups);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> postGroup(@RequestBody Group group) {
        try {
            groupService.addGroup(group);
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putGroup(@RequestBody Group group) {
        // try {
        //     // service.addcity(CityDto.fromCityDto(cityDto1));
            return ResponseEntity.status(HttpStatus.OK).body(group);
        // } catch (ProjectException e) {
        //     return ResponseEntity.status(e.getMenssage().getCodeHttp())
        //             .body(e.getMenssage());
        // }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteGroup(@RequestBody Group group) {
        // try {
        //     // service.removeCity(CityDto.fromCityDto(cityDto1));
            return ResponseEntity.status(HttpStatus.OK).body(group);
        // } catch (ProjectException e) {
        //     return ResponseEntity.status(e.getMenssage().getCodeHttp())
        //             .body(e.getMenssage());
        // }
    }
}
