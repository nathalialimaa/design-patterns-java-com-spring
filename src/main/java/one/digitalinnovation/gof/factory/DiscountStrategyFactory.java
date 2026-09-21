package one.digitalinnovation.gof.factory;

import one.digitalinnovation.gof.enums.DiscountType;
import one.digitalinnovation.gof.strategy.DiscountStrategy;
import one.digitalinnovation.gof.strategy.FixedDiscountStrategy;
import one.digitalinnovation.gof.strategy.NoDiscountStrategy;
import one.digitalinnovation.gof.strategy.PercentageDiscountStrategy;
import org.springframework.stereotype.Component;

@Component
public class DiscountStrategyFactory {

    private final PercentageDiscountStrategy percentageStrategy;
    private final FixedDiscountStrategy fixedStrategy;
    private final NoDiscountStrategy noDiscountStrategy;

    public DiscountStrategyFactory(
            PercentageDiscountStrategy percentageStrategy,
            FixedDiscountStrategy fixedStrategy,
            NoDiscountStrategy noDiscountStrategy
    ) {
        this.percentageStrategy = percentageStrategy;
        this.fixedStrategy = fixedStrategy;
        this.noDiscountStrategy = noDiscountStrategy;
    }

    public DiscountStrategy getStrategy(DiscountType type) {

        return switch (type) {

            case PERCENTAGE -> percentageStrategy;

            case FIXED -> fixedStrategy;

            case NONE -> noDiscountStrategy;
        };
    }
}