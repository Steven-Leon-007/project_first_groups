package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;
import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.ISubjectInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.secondproject.DynamicMemory.UptcList;

@Service
public class SubjectService implements ISubjectInterface {
    private final JsonFunctions jsonFunctions;
    private final GroupService groupService;
    private UptcList<Subject> subjects = new UptcList<Subject>();

    @Autowired
    public SubjectService(@Value("${json.subject.path}") String subjectPath,
            @Value("${json.subject.root-element}") String subjectRootElement,
            GroupService groupService) {
        this.jsonFunctions = new JsonFunctions(subjectPath, subjectRootElement);
        this.groupService = groupService;
    }

    public UptcList<Subject> getSubjects() throws ProjectException {
        try {
            subjects = jsonFunctions.getFromJSON(Subject.class);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        return subjects;
    }

    public void addSubject(Subject subject) throws ProjectException {
        try {
            subjects.add(subject);
            jsonFunctions.addObjectToJSON(subject);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }

    public UptcList<Subject> updateSubject(String searchField, String searchValue, Subject subjectUpdated)
            throws ProjectException {
        try {
            subjects = jsonFunctions.getFromJSON(Subject.class);
            if (subjectUpdated != null) {
                subjects = jsonFunctions.putInJSON(subjects, searchField, searchValue, subjectUpdated);
            }
            return subjects;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    public UptcList<Subject> removeSubject(Subject subjectSearched) throws ProjectException {

        try {
            subjects = jsonFunctions.getFromJSON(Subject.class);
            for (Subject subject : subjects) {
                if (subjectMatch(subjectSearched, subject) && !isAttachedToGroup(subject)) {
                    subjects.remove(subject);
                    jsonFunctions.postInJSON(subjects);
                    return subjects;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.OBJECT_DELETE_NOT_ALLOWED);
    }

    public UptcList<Subject> deleteSubjectThroughParam(String searchField, String searchValue)
            throws ProjectException {
        try {
            subjects = jsonFunctions.getFromJSON(Subject.class);
            if (!isAttachedToGroup(subjectExists(searchValue))) {
                subjects = jsonFunctions.deleteFromJSON(subjects, searchField, searchValue);
                return subjects;
            }
            throw new ProjectException(ExceptionType.NOT_FOUND);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    private boolean isAttachedToGroup(Subject subject) throws ProjectException {
        UptcList<Group> groupList = new UptcList<Group>();
        try {
            groupList = groupService.getGroups();
            for (Group group : groupList) {
                if (group.getSubjectGroupCode().equals(subject.getSubjectCode())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
        return false;
    }

    private boolean subjectMatch(Subject subjectSearched, Subject subject) {
        return subjectSearched.getSubjectName().equals(subject.getSubjectName())
                && subjectSearched.getSubjectCode().equals(subject.getSubjectCode());
    }

    private Subject subjectExists(String searchValue) {
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equals(searchValue) || subject.getSubjectCode().equals(searchValue)) {
                return subject;
            }
        }
        return null;
    }

}
