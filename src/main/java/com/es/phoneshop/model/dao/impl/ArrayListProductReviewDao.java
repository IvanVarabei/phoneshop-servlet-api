package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductReviewDao;
import com.es.phoneshop.model.entity.ProductReview;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao extends ArrayListGenericDao<ProductReview> implements ProductReviewDao {
    private ArrayListProductReviewDao(){
    }

    private static class ArrayListProductReviewDaoHolder{
        private static final ArrayListProductReviewDao ARRAY_LIST_PRODUCT_REVIEW_DAO_INSTANCE
                = new ArrayListProductReviewDao();
    }

    public static ArrayListProductReviewDao getInstance(){
        return ArrayListProductReviewDaoHolder.ARRAY_LIST_PRODUCT_REVIEW_DAO_INSTANCE;
    }

    @Override
    public List<ProductReview> findApprovedProductReviews(long productId) {
        return items.stream()
                .filter(productReview -> productReview.getProductId() == productId)
                .filter(ProductReview::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> findNotApprovedProductReviews() {
        return items.stream()
                .filter(productReview -> !productReview.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public void approve(long productReviewId) {
        items.stream()
                .filter(productReview -> productReview.getId().equals(productReviewId))
                .forEach(productReview -> productReview.setApproved(true));
    }

    @Override
    public void delete(long productReviewId) {
        items.removeIf(productReview -> productReview.getId().equals(productReviewId));
    }
}
