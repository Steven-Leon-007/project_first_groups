package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;

@Service
public class LoaderService {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final PlaceService placeService;

    @Autowired
    public LoaderService(GroupService groupService, SubjectService subjectService, PlaceService placeService) {
        this.placeService = placeService;
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    public void loadAllServices() throws ProjectException{
        groupService.getGroups();
        subjectService.getSubjects();
        placeService.getPlaces();
    }

}
