package com.pc.task_management_system.exception;

public class EmailIdAlreadyExistsException extends RuntimeException{
    public EmailIdAlreadyExistsException(){
        super("Email ID already exists!");
    }
}
