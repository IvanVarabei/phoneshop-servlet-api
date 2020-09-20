package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.ProductReviewDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductReviewDao;
import com.es.phoneshop.model.entity.ProductReview;
import com.es.phoneshop.model.service.ProductReviewService;

import java.util.List;

public class DefaultProductReviewService implements ProductReviewService {
    private ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    private DefaultProductReviewService() {
    }

    private static class ProductReviewServiceHolder {
        private static final DefaultProductReviewService DEFAULT_PRODUCT_REVIEW_SERVICE_INSTANCE =
                new DefaultProductReviewService();
    }

    public static DefaultProductReviewService getInstance() {
        return DefaultProductReviewService.ProductReviewServiceHolder.DEFAULT_PRODUCT_REVIEW_SERVICE_INSTANCE;
    }

    @Override
    public List<ProductReview> findApprovedProductReviews(long productId) {
        return productReviewDao.findApprovedProductReviews(productId);
    }

    @Override
    public List<ProductReview> findNotApprovedProductReviews() {
        return productReviewDao.findNotApprovedProductReviews();
    }

    @Override
    public void approve(long id) {
        productReviewDao.approve(id);
    }

    @Override
    public void save(ProductReview productReview) { // todo generalize save and delete
        productReviewDao.save(productReview);
    }

    @Override
    public void delete(long id) {
        productReviewDao.delete(id);
    }
}
