package lt.sda.dto;

import java.util.List;

public class DataDTO {
    private List<ProductDTO> products;
    private List<CoinDTO> wallet;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<CoinDTO> getWallet() {
        return wallet;
    }

    public void setWallet(List<CoinDTO> wallet) {
        this.wallet = wallet;
    }

}
