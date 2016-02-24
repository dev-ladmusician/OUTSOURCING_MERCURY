package com.goqual.mercury.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ladmusician on 2/24/16.
 */
public class Report extends RealmObject {
    @PrimaryKey
    private int id;
    private int _reportid;
    private int member;
    private String title;
    private String location;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int get_reportid() {
        return _reportid;
    }

    public void set_reportid(int _reportid) {
        this._reportid = _reportid;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
