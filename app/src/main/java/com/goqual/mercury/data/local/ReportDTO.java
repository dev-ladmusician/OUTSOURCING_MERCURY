package com.goqual.mercury.data.local;

/**
 * Created by ladmusician on 2/23/16.
 */
public class ReportDTO {
    private int _reportid;
    private int id;
    private int member;
    private String title;
    private String location;
    private String content;
    private String date;
    private String imag_url;

    public int get_reportid() {
        return _reportid;
    }

    public void set_reportid(int _reportid) {
        this._reportid = _reportid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImag_url() {
        return imag_url;
    }

    public void setImag_url(String imag_url) {
        this.imag_url = imag_url;
    }
}
