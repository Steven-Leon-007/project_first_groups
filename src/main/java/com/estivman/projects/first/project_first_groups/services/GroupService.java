package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;
import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.IGroupInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.secondproject.DynamicMemory.UptcList;

@Service
public class GroupService implements IGroupInterface {
    private final JsonFunctions jsonFunctions;
    private final PlaceService placeService;
    private final SubjectService subjectService;
    private UptcList<Group> groups = new UptcList<Group>();

    @Autowired
    public GroupService(@Value("${json.group.path}") String groupPath,
            @Value("${json.group.root-element}") String groupRootElement, @Lazy PlaceService placeService,
            @Lazy SubjectService subjectService) {
        this.jsonFunctions = new JsonFunctions(groupPath, groupRootElement);
        this.placeService = placeService;
        this.subjectService = subjectService;
    }

    public UptcList<Group> getGroups() throws ProjectException {
        try {
            groups = jsonFunctions.getFromJSON(Group.class);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        return groups;
    }

    public Group addGroup(Group group) throws ProjectException {
        try {
            // So, for add a group must exists the subject and the place too
            // TODO: FALTA VALIDAR HORARIOS
            String place = group.getPlaceGroupId();
            String subject = group.getSubjectGroupCode();

            if(validatePlaceExist(place) && !validatePlaceAssigned(place) && doesSubjectExist(subject)){
                groups.add(group);
                jsonFunctions.addObjectToJSON(group);
                return group;
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_SAVED);
    }

    private boolean doesSubjectExist(String targetSubjectCode) throws ProjectException {
        for (Subject subject : subjectService.getSubjects()) {
            if (subject.getSubjectCode().equals(targetSubjectCode))
                return true;
        }
        return false;
    }

    private boolean validatePlaceExist(String targetPlace) throws ProjectException {
        for (Place place : placeService.getPlaces()) {
            if(place.getPlaceId().equals(targetPlace)){
                return true;
            }
        }
        return false;
    }

    private boolean validatePlaceAssigned(String placeSearched){
        for (Group group : groups) {
            if(group.getPlaceGroupId().equals(placeSearched)){
                return true;
            }
        }
        return false;
    }
}
