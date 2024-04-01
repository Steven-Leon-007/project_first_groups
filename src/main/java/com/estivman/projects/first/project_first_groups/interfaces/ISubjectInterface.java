package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.secondproject.DynamicMemory.UptcList;

public interface ISubjectInterface {
    public UptcList<Subject> getSubjects() throws ProjectException;

    public void addSubject(Subject subject) throws ProjectException;

    public UptcList<Subject> updateSubject(String searchField, String searchValue, Subject subjectUpdated)
            throws ProjectException;

    public UptcList<Subject> removeSubject(Subject subjectSearched) throws ProjectException;

    public UptcList<Subject> deleteSubjectThroughParam(String searchField, String searchValue)
            throws ProjectException;

}
