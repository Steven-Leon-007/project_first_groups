package com.estivman.projects.first.project_first_groups.dtos;

import java.io.Serializable;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Group;
import com.estivman.secondproject.DynamicMemory.UptcList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto implements Serializable {
    private String subjectGroupCode;
    private String placeGroupId;
    private UptcList<String> groupSchedules;

    public static GroupDto fromGroup(Group group) {
        GroupDto dto = new GroupDto();
        dto.setPlaceGroupId(group.getPlaceGroupId());
        dto.setSubjectGroupCode(group.getSubjectGroupCode());
        dto.setGroupSchedules(group.getGroupSchedules());
        return dto;
    }

    public static Group fromGroupDto(GroupDto dto) {
        Group group = new Group();
        group.setPlaceGroupId(dto.getPlaceGroupId());
        group.setSubjectGroupCode(dto.getSubjectGroupCode());
        group.setGroupSchedules(dto.getGroupSchedules());
        return group;
    }

    public static UptcList<GroupDto> fromGroup(UptcList<Group> group) {
        UptcList<GroupDto> groupDto = new UptcList<>();
        for (Group groupSingle : group) {
            groupDto.add(GroupDto.fromGroup(groupSingle));
        }
        return groupDto;
    }

    public static void validateGroup(GroupDto dto) throws ProjectException {
        if (dto.getPlaceGroupId() == null || dto.getSubjectGroupCode() == null || dto.getGroupSchedules() == null ||
                dto.getPlaceGroupId().isEmpty() || dto.getSubjectGroupCode().isEmpty()
                || dto.getGroupSchedules().isEmpty()) {
            throw new ProjectException(ExceptionType.INFORMATION_INCOMPLETE);
        }
    }
}
