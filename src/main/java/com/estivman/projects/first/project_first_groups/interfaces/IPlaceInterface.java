package com.estivman.projects.first.project_first_groups.interfaces;

import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.secondproject.DynamicMemory.UptcList;

public interface IPlaceInterface {
    public UptcList<Place> loadPlaces() throws ProjectException;

    public UptcList<Place> getPlaces() throws ProjectException;

    public void addPlace(Place place) throws ProjectException;

    public UptcList<Place> removePlace(Place placeSearched) throws ProjectException;

    public UptcList<Place> updatePlace(Place placeSearched, Place placeUpdated) throws ProjectException;

    public UptcList<Place> updatePlaceThroughParam(String searchField, String searchValue, Place placeUpdated)
            throws ProjectException;

    public UptcList<Place> deletePlaceThroughParam(String searchField, String searchValue) throws ProjectException;

}
