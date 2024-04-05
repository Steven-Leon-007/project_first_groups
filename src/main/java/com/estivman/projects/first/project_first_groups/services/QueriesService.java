package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.IQueriesInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;


@Service
public class QueriesService implements IQueriesInterface {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private UptcList<Group> groups;
    private UptcList<Subject> subjects;

    @Autowired
    public QueriesService(GroupService groupService, SubjectService subjectService) {
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    public UptcList<Subject> subjectsWithSamePlace() throws ProjectException {

        UptcList<Subject> subjectsFiltered = new UptcList<>();
        groups = groupService.getGroups();
        int amount = 0;
        try {
            for (int i = 0; i < groups.size(); i++) {
                for (int j = 0; j < groups.size(); j++) {
                    if (groups.get(i).getPlaceGroupId().equals(groups.get(j).getPlaceGroupId())) {
                        amount++;
                    }
                    if (amount >= 2
                            && !subjectsFiltered.contains(getSubjectFromCode(groups.get(i).getSubjectGroupCode()))) {
                        subjectsFiltered.add(getSubjectFromCode(groups.get(i).getSubjectGroupCode()));
                    }
                }
                amount = 0;
            }
            return subjectsFiltered;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    public UptcList<Subject> searchSubjectsWithSamePlace(Place placeSeached) throws ProjectException {
        UptcList<Group> groupsFiltered = new UptcList<>();
        UptcList<Subject> subjectsFiltered = new UptcList<>();
        try {
            groups = groupService.getGroups();

            for (Group group : groups) {
                // Search all coincidences of the place searched
                if (group.getPlaceGroupId().equals(placeSeached.getPlaceId())) {
                    groupsFiltered.add(group);
                }
            }

            for (int i = 0; i < groupsFiltered.size(); i++) {
                for (int j = 0; j < groupsFiltered.size(); j++) {
                    if (validateSubject(groupsFiltered, subjectsFiltered, i, j)) {
                        subjectsFiltered.add(getSubjectFromCode(groupsFiltered.get(i).getSubjectGroupCode()));
                    }
                }
            }
            return subjectsFiltered;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    public UptcList<Subject> subjectsWithMultipleGroups() throws ProjectException {
        try {

            groups = groupService.getGroups();
            subjects = subjectService.getSubjects();

            UptcList<Subject> subjectsTmp = subjects;
            UptcList<Subject> subjectsFounded = new UptcList<Subject>();
            UptcList<Group> groupsTmp = groups;
            int amount = 0;
            for (Subject subject : subjectsTmp) {
                for (Group group : groupsTmp) {
                    if (group.getSubjectGroupCode().equals(subject.getSubjectCode())) {
                        amount++;
                    }
                    if (amount >= 2 && !subjectsFounded.contains(subject)) {
                        subjectsFounded.add(subject);
                    }
                }
                amount = 0;
            }
            return subjectsFounded;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    public UptcList<Subject> subjectsWithSameSchedule() throws ProjectException {
        try {
            groups = groupService.getGroups();

            UptcList<Subject> subjectsFounded = new UptcList<Subject>();
            UptcList<Group> groupsTmp = groups;
            int amount = 0;

            for (int i = 0; i < groupsTmp.size(); i++) {
                for (int j = 0; j < groupsTmp.size(); j++) {
                    if (isScheduleSame(groupsTmp.get(i).getGroupSchedules(), groupsTmp.get(j).getGroupSchedules())) {
                        amount++;
                    }
                    if (amount >= 2
                            && !subjectsFounded.contains(getSubjectFromCode(groupsTmp.get(i).getSubjectGroupCode()))) {
                        subjectsFounded.add(getSubjectFromCode(groupsTmp.get(i).getSubjectGroupCode()));
                    }
                }
                amount = 0;
            }

            return subjectsFounded;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);

        }
    }

    private Subject getSubjectFromCode(String code) throws ProjectException {
        subjects = subjectService.getSubjects();
        for (Subject subject : subjects) {
            if (subject.getSubjectCode().equals(code)) {
                return subject;
            }

        }
        return null;
    }

    private boolean validateSubject(UptcList<Group> groupsFiltered, UptcList<Subject> subjectsFiltered, int i, int j)
            throws ProjectException {
        return groupsFiltered.get(i).getSubjectGroupCode().equals(groupsFiltered.get(j).getSubjectGroupCode())
                && !subjectsFiltered.contains(getSubjectFromCode(groupsFiltered.get(j).getSubjectGroupCode()));
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

}
