package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

public interface IQueriesInterface {

    public UptcList<Subject> subjectsWithSamePlace() throws ProjectException;

    public UptcList<Subject> searchSubjectsWithSamePlace(Place placeSeached) throws ProjectException;

    public UptcList<Subject> subjectsWithMultipleGroups() throws ProjectException;

    public UptcList<Subject> subjectsWithSameSchedule() throws ProjectException;
}
