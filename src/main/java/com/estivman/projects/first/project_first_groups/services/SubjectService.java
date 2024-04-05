package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.ISubjectInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

@Service
public class SubjectService implements ISubjectInterface {
    private final GroupService groupService;
    private UptcList<Subject> subjects = new UptcList<Subject>();

    @Autowired
    public SubjectService(GroupService groupService) {
        this.groupService = groupService;
    }

    public UptcList<Subject> getSubjects() throws ProjectException {
        try {
            return subjects;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }

    public void addSubject(Subject subject) throws ProjectException {
        try {
            subjects.add(subject);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }


    public UptcList<Subject> updateSubject(Subject subjectSearched, Subject subjectUpdated)
            throws ProjectException {
        try {

            for (int i = 0; i < subjects.size(); i++) {
                if (subjectMatch(subjectSearched, subjectUpdated)) {
                    subjects.set(i, subjectUpdated);
                    return subjects;
                }
            }
            return subjects;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND);
        }
    }

    public UptcList<Subject> removeSubject(Subject subjectSearched) throws ProjectException {

        try {
            for (Subject subject : subjects) {
                if (subjectMatch(subjectSearched, subject) && !isAttachedToGroup(subject)) {
                    subjects.remove(subject);
                    return subjects;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.OBJECT_DELETE_NOT_ALLOWED);
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

}
