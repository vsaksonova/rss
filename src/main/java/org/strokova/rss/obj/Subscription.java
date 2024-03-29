package org.strokova.rss.obj;

/**
 * @author vstrokova, 03.08.2016.
 */
public class Subscription {
    public static final int COLUMN_FEED_NAME_LENGTH = 300;

    private int user_id;
    private int feed_id;
    private String feed_name;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(int feed_id) {
        this.feed_id = feed_id;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }
}
