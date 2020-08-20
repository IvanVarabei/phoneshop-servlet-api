package com.es.phoneshop.value;

public class Const {
    public static class ErrorInfo {
        public static final String NOT_FOUND = "Product with code '%s' not found.";
        public static final String NOT_ENOUGH_STOCK = "Not enough stock. Available:%s";
        public static final String NOT_NUMBER = "Not a number";
        public static final int PAGE_NOT_FOUND_CODE = 404;

        private ErrorInfo() {
        }
    }

    public static class RequestAttribute {
        public static final String MESSAGE = "message";
        public static final String ERROR = "error";
        public static final String ERRORS = "errors";
        public static final String PRODUCT = "product";
        public static final String PRODUCTS = "products";
        public static final String CART = "cart";
        public static final String RECENT = "recent";
        public static final String ORDER = "recent";
        public static final String PAY_METHODS = "paymentMethods";

        private RequestAttribute() {
        }
    }

    public static class RequestParam {
        public static final String QUANTITY = "quantity";
        public static final String SEARCH_QUERY = "query";
        public static final String SORT_FIELD = "sortField";
        public static final String SORT_ORDER = "sortOrder";
        public static final String PRODUCT_ID = "productId";
        public static final String PAY_METHOD = "paymentMethod";

        private RequestParam() {
        }
    }
}
