package app.model.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private List<CartLine> cartLines = new ArrayList<CartLine>();

    public Cart() {
    }

    public List<CartLine> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<CartLine> cartLines) {
        this.cartLines = cartLines;
    }

    private CartLine findLineById(int id){
        for (CartLine line : this.cartLines){
            if (line.getProductDTO().getId().equals(id))
                return line;
        }
        return null;
    }

    public void addProduct(ProductDTO productDTO, int quantity){
        CartLine cartLine = this.findLineById(productDTO.getId());
        if (cartLine == null){
            cartLine = new CartLine();
            cartLine.setQuantity(0);
            cartLine.setProductDTO(productDTO);
            this.cartLines.add(cartLine);
        }
        int newQuantity = cartLine.getQuantity() + quantity;
        if (newQuantity <= 0)
            this.cartLines.remove(cartLine);
        else
            cartLine.setQuantity(newQuantity);
    }

    public void updateProduct(int id, int quantity){
        CartLine cartLine = this.findLineById(id);
        if (cartLine != null){
            if (quantity <= 0)
                this.cartLines.remove(cartLine);
            else
                cartLine.setQuantity(quantity);
        }
    }

    public void removeProduct(Integer productId){
        CartLine cartLine = this.findLineById(productId);
        if (cartLine != null)
            this.cartLines.remove(cartLine);
    }

    public boolean isEmpty(){
        return this.cartLines.isEmpty();
    }

    public int getQuantityTotal(){
        int quantity = 0;
        for (CartLine cartLine : this.cartLines)
            quantity += cartLine.getQuantity();
        return quantity;
    }

    public double getAmountTotal(){
        double total = 0;
        for (CartLine cartLine : this.cartLines)
            total += cartLine.getAmount();
        return total;
    }
}

