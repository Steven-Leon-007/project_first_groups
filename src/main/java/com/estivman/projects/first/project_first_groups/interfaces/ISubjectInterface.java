package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

public interface ISubjectInterface {

        public UptcList<Subject> getSubjects() throws ProjectException;

        public void addSubject(Subject subject) throws ProjectException;


        public UptcList<Subject> updateSubject(Subject subjectSearched, Subject subjectUpdated)
            throws ProjectException;

        public UptcList<Subject> removeSubject(Subject subjectSearched) throws ProjectException;


}
