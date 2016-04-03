package com.goqual.mercury.data.local;

/**
 * Created by ladmusician on 2/23/16.
 */
public class FeedDTO {
    private int _feedid;
    private int id;
    private String title;
    private String period;
    private String main_img_url;
    private String people;

    public FeedDTO() {}

    public FeedDTO(int feedId) {
        this._feedid = feedId;
    }

    public FeedDTO(int _feedid, String title, String period) {
        this._feedid = _feedid;
        this.title = title;
        this.period = period;
    }

    public FeedDTO(int _feedid, String title, String period, String people) {
        this._feedid = _feedid;
        this.title = title;
        this.period = period;
        this.people = people;
    }

    public int get_feedid() {
        return _feedid;
    }

    public void set_feedid(int _feedid) {
        this._feedid = _feedid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMain_img_url() {
        return main_img_url;
    }

    public void setMain_img_url(String main_img_url) {
        this.main_img_url = main_img_url;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
