package com.estivman.projects.first.project_first_groups.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.interfaces.IPlaceInterface;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

@Service
public class PlaceService implements IPlaceInterface {
    private final GroupService groupService;
    private UptcList<Place> places = new UptcList<Place>();
    @Autowired
    public PlaceService(GroupService groupService) {
        this.groupService = groupService;
    }


    public UptcList<Place> getPlaces() throws ProjectException {
        try {
            return places;
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }

    public void addPlace(Place place) throws ProjectException {
        try {
            places.add(place);
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
    }

    public UptcList<Place> removePlace(Place placeSearched) throws ProjectException {
        try {
            for (Place place : places) {
                if (doesPlaceMatch(placeSearched, place) && !isAttachedToGroup(place)) {
                    places.remove(place);
                    return places;
                }
            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    public UptcList<Place> updatePlace(Place placeSearched, Place placeUpdated) throws ProjectException {
        try {
            int index = 0;
            for (Place place : places) {
                if (doesPlaceMatch(placeSearched, place) && placeSearched != null) {
                    places.set(index, placeUpdated);
                    return places;
                }
                index++;

            }
        } catch (Exception e) {
            throw new ProjectException(ExceptionType.NOT_FOUND_FILE);
        }
        throw new ProjectException(ExceptionType.NOT_FOUND);

    }

    private boolean isAttachedToGroup(Place place) throws ProjectException {
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

    private boolean doesPlaceMatch(Place placeSearched, Place place) {
        return placeSearched.getPlaceId().equals(place.getPlaceId())
                && placeSearched.getPlaceAddress().equals(place.getPlaceAddress())
                && placeSearched.getPlaceName().equals(place.getPlaceName());
    }
}
