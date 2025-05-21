package com.pc.task_management_system.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(){
        super("Username not found.");
    }
}
