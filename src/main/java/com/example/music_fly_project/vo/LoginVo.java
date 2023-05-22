package com.example.music_fly_project.vo;

public class LoginVo {
    private Long id;
    private String pass;
    private String ip;
    private boolean sec;
    private String secPass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isSec() {
        return sec;
    }

    public void setSec(boolean sec) {
        this.sec = sec;
    }

    public String getSecPass() {
        return secPass;
    }

    public void setSecPass(String secPass) {
        this.secPass = secPass;
    }
}
