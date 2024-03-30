package com.estivman.projects.first.project_first_groups.exceptions;

public class ProjectException extends Exception {
    private ExceptionType exceptionType;

    public ProjectException(ExceptionType exceptionType) {
        super(exceptionType.message);
        this.exceptionType = exceptionType;
    }

    public Message getMenssage() {
        return new Message(this.exceptionType.code,
                this.exceptionType.message,
                this.exceptionType.codeHttp);
    }

}
