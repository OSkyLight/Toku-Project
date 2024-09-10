package com.example.tokuverseproject.Model;

import java.io.Serializable;

public class Cart implements Serializable {
    Product product;
    Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
