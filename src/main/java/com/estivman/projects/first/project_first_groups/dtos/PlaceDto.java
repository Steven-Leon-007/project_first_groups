package com.estivman.projects.first.project_first_groups.dtos;

import java.io.Serializable;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Place;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto implements Serializable {
    private String placeId;
    private String placeName;
    private String placeAddress;

    public static PlaceDto fromPlace(Place place) {
        PlaceDto dto = new PlaceDto();
        dto.setPlaceId(place.getPlaceId());
        dto.setPlaceAddress(place.getPlaceAddress());
        dto.setPlaceName(place.getPlaceName());
        return dto;
    }

    public static Place fromPlaceDto(PlaceDto dto) {
        Place place = new Place();
        place.setPlaceId(dto.getPlaceId());
        place.setPlaceAddress(dto.getPlaceAddress());
        place.setPlaceName(dto.getPlaceName());
        return place;
    }

    public static UptcList<PlaceDto> fromPlace(UptcList<Place> place) {
        UptcList<PlaceDto> placeDto = new UptcList<PlaceDto>();
        for (Place placeSingle : place) {
            placeDto.add(PlaceDto.fromPlace(placeSingle));
        }
        return placeDto;
    }

    public static void validatePlace(PlaceDto placeDto) throws ProjectException {
        if (placeDto.getPlaceAddress() == null || placeDto.getPlaceId() == null || placeDto.getPlaceName() == null ||
                placeDto.getPlaceAddress().isEmpty() || placeDto.getPlaceId().isEmpty()
                || placeDto.getPlaceName().isEmpty()) {
            throw new ProjectException(ExceptionType.INFORMATION_INCOMPLETE);
        }
    }
}
