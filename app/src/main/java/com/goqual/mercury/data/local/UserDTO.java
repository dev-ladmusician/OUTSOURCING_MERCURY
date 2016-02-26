package com.goqual.mercury.data.local;

/**
 * Created by ladmusician on 2/23/16.
 */
public class UserDTO {
    private int _userid;
    private int id;
    private String username;
    private String name;
    private String password;
    private String phone;

    public UserDTO(int _userid) {
        this._userid = _userid;
    }

    public UserDTO(String username, String name, String password, String phone) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(int userId, String name) {
        this._userid = userId;
        this.name = name;
    }

    public int get_userid() {
        return _userid;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
