package one.digitalinnovation.gof.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import one.digitalinnovation.gof.enums.DiscountType;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private DiscountType discountType;

    public Product() {
    }

    public Product(String name, BigDecimal price, DiscountType discountType) {
        this.name = name;
        this.price = price;
        this.discountType = discountType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }
}
