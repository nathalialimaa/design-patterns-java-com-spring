package one.digitalinnovation.gof.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        return new BigDecimal("100.00");
    }
}
