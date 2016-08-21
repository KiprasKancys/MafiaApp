package com.example.kipras.newmafija.app;

public class Options {
    private String role;
    private String number;

    public Options(String role, String number) {
        this.role = role;
        this.number = number;
    }

    public String getRole(){
        return role;
    }

    public String getNumber(){
        return number;
    }
}