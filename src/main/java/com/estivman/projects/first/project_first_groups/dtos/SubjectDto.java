package com.estivman.projects.first.project_first_groups.dtos;

import java.io.Serializable;

import com.estivman.projects.first.project_first_groups.exceptions.ExceptionType;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.uptc_list_library.DynamicMemory.UptcList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDto implements Serializable {
    private String subjectName;
    private String subjectCode;

    public static SubjectDto fromSubject(Subject subject) {
        SubjectDto dto = new SubjectDto();
        dto.setSubjectName(subject.getSubjectName());
        dto.setSubjectCode(subject.getSubjectCode());
        return dto;
    }

    public static Subject fromSubjectDto(SubjectDto dto) {
        Subject subject = new Subject();
        subject.setSubjectName(dto.getSubjectName());
        subject.setSubjectCode(dto.getSubjectCode());
        return subject;
    }

    public static UptcList<SubjectDto> fromSubject(UptcList<Subject> subject) {
        UptcList<SubjectDto> subjectDto = new UptcList<SubjectDto>();
        for (Subject subjectSingle : subject) {
            subjectDto.add(SubjectDto.fromSubject(subjectSingle));
        }
        return subjectDto;
    }

    public static void validateSubject(SubjectDto subjectDto) throws ProjectException {
        if (subjectDto.getSubjectName() == null || subjectDto.getSubjectCode() == null
                || subjectDto.getSubjectName().isEmpty() || subjectDto.getSubjectCode().isEmpty()) {
            throw new ProjectException(ExceptionType.INFORMATION_INCOMPLETE);
        }
    }
}
