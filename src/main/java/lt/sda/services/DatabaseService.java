package lt.sda.services;

import lt.sda.dto.CoinDTO;
import lt.sda.dto.ProductDTO;
import lt.sda.models.Coin;
import lt.sda.models.Product;

import java.util.List;

public interface DatabaseService {
    List<Product> getProductList();
    void saveProductList(List<ProductDTO> products);
    List<Coin> getWallet();
    void saveWallet(List<CoinDTO> coins);
}
