package com.example.dietideals24.models;

public class ProfiloCheck {

    String nickname;
    String password;

    public ProfiloCheck(String nickname, String password) {

        this.nickname = nickname;
        this.password = password;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean check() {
        if(nickname.isEmpty()){
            return false;
        } else if (nickname.length() > 30){
            return false;
        }
        if(password.isEmpty()){
            return false;
        } else if(password.length() > 50){
            return false;
        }
        return true;
    }

}
