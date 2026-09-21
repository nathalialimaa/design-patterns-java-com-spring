package one.digitalinnovation.gof.dto;

import one.digitalinnovation.gof.entity.Product;
import one.digitalinnovation.gof.enums.DiscountType;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private DiscountType discountType;
    private BigDecimal discount;
    private BigDecimal finalPrice;

    public ProductResponse(
            Long id,
            String name,
            BigDecimal price,
            DiscountType discountType,
            BigDecimal discount,
            BigDecimal finalPrice
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountType = discountType;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public static ProductResponse from(
            Product product,
            BigDecimal discount,
            BigDecimal finalPrice
    ) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscountType(),
                discount,
                finalPrice
        );
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
}
