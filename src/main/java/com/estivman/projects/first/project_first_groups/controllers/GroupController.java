package com.estivman.projects.first.project_first_groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.services.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {
    
    @Autowired
    private GroupService groupService;
}
