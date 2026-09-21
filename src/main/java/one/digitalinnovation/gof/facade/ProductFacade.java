package one.digitalinnovation.gof.facade;

import one.digitalinnovation.gof.dto.ProductRequest;
import one.digitalinnovation.gof.dto.ProductResponse;
import one.digitalinnovation.gof.entity.Product;
import one.digitalinnovation.gof.factory.DiscountStrategyFactory;
import one.digitalinnovation.gof.service.ProductService;
import one.digitalinnovation.gof.strategy.DiscountStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductFacade {

    private final ProductService productService;
    private final DiscountStrategyFactory discountStrategyFactory;

    public ProductFacade(
            ProductService productService,
            DiscountStrategyFactory discountStrategyFactory
    ) {
        this.productService = productService;
        this.discountStrategyFactory = discountStrategyFactory;
    }

    public ProductResponse create(ProductRequest request) {
        //transformando productRequest em Product
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getDiscountType()
        );

        Product savedProduct = productService.create(product);

        return toResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {

        return productService.findAll()
                .stream()
                //para cada Product, execute o to response
                //.map(product -> this.toResponse(product) )
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {

        Product product = productService.findById(id);

        return toResponse(product);
    }

    private ProductResponse toResponse(Product product) {

        DiscountStrategy strategy =
                discountStrategyFactory.getStrategy(
                        product.getDiscountType()
                );

        BigDecimal discount =
                strategy.calculateDiscount(product.getPrice());

        BigDecimal finalPrice =
                product.getPrice().subtract(discount);

        return ProductResponse.from(
                product,
                discount,
                finalPrice
        );
    }

    public void delete(Long id) {
        productService.delete(id);
    }
}
