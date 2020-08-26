package com.es.phoneshop.value;

public class Const {
    private Const() {
    }

    public static class ErrorInfo {
        public static final String PRODUCT_NOT_FOUND = "Product with code '%s' not found.";
        public static final String ORDER_NOT_FOUND = "Order with code '%s' not found.";
        public static final String NOT_ENOUGH_STOCK = "Not enough stock. Available:%s.";
        public static final String NOT_NUMBER = "Not a number.";
        public static final String VALUE_IS_REQUIRED = "Value is required.";
        public static final String INVALID_VALUE = "Invalid value.";
        public static final int PAGE_NOT_FOUND_CODE = 404;
        public static final int TOO_MANY_REQUESTS_CODE = 429;

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
        public static final String ORDER = "order";
        public static final String PAY_METHODS = "paymentMethods";
        public static final String SEARCH_ERRORS = "searchErrors";

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
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PHONE = "phone";
        public static final String DELIVERY_ADDRESS = "deliveryAddress";
        public static final String DELIVERY_DATE = "deliveryDate";
        public static final String PRODUCT_CODE = "productCode";
        public static final String MIN_PRICE = "minPrice";
        public static final String MAX_PRICE = "maxPrice";
        public static final String MIN_STOCK = "minStock";



        private RequestParam() {
        }
    }
}
