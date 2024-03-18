package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;

@Service
public class SubjectService {
    private final JsonFunctions jsonFunctions;

    @Autowired
    public SubjectService(@Value("{json.subject.path}") String jsonSubjectPath,
            @Value("{json.subject.root-element}") String jsonSubjectRootElement) {
        this.jsonFunctions = new JsonFunctions(jsonSubjectPath, jsonSubjectRootElement);
    }

}
