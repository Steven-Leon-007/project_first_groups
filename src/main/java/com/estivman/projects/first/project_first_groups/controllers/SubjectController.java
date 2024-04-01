package com.estivman.projects.first.project_first_groups.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estivman.projects.first.project_first_groups.dtos.SubjectDto;
import com.estivman.projects.first.project_first_groups.exceptions.ProjectException;
import com.estivman.projects.first.project_first_groups.model.Subject;
import com.estivman.projects.first.project_first_groups.services.SubjectService;
import com.estivman.secondproject.DynamicMemory.UptcList;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    
    @GetMapping()
    public ResponseEntity<Object> getSubjects() {
        UptcList<Subject> subjects;
        try {
            subjects = subjectService.getSubjects();
            return ResponseEntity.status(HttpStatus.OK).body(subjects);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> postSubject(@RequestBody SubjectDto subjectDto) {
        try {
            SubjectDto.validateSubject(subjectDto);
            Subject subject = SubjectDto.fromSubjectDto(subjectDto);
            subjectService.addSubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }



    @PutMapping()
    public ResponseEntity<Object> putSubject(@RequestParam String searchField, @RequestParam String searchValue,
            @RequestBody SubjectDto subjectDto) {
        try {
            SubjectDto.validateSubject(subjectDto);
            Subject subjectUpdated = SubjectDto.fromSubjectDto(subjectDto);

            subjectService.updateSubject(searchField, searchValue, subjectUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(subjectUpdated);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteSubject(@RequestBody SubjectDto subjectDto) {
        try {
            SubjectDto.validateSubject(subjectDto);
            Subject subject = SubjectDto.fromSubjectDto(subjectDto);
            subjectService.removeSubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }

    @DeleteMapping("/deleteSubjectParam")
    public ResponseEntity<Object> deleteSubjectParam(@RequestParam String searchField,
            @RequestParam String searchValue) {
        try {
            subjectService.deleteSubjectThroughParam(searchField, searchValue);
            return ResponseEntity.status(HttpStatus.OK).body("Element deleted successfully");
        } catch (ProjectException e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }
    }
}
