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

    public Group addGroup(Group groupAdded) throws ProjectException {
        try {
            // So, for add a group must exists the subject and the place too

            String place = groupAdded.getPlaceGroupId();
            String subject = groupAdded.getSubjectGroupCode();
            boolean isScheduleAlready = false;

            for (Group group : groups) {
                if (isScheduleSame(group.getGroupSchedules(), groupAdded.getGroupSchedules())) {
                    isScheduleAlready = true;
                }
            }

            if (doesSubjectExist(subject) && (validatePlaceExist(place) || !isScheduleAlready)) {
                groups.add(groupAdded);
                jsonFunctions.addObjectToJSON(groupAdded);
                return groupAdded;
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_SAVED);
    }

    public Group deleteGroup(Group groupToDelete) throws ProjectException {
        try {
            groups = jsonFunctions.getFromJSON(Group.class);
            // to delete a group is not necessary validate anything
            // actually, just it's existence
            for (Group group : groups) {
                if (doesGroupExists(group, groupToDelete)) {
                    groups.remove(group);
                    jsonFunctions.postInJSON(groups);
                    return group;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);

        }
        throw new ProjectException(ExceptionType.NOT_FOUND);
    }

    public UptcList<Group> editGroup(Group groupSearched, Group groupUpdated)
            throws ProjectException {

        String place = groupUpdated.getPlaceGroupId();
        String subject = groupUpdated.getSubjectGroupCode();
        boolean isScheduleAlready = false;
        int index = 0;
        try {
            groups = jsonFunctions.getFromJSON(Group.class);
            for (Group group : groups) {
                if (isScheduleSame(group.getGroupSchedules(), groupUpdated.getGroupSchedules())) {
                    isScheduleAlready = true;
                }
            }
            for (Group group : groups) {
                if (doesGroupExists(group, groupSearched) && doesSubjectExist(subject)
                        && (validatePlaceExist(place) || !isScheduleAlready)) {
                    groups.set(index, groupUpdated);
                    jsonFunctions.postInJSON(groups);
                    return groups;
                }
                index++;
            }

        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);
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
            if (place.getPlaceId().equals(targetPlace) && !validatePlaceAssigned(targetPlace)) {
                return true;
            }
        }
        return false;
    }

    private boolean validatePlaceAssigned(String placeSearched) {
        for (Group group : groups) {
            if (group.getPlaceGroupId().equals(placeSearched)) {
                return true;
            }
        }
        return false;
    }

    private boolean isScheduleSame(UptcList<String> scheduleSaved, UptcList<String> scheduleSearched) {

        if (scheduleSaved.size() != scheduleSearched.size()) {
            return false;
        }

        for (int i = 0; i < scheduleSearched.size(); i++) {
            if (!scheduleSearched.get(i).equals(scheduleSaved.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean doesGroupExists(Group group, Group groupSearched) {
        return group.getSubjectGroupCode().equals(groupSearched.getSubjectGroupCode())
                && group.getPlaceGroupId().equals(groupSearched.getPlaceGroupId())
                && isScheduleSame(group.getGroupSchedules(), groupSearched.getGroupSchedules());
    }

}
