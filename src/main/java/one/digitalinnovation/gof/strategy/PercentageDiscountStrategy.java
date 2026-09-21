package one.digitalinnovation.gof.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PercentageDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal price) {
        return price.multiply(new BigDecimal("0.10"));
    }
}
