package com.wenkai.my.gift.bean;

import java.util.List;

/**
 * Created by my on 2016/3/18.
 */
public class HotContextInfo {



    private int code;

    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {

        private PagingEntity paging;


        private List<ItemsEntity> items;

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class PagingEntity {
            private String next_url;

            public void setNext_url(String next_url) {
                this.next_url = next_url;
            }

            public String getNext_url() {
                return next_url;
            }
        }

        public static class ItemsEntity {

            private DataEntity1 data;
            private String type;

            public void setData(DataEntity1 data) {
                this.data = data;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataEntity1 getData() {
                return data;
            }

            public String getType() {
                return type;
            }

            public static class DataEntity1 {
                private Object brand_id;
                private Object brand_order;
                private int category_id;
                private String cover_image_url;
                private String cover_webp_url;
                private int created_at;
                private String description;
                private int editor_id;
                private int favorites_count;
                private int id;
                private boolean is_favorite;
                private String name;
                private String price;
                private String purchase_id;
                private Object purchase_shop_id;
                private int purchase_status;
                private int purchase_type;
                private String purchase_url;
                private int subcategory_id;
                private int updated_at;
                private String url;
                private List<?> ad_monitors;
                private List<String> image_urls;
                private List<?> post_ids;
                private List<String> webp_urls;

                public void setBrand_id(Object brand_id) {
                    this.brand_id = brand_id;
                }

                public void setBrand_order(Object brand_order) {
                    this.brand_order = brand_order;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public void setCover_image_url(String cover_image_url) {
                    this.cover_image_url = cover_image_url;
                }

                public void setCover_webp_url(String cover_webp_url) {
                    this.cover_webp_url = cover_webp_url;
                }

                public void setCreated_at(int created_at) {
                    this.created_at = created_at;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setEditor_id(int editor_id) {
                    this.editor_id = editor_id;
                }

                public void setFavorites_count(int favorites_count) {
                    this.favorites_count = favorites_count;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setIs_favorite(boolean is_favorite) {
                    this.is_favorite = is_favorite;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public void setPurchase_id(String purchase_id) {
                    this.purchase_id = purchase_id;
                }

                public void setPurchase_shop_id(Object purchase_shop_id) {
                    this.purchase_shop_id = purchase_shop_id;
                }

                public void setPurchase_status(int purchase_status) {
                    this.purchase_status = purchase_status;
                }

                public void setPurchase_type(int purchase_type) {
                    this.purchase_type = purchase_type;
                }

                public void setPurchase_url(String purchase_url) {
                    this.purchase_url = purchase_url;
                }

                public void setSubcategory_id(int subcategory_id) {
                    this.subcategory_id = subcategory_id;
                }

                public void setUpdated_at(int updated_at) {
                    this.updated_at = updated_at;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setAd_monitors(List<?> ad_monitors) {
                    this.ad_monitors = ad_monitors;
                }

                public void setImage_urls(List<String> image_urls) {
                    this.image_urls = image_urls;
                }

                public void setPost_ids(List<?> post_ids) {
                    this.post_ids = post_ids;
                }

                public void setWebp_urls(List<String> webp_urls) {
                    this.webp_urls = webp_urls;
                }

                public Object getBrand_id() {
                    return brand_id;
                }

                public Object getBrand_order() {
                    return brand_order;
                }

                public int getCategory_id() {
                    return category_id;
                }

                public String getCover_image_url() {
                    return cover_image_url;
                }

                public String getCover_webp_url() {
                    return cover_webp_url;
                }

                public int getCreated_at() {
                    return created_at;
                }

                public String getDescription() {
                    return description;
                }

                public int getEditor_id() {
                    return editor_id;
                }

                public int getFavorites_count() {
                    return favorites_count;
                }

                public int getId() {
                    return id;
                }

                public boolean isIs_favorite() {
                    return is_favorite;
                }

                public String getName() {
                    return name;
                }

                public String getPrice() {
                    return price;
                }

                public String getPurchase_id() {
                    return purchase_id;
                }

                public Object getPurchase_shop_id() {
                    return purchase_shop_id;
                }

                public int getPurchase_status() {
                    return purchase_status;
                }

                public int getPurchase_type() {
                    return purchase_type;
                }

                public String getPurchase_url() {
                    return purchase_url;
                }

                public int getSubcategory_id() {
                    return subcategory_id;
                }

                public int getUpdated_at() {
                    return updated_at;
                }

                public String getUrl() {
                    return url;
                }

                public List<?> getAd_monitors() {
                    return ad_monitors;
                }

                public List<String> getImage_urls() {
                    return image_urls;
                }

                public List<?> getPost_ids() {
                    return post_ids;
                }

                public List<String> getWebp_urls() {
                    return webp_urls;
                }
            }
        }
    }
}
