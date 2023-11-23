package com.leontev.graviton.feed;

import com.leontev.graviton.feed.Feed;

public interface FeedService {
    Feed getFeed(String telegramId);
}
