package org.strokova.rss.obj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author vstrokova, 10.08.2016.
 */
public class FeedItemWithReadStatus {
    private static final String STATUS_READ = "read";
    private static final String STATUS_UNREAD = "unread";

    private String guid;
    private String title;
    private String description;
    private String link;
    private Date pub_date;
    private String feed_id;
    private boolean is_read;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    public boolean is_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getFormattedDate() {
        String datePattern = "EEE, MMM d, h:mm a";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern, Locale.getDefault());
        return formatter.format(pub_date);
    }

    public String getReadStatusAsString() {
        return is_read ? STATUS_READ : STATUS_UNREAD;
    }
}
