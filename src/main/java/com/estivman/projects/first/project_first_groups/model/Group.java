package com.estivman.projects.first.project_first_groups.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group implements Serializable {
    private String subjectGroupCode;
    private String placeGroupId;
    private String groupSchedules;
}
