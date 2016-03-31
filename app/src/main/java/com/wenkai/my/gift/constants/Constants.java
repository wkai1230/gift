package com.wenkai.my.gift.constants;

/**
 * Created by my on 2016/3/14.
 */
public class Constants {
    public static final String HOME_TABLAYOUT_URL = "http://api.liwushuo.com/v2/channels/preset?gender=1&generation=2";
    public static final String HOME_HEART_EXPAND_URL = "http://api.liwushuo.com/v2/channels/101/items?ad=2&gender=1&generation=2&limit=20&offset=0";
    public static final String HOME_HEART_VIEW_PAGER_URL = "http://api.liwushuo.com/v2/banners";
    public static final String HOME_HEART_RECYCLER_URL = "http://api.liwushuo.com/v2/secondary_banners?gender=1&generation=1";
    public static final String HOME_HEART_PAGE_URL_HEADER = "http://api.liwushuo.com/v2/channels/";
    public static final String HOME_HEART_PAGE_URL_END = "/items?gender=1&limit=20&offset=0&generation=2";
    public static final String HOT_CONTENT_URL = "http://api.liwushuo.com/v2/items?gender=1&limit=20&offset=0&generation=1";
    public static final String CATEGORY_STRATEGY_URL = "http://api.liwushuo.com/v2/channel_groups/all";
    public static final String CATEGORY_STRATEGY_RECYCLER_URL = "http://api.liwushuo.com/v2/collections?limit=10&offset=0";
    public static final String CATEGORY_Gift_URL = "http://api.liwushuo.com/v2/item_categories/tree";
    public static final String HOME_HEART_ACTIVITY_LIKE_HEADER = "http://api.liwushuo.com/v2%2Fposts%2F";
    public static final String HOME_HEART_ACTIVITY_LIKE_END = "%2Frecommend";
    public static final String HOME_LIST_SHOW_HEADER = "http://api.liwushuo.com/v2/collections/";
    public static final String HOME_LIST_SHOW_END = "/posts?limit=20&offset=0";
    public static final String CATEGORY_GIFT_CONTENT_HEART = "http://api.liwushuo.com/v2/item_subcategories/";
    public static final String CATEGORY_GIFT_CONTENT_END = "/items?limit=20&offset=0";
    public static final String HOT_ACTIVITY = "http://api.liwushuo.com/v2/items/";
    public static final String HOT_LIKE_HEADER = "http://api.liwushuo.com/v2%2Fitems%2F";
    public static final String HOT_LIKE_END = "%2Frecommend";
    public static final String HOT_COMMENTS_HEARD = "http://api.liwushuo.com/v2/items/";
    public static final String HOT_COMMENTS_END = "/comments?limit=20&offset=0";
    public static final String SEARCH_HOT_WORD = "http://api.liwushuo.com/v2/search/hot_words";
    public static final String SEARCH_GIFT = "http://api.liwushuo.com/v2/search/item?limit=20&offset=0&sort=&keyword=";
    public static final String SEARCH_STRATEGY = "http://api.liwushuo.com/v2/search/post?limit=20&offset=0&sort=&keyword=";
    public static final String SELECT_GIFT = "http://api.liwushuo.com/v2/search/item_by_type?limit=20&offset=0";
}