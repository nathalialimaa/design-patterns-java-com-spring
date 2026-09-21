package one.digitalinnovation.gof.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal calculateDiscount(BigDecimal price);
}
