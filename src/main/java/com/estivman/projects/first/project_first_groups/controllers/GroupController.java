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

import com.estivman.projects.first.project_first_groups.dtos.GroupDto;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.services.GroupService;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

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
    public ResponseEntity<Object> postGroup(@RequestBody GroupDto dto) {
        try {
            GroupDto.validateGroup(dto);
            Group group = GroupDto.fromGroupDto(dto);
            groupService.addGroup(group);
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putGroup(@RequestBody UptcList<GroupDto> groupsDto) {
        try {
            if(groupsDto.size() != 2){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number of groups specified");
            }
            GroupDto.validateGroup(groupsDto.get(0));
            GroupDto.validateGroup(groupsDto.get(1));

            Group groupSearched = GroupDto.fromGroupDto(groupsDto.get(0));
            Group groupUpdated = GroupDto.fromGroupDto(groupsDto.get(1));

            groupService.editGroup(groupSearched, groupUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(groupUpdated);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteGroup(@RequestBody GroupDto dto) {
        try {
            GroupDto.validateGroup(dto);
            Group group = GroupDto.fromGroupDto(dto);
            groupService.deleteGroup(group);
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }
}
