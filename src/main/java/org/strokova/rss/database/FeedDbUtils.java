package org.strokova.rss.database;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.strokova.rss.obj.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * author: Veronika, 7/28/2016.
 */

public class FeedDbUtils {
    private static final Logger logger = Logger.getLogger(FeedDbUtils.class.getName());
    private static final int NO_RESULT_ID = -1;

    // TODO: AsyncQueryRunner?

    public static List<FeedItem> getUserFeedItemsLatest(int userId) {
        String query =
                "select * from feed_item item\n" +
                        "join subscription sub\n" +
                        "on item.feed_id = sub.feed_id\n" +
                        "where sub.user_id = ?\n" +
                        "order by item.pub_date desc";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<List<FeedItem>> resultHandler = new BeanListHandler<>(FeedItem.class);
        List<FeedItem> feedItems = null;
        try {
            feedItems = run.query(query, resultHandler, userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return feedItems;
    }

    public static List<FeedItem> getFeedItemsByFeedLink(String feedLink) {
        String query =
                "select * from feed_item\n" +
                        "join feed\n" +
                        "on feed_item.feed_id = feed.id\n" +
                        "where feed.feed_link = ?\n" +
                        "order by feed_item.pub_date desc"; //TODO: order by date
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<List<FeedItem>> resultHandler = new BeanListHandler<>(FeedItem.class);
        List<FeedItem> feedItems = null;
        try {
            feedItems = run.query(query, resultHandler, feedLink);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return feedItems;
    }

    public static Feed getFeedByFeedLink(String feedLink) {
        String query = "select * from feed where feed_link = ?";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<Feed> resultHandler = new BeanHandler<>(Feed.class);
        Feed feed = null;
        try {
            feed = run.query(query, resultHandler, feedLink);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return feed;
    }

    public static Feed getFeedByFeedLink(String feedLink, Connection conn) {
        String query = "select * from feed where feed_link = ?";
        QueryRunner run = new QueryRunner();
        ResultSetHandler<Feed> resultHandler = new BeanHandler<>(Feed.class);
        Feed feed = null;
        try {
            feed = run.query(conn, query, resultHandler, feedLink);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return feed;
    }

    public static List<SubscriptionWithFeed> getUserSubscriptionsWithFeeds(int userId) {
        String query =
                "select s.user_id, s.feed_id, f.feed_link, s.feed_name\n" +
                        "from subscription s\n" +
                        "left join feed f\n" +
                        "on s.feed_id = f.id\n" +
                        "where s.user_id = ?";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<List<SubscriptionWithFeed>> resultHandler = new BeanListHandler<>(SubscriptionWithFeed.class);
        List<SubscriptionWithFeed> subscriptions = null;
        try {
            subscriptions = run.query(query, resultHandler, userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return subscriptions;
    }

    public static SubscriptionWithFeed getSubscriptionWithFeedByFeedLink(String feedLink) {
        String query =
                "select s.user_id, s.feed_id, f.feed_link, s.feed_name\n" +
                        "from subscription s\n" +
                        "left join feed f\n" +
                        "on s.feed_id = f.id\n" +
                        "where f.feed_link = ?";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<SubscriptionWithFeed> resultHandler = new BeanHandler<>(SubscriptionWithFeed.class);
        SubscriptionWithFeed subscription = null;
        try {
            subscription = run.query(query, resultHandler, feedLink);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return subscription;
    }

    public static boolean isValidUser(String userName, String userPassword) {
        String query = "select * from user where username = ? and password = ?";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<User> resultHandler = new BeanHandler<>(User.class);
        User user = null;
        try {
            user = run.query(query, resultHandler, userName, userPassword);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e); // TODO: create custom exceptions
        }
        return (user != null);
    }

    public static int getUserId(String userName) {
        String query = "select * from user where username = ?";
        QueryRunner run = new QueryRunner(FeedDbDataSource.getDataSource());
        ResultSetHandler<User> resultHandler = new BeanHandler<>(User.class);
        User user = null;
        try {
            user = run.query(query, resultHandler, userName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }

        if (user != null) {
            return user.getId();
        } else {
            return NO_RESULT_ID; // TODO: handle 'nothing found' cases
        }
    }

    // insert new RSS feed into feed table
    // @return feed.id of inserted feed
    public static int insertRssIntoFeedTable(String feedLink, Connection conn) {
        String query = "insert into feed (feed_link) values (?)\n" +
                "on duplicate key update feed_link = values(feed_link)";
        QueryRunner run = new QueryRunner();
        ResultSetHandler<Feed> resultHandler = new  BeanHandler<>(Feed.class);
        try {
            run.insert(conn, query, resultHandler, feedLink);
        } catch (SQLException e) {
            //TODO: can handle Duplicate entry 1062 error (if nor selecting in return)
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
        return getFeedByFeedLink(feedLink, conn).getId();
    }

    // insert new subscription for a user when they add new rss
    public static void insertIntoSubscriptionTable(int userId, int feedId, String feedName, Connection conn) {
        String query =
                "insert into subscription (user_id, feed_id, feed_name) values (?, ?, ?)\n" +
                "on duplicate key update\n" +
                "feed_name = values(feed_name)";
        QueryRunner run = new QueryRunner();
        ResultSetHandler<Subscription> resultHandler = new BeanHandler<>(Subscription.class);
        try {
            run.insert(conn, query, resultHandler, userId, feedId, feedName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
    }

    // insert feed items when a new feed is added
    // TODO update items each time
    public static void insertIntoFeedItemTable(Object[][] items, Connection conn) {
        String query =
                "INSERT INTO feed_item (guid, title, description, link, pub_date, feed_id) \n" +
                "VALUES (?, ?, ?, ?, ?, ?) \n" +
                "ON DUPLICATE KEY UPDATE \n" +
                "title = VALUES(title), \n" +
                "description = VALUES(description),\n" +
                "link = VALUES(link),\n" +
                "pub_date = VALUES(pub_date),\n" +
                "feed_id = VALUES(feed_id);";
        QueryRunner run = new QueryRunner();
        try {
            run.batch(conn, query, items);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SQL", e);
        }
    }
}
