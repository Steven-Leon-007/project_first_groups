package com.estivman.projects.first.project_first_groups.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Place implements Serializable {
    private String placeId;
    private String placeName;
    private String placeAddress;
}
