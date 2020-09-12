package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.ProductReview;

import java.util.List;

public interface ProductReviewDao extends GenericDao<ProductReview>{
    List<ProductReview> findApprovedProductReviews(long productId);

    List<ProductReview> findNotApprovedProductReviews();

    void approve(long id);

    void delete(long productReviewId);
}
