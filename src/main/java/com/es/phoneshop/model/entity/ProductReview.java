package com.es.phoneshop.model.entity;

public class ProductReview implements StorageItem{
    private Long id;
    private Long productId;
    private String author;
    private int rating;
    private String comment;
    private boolean isApproved;

    public ProductReview(String author, int rating, String comment, long productId) {
        this.author = author;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductReview that = (ProductReview) o;

        if (rating != that.rating) return false;
        if (isApproved != that.isApproved) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (isApproved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductReview{");
        sb.append("id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", author='").append(author).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", isApproved=").append(isApproved);
        sb.append('}');
        return sb.toString();
    }
}
