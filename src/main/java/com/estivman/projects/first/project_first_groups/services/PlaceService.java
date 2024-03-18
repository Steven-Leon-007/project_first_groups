package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;

@Service
public class PlaceService {
    private final JsonFunctions jsonFunctions;

    @Autowired
    public PlaceService(@Value("{json.place.path}") String jsonPlacePath,
            @Value("{json.place.root-element}") String jsonPlaceRootElement) {
        this.jsonFunctions = new JsonFunctions(jsonPlacePath, jsonPlaceRootElement);
    }
}
