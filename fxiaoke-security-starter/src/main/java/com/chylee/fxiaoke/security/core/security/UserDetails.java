package com.chylee.fxiaoke.security.core.security;

public class UserDetails implements User {
    private int id;
    private int deptId;
    private String username;
    private String password;
    private String nickname;

    public UserDetails(int id, int deptId, String username, String nickname, String password) {
        this.id = id;
        this.deptId = deptId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public int getDeptId() {
        return deptId;
    }

    @Override
    public void erasePassword() {
        password = null;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
