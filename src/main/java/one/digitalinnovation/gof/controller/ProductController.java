package one.digitalinnovation.gof.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import one.digitalinnovation.gof.dto.ProductRequest;
import one.digitalinnovation.gof.dto.ProductResponse;
import one.digitalinnovation.gof.facade.ProductFacade;

    @RestController
    @RequestMapping("/products")
    public class ProductController {

        private final ProductFacade productFacade;

        public ProductController(ProductFacade productFacade) {
            this.productFacade = productFacade;
        }

        @PostMapping
        public ResponseEntity<ProductResponse> create(
                @Valid @RequestBody ProductRequest request
        ) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productFacade.create(request));
        }

        @GetMapping
        public ResponseEntity<List<ProductResponse>> findAll() {
            return ResponseEntity.ok(productFacade.findAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> findById(
                @PathVariable Long id
        ) {
            return ResponseEntity.ok(
                    productFacade.findById(id)
            );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(
                @PathVariable Long id
        ) {
            productFacade.delete(id);

            return ResponseEntity.noContent().build();
        }
    }
