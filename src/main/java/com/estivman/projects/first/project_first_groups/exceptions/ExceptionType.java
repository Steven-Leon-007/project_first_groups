package com.estivman.projects.first.project_first_groups.exceptions;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "The requested object was not found", 404),
    NOT_SAVED(HttpStatus.BAD_REQUEST.value(), "The requested file could not be saved", 410),
    OBJECT_DELETE_NOT_ALLOWED(HttpStatus.CONFLICT.value(), "The requested object can't be deleted", 409),
    INFORMATION_INCOMPLETE(HttpStatus.BAD_REQUEST.value(), "One or more fields in the request are incomplete", 430),
    NOT_FOUND_FILE(HttpStatus.BAD_REQUEST.value(), "The requested file was not found", 420),

    SAVE(HttpStatus.OK.value(), "Object saved successfully", 210);

    public final String message;
    public final int code;
    public final int codeHttp;

    private ExceptionType(int codeHttp, String message, int code) {
        {
            this.message = message;
            this.code = code;
            this.codeHttp = codeHttp;
        }

    }
}
