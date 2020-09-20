package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.ProductReview;

import java.util.List;

public interface ProductReviewService{
    List<ProductReview> findApprovedProductReviews(long productId);

    List<ProductReview> findNotApprovedProductReviews();

    void approve(long id);

    void save(ProductReview productReview);

    void delete(long id);
}
