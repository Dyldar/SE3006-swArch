package tr.edu.mu.se3006.business;
import tr.edu.mu.se3006.domain.Product;
import tr.edu.mu.se3006.persistence.ProductRepository;

public class OrderService {
    // TODO: Define ProductRepository dependency
    public final ProductRepository productRepository;
    // TODO: Implement Constructor Injection
    public OrderService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    public void placeOrder(Long productId, int quantity) {
        // TODO 1: Find product via repository
        // TODO 2: Check stock (throw IllegalArgumentException if insufficient)
        // TODO 3: Reduce stock
        // TODO 4: Save updated product

        ///Product product = productRepository.findById(productId);
        //if( int quantity < product.sto)

        Product product = productRepository.findById(productId);
        int stock = product.getStock();
        if(quantity < stock){
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        stock -= quantity;
        product.setStock(stock);
        productRepository.save(product);
        
    }
}
