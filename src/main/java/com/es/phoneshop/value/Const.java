package com.es.phoneshop.value;

public class Const {
    private Const() {
    }

    public static class ErrorKey {
        public static final String IMAGE_URL = "imageUrlError";
        public static final String TAG = "tagError";
        public static final String DESCRIPTION = "descriptionError";
        public static final String MIN_PRICE = "minPriceError";
        public static final String MAX_PRICE = "maxPriceError";
        public static final String PRICE = "priceError";
        public static final String STOCK = "stockError";
        public static final String COMMENT = "commentError";

        private ErrorKey() {
        }
    }

    public static class ErrorInfo {
        public static final String PRODUCT_NOT_FOUND = "Product with code '%s' not found";
        public static final String ORDER_NOT_FOUND = "Order with code '%s' not found";
        public static final String NOT_ENOUGH_STOCK = "Not enough stock. Available:%s";
        public static final String NOT_NUMBER = "Not a number";
        public static final String VALUE_IS_REQUIRED = "Value is required";
        public static final String NON_NEGATIVE_NUMBER = "Must be non negative";
        public static final String NON_NEGATIVE_INT = "Must be non negative int";
        public static final String INVALID_VALUE = "Invalid value";
        public static final String DIFFERENT_PASSWORDS = "You entered different passwords.";
        public static final String USER_ALREADY_EXIST = "Such a user already exists. Try another login.";
        public static final String WRONG_LOGIN_PASSWORD = "There is no user having such login and password.";
        public static final int PAGE_NOT_FOUND_CODE = 404;
        public static final int TOO_MANY_REQUESTS_CODE = 429;

        private ErrorInfo() {
        }
    }

    public static class AttributeKey {
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
        public static final String TAGS = "tags";
        public static final String PRODUCT_REVIEWS = "productReviews";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String REPEAT_PASSWORD = "repeatPassword";
        public static final String DESIRABLE_SECURED_PAGE = "destination";

        private AttributeKey() {
        }
    }

    public static class RequestParam {
        public static final String QUANTITY = "quantity";
        public static final String SEARCH_QUERY = "query";
        public static final String SORT_FIELD = "sortField";
        public static final String SORT_ORDER = "sortOrder";
        public static final String PRODUCT_ID = "productId";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PHONE = "phone";
        public static final String DELIVERY_DATE = "deliveryDate";
        public static final String DELIVERY_ADDRESS = "deliveryAddress";
        public static final String PAY_METHOD = "paymentMethod";
        public static final String IMAGE_URL = "imageUrl";
        public static final String TAG = "tag";
        public static final String SEARCH_TAG = "searchTag";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String STOCK = "stock";
        public static final String SEARCH_STOCK = "searchStock";
        public static final String MIN_PRICE = "minPrice";
        public static final String MAX_PRICE = "maxPrice";
        public static final String ACTION = "action";
        public static final String RATING = "rating";
        public static final String COMMENT = "comment";

        private RequestParam() {
        }
    }
}
