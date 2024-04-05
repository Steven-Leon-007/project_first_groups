package com.estivman.projects.first.project_first_groups.model;

import java.io.Serializable;

import com.estivman.uptc_list_library.DynamicMemory.UptcList;

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
public class Group implements Serializable {
    private String subjectGroupCode;
    private String placeGroupId;
    private UptcList<String> groupSchedules;
}
