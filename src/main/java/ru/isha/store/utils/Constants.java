package ru.isha.store.utils;

public final class Constants {
    public static final String CURRENT_SHOPPING_CART = "CURRENT_SHOPPING_CART";

    public static final int MAX_PRODUCT_COUNT_PER_SHOPPING_CART = 10;

    public static final int MAX_PRODUCTS_PER_SHOPPING_CART = 20;

    public static final int ORDERS_PER_PAGE = 5;

    public static final int MAX_PRODUCTS_PER_HTML_PAGE = 12;

    public static final String CATEGORY_LIST = "CATEGORY_LIST";

    public static final String SUBCATEGORY_LIST = "SUBCATEGORY_LIST";

    public static final String PRODUCER_LIST = "PRODUCER_LIST";

    public static final String CURRENT_ACCOUNT = "CURRENT_ACCOUNT";

    public static final String SPECCATEGORY_LIST = "SPECCATEGORY_LIST";

    public static final long FREE_DELIVERY_COST = 50L;
    public static final long DELIVERY_COST = 1L;


    public static final long MAX_FILE_SIZE = 1024 * 1024 * 25;//25MB

    public static final long MAX_REQUEST_SIZE = 1024 * 1024 * 30;//30MB

    public static final int FILE_SIZE_THRESHOLD = 0;

    public enum Cookie {
        //   1 year ttl
        //   pSCC -pharmacy shopping cart cookie
        SHOPPING_CART("pSCC", 60 * 60 * 24 * 365);

        private final String name;
        private final int ttl;

        Cookie(String name, int ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }
    }


}
