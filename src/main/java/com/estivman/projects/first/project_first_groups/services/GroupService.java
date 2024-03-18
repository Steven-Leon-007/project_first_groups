package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;

@Service
public class GroupService {
    private final JsonFunctions jsonFunctions;

    @Autowired
    public GroupService(@Value("{json.group.path}") String jsonGroupPath,
            @Value("{json.group.root-element}") String jsonGroupRootElement) {
        this.jsonFunctions = new JsonFunctions(jsonGroupPath, jsonGroupRootElement);
    }
}
