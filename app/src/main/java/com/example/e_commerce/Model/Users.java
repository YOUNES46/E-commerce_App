package com.example.e_commerce.Model;

public class Users {
    private String name,phone,password;

    public Users(){

    }
    public Users(String nom,String por,String pas){
        this.name =nom;
        this.phone =por;
        this.password=pas;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
