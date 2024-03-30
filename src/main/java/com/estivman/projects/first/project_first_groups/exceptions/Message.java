package com.estivman.projects.first.project_first_groups.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    private int code;
    private String message;
    private int codeHttp;
    
}
