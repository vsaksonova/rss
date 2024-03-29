package org.strokova.rss.util;

/**
 * @author vstrokova, 18.08.2016.
 */
public final class RequestConstants {
    private RequestConstants() {}

    // request parameters
    public static final String PARAM_RSS_LINK = "rss_link";
    public static final String PARAM_RSS_NAME = "rss_name";
    public static final String PARAM_RENAME_LINK = "rename";
    public static final String PARAM_NEW_FEED_NAME = "newFeedName";
    public static final String PARAM_REMOVE_LINK = "remove";
    public static final String PARAM_RSS_ID = "id";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_ORDER = "order";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_USER_PASSWORD = "userpass";
    public static final String PARAM_MARK_READ = "markRead";
    public static final String PARAM_REDIRECT_URI = "redirectUri";


    // request attributes
    public static final String REQ_ATTR_FEED = "feed";
    public static final String REQ_ATTR_FEED_ITEMS = "feedItems";
    public static final String REQ_ATTR_PAGINATION_PAGE_COUNT = "pageCount";
    public static final String REQ_ATTR_PAGINATION_SERVLET_PATTERN = "servletPattern";
    public static final String REQ_ATTR_PAGINATION_SERVLET_PATTERN_VALUE_FEED = "feed";
    public static final String REQ_ATTR_PAGINATION_SERVLET_PATTERN_VALUE_LATEST = "latest";
    public static final String REQ_ATTR_RSSLIST_SUBSCRIPTIONS = "subscriptions";
    public static final String REQ_ATTR_ERROR = "error";
    public static final String REQ_ATTR_MAX_LENGTH_USERNAME = "usernameMaxLength";
    public static final String REQ_ATTR_MAX_LENGTH_PASSWORD = "passwordMaxLength";
    public static final String REQ_ATTR_MAX_LENGTH_FEED_NAME = "feedNameMaxLength";
    public static final String REQ_ATTR_MAX_LENGTH_FEED_LINK = "feedLinkMaxLength";
    public static final String REQ_ATTR_REDIRECT_URI = "redirectUri";


    // session attributes
    public static final String SESSION_ATTR_USER_ID = "userId";

    // encoding
    public static final String ENCODING_UTF_8 = "UTF-8";
}
