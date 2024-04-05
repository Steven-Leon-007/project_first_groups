package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

public interface IGroupInterface {
    public UptcList<Group> getGroups() throws ProjectException;

    public Group addGroup(Group group) throws ProjectException;

    public Group deleteGroup(Group groupToDelete) throws ProjectException;

    public UptcList<Group> editGroup(Group groupSearched, Group groupUpdated) throws ProjectException;
}
