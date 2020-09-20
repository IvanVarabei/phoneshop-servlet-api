package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.ProductReview;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.ProductReviewService;
import com.es.phoneshop.model.service.ProductService;
import com.es.phoneshop.model.service.impl.DefaultProductReviewService;
import com.es.phoneshop.model.service.impl.DefaultProductService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductReviewServlet extends HttpServlet {
    private static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";
    private static final String REDIRECT_AFTER_PLACING_REVIEW = "%s/products/%s?message=Review sent to moderation";
    private ProductReviewService productReviewService = DefaultProductReviewService.getInstance();
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long productId = Long.parseLong(req.getPathInfo().substring(1));
        int rating = Integer.parseInt(req.getParameter(Const.RequestParam.RATING));
        String comment = req.getParameter(Const.RequestParam.COMMENT);
        String author = req.getSession().getAttribute(Const.AttributeKey.LOGIN).toString();
        if (comment == null || comment.isEmpty()) {
            req.setAttribute(Const.ErrorKey.COMMENT, Const.ErrorInfo.VALUE_IS_REQUIRED);
            try {
                req.setAttribute(Const.AttributeKey.PRODUCT, productService.find(productId));
                List<ProductReview> productReviews = productReviewService.findApprovedProductReviews(productId);
                req.setAttribute(Const.AttributeKey.PRODUCT_REVIEWS, productReviews);
            } catch (ItemNotFoundException ignored) {
            }
            req.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(req, resp);
        } else {
            productReviewService.save(new ProductReview(author, rating, comment, productId));
            resp.sendRedirect(String.format(REDIRECT_AFTER_PLACING_REVIEW, req.getContextPath(), productId));
        }
    }
}
