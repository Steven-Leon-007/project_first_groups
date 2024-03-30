package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estivman.json.json_library.Services.JsonFunctions;
import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.IPlaceInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.secondproject.DynamicMemory.UptcList;

@Service
public class PlaceService implements IPlaceInterface {
    private final JsonFunctions jsonFunctions;
    private final GroupService groupService;
    private UptcList<Place> places = new UptcList<Place>();

    @Autowired
    public PlaceService(@Value("${json.place.path}") String placePath,
            @Value("${json.place.root-element}") String placeRootElement, GroupService groupService) {
        this.jsonFunctions = new JsonFunctions(placePath, placeRootElement);
        this.groupService = groupService;
    }
    
    public UptcList<Place> getPlaces() throws ProjectException {
        try {
            places = jsonFunctions.getFromJSON(Place.class);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        return places;
    }

    public void addPlace(Place place) throws ProjectException {
        try {
            places.add(place);
            jsonFunctions.addObjectToJSON(place);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }

    public UptcList<Place> removePlace(Place placeSearched) throws ProjectException {
        try {
            places = jsonFunctions.getFromJSON(Place.class);
            for (Place place : places) {
                if (doesPlaceMatch(placeSearched, place) && !isAttachedToGroup(place)) {
                    places.remove(place);
                    jsonFunctions.postInJSON(places);
                    return places;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    public UptcList<Place> updatePlace(Place placeSearched) throws ProjectException {
        try {
            places = jsonFunctions.getFromJSON(Place.class);
            for (Place place : places) {
                if (doesPlaceMatch(placeSearched, place) && placeSearched != null) {
                    place = placeSearched;
                    jsonFunctions.postInJSON(places);
                    return places;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    public UptcList<Place> updatePlaceThroughParam(String searchField, String searchValue, Place placeUpdated)
            throws ProjectException {
        try {
            places = jsonFunctions.getFromJSON(Place.class);
            places = jsonFunctions.putInJSON(places, searchField, searchValue, placeUpdated);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    public UptcList<Place> deletePlaceThroughParam(String searchField, String searchValue) throws ProjectException {
        try {
            places = jsonFunctions.getFromJSON(Place.class);
            places = jsonFunctions.deleteFromJSON(places, searchField, searchValue);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    public boolean isAttachedToGroup(Place place) throws ProjectException {
        UptcList<Group> groupList = new UptcList<Group>();
        try {
            groupList = groupService.getGroups();
            for (Group group : groupList) {
                if (group.getPlaceGroupId().equals(place.getPlaceId())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        return false;
    }

    public boolean doesPlaceMatch(Place placeSearched, Place place) {
        return placeSearched.getPlaceId().equals(place.getPlaceId())
                && placeSearched.getPlaceAddress().equals(place.getPlaceAddress())
                && placeSearched.getPlaceName().equals(place.getPlaceName());
    }
}
