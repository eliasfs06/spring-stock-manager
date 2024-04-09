package com.eliasfs06.spring.stockmanager.model.dto;

import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;

import java.util.List;

public class ProductAcquisitionItemDTO {
    private String productId;
    private String quantity;

    public ProductAcquisitionItemDTO(String productId, String quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductAcquisitionItemDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
