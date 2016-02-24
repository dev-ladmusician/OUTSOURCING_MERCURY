package com.goqual.mercury.data.local;

/**
 * Created by ladmusician on 2/23/16.
 */
public class FeedDTO {
    private int _feedid;
    private int id;
    private String title;
    private String period;

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
}
