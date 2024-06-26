package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

public interface IPlaceInterface {

    public UptcList<Place> getPlaces() throws ProjectException;

    public void addPlace(Place place) throws ProjectException;

    public UptcList<Place> removePlace(Place placeSearched) throws ProjectException;

    public UptcList<Place> updatePlace(Place placeSearched, Place placeUpdated) throws ProjectException;


}
