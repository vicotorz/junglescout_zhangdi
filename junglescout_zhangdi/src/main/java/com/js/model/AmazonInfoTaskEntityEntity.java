package com.js.model;

public class AmazonInfoTaskEntityEntity extends FetchInfoTaskEntity {

    private String ASIN;
    private String productStar;
    private String productRank;
    

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getProductStar() {
        return productStar;
    }

    public void setProductStar(String productStar) {
        this.productStar = productStar;
    }

    public String getProductRank() {
        return productRank;
    }

    public void setProductRank(String productRank) {
        this.productRank = productRank;
    }
}
