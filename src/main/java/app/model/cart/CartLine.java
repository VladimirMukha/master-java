package app.model.cart;

import java.io.Serializable;

public class CartLine implements Serializable {
    private ProductDTO productDTO;
    private int quantity;

    public CartLine(ProductDTO productDTO, int quantity) {
        this.productDTO = productDTO;
        this.quantity = quantity;
    }

    public CartLine() {
        this.quantity = 1;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount(){
        return this.productDTO.getPrice() * this.quantity;
    }
}
