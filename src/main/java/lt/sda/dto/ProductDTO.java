package lt.sda.dto;

import lt.sda.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO implements Serializable {
    private String name;
    private String size;
    private double price;
    private int code;
    private int inStock;

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Product> transform(){
        List<Product> products = new ArrayList<>();
        for(int i = 0; i<inStock; i++){
            products.add(new Product(name, size, price, code));
        }

        return products;
    }

    public static ProductDTO transform(Product product, int count){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSize(product.getSize());
        productDTO.setInStock(count);

        return productDTO;
    }
}
