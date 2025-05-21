package com.pc.task_management_system.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("Username already exists!");
    }
}
