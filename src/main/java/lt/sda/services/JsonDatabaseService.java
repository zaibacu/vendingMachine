package lt.sda.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lt.sda.dto.CoinDTO;
import lt.sda.dto.DataDTO;
import lt.sda.dto.ProductDTO;
import lt.sda.models.Coin;
import lt.sda.models.Product;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JsonDatabaseService implements DatabaseService {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String databaseFilePath;

    public JsonDatabaseService(String resourceName){
         databaseFilePath = JsonDatabaseService
                .class
                .getClassLoader()
                .getResource(resourceName)
                .getFile();
    }
    private DataDTO getData(){
        try(FileReader reader = new FileReader(databaseFilePath)){
            return gson.fromJson(reader, DataDTO.class);
        }
        catch(FileNotFoundException ex){
            System.err.println(ex);
            return new DataDTO();
        }
        catch(IOException ioe){
            System.err.println(ioe);
            return new DataDTO();
        }
    }

    @Override
    public List<Product> getProductList() {
        DataDTO data = getData();
        List<ProductDTO> productDTOs = data.getProducts();

        return productDTOs
                .stream()
                .map(p -> p.transform())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProductList(List<ProductDTO> products) {
        DataDTO data = getData();
        data.setProducts(products);
        store(data);
    }

    @Override
    public List<Coin> getWallet() {
        DataDTO data = getData();
        List<CoinDTO> coinDTOs = data.getWallet();
        return coinDTOs
                .stream()
                .map(c -> c.transform())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void saveWallet(List<CoinDTO> coins) {

    }

    private void store(DataDTO dataDTO) {
        try(FileWriter writer = new FileWriter(databaseFilePath)){
            gson.toJson(dataDTO, writer);
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}
