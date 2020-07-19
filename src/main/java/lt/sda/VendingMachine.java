package lt.sda;

import lt.sda.dto.ProductDTO;
import lt.sda.models.Coin;
import lt.sda.models.Product;
import lt.sda.services.DatabaseService;
import lt.sda.services.UserInterfaceService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class VendingMachine {
    private DatabaseService dbService;
    private UserInterfaceService uiService;
    private CoinsCalculator coinsCalculator;
    private boolean isRunning = true;
    private List<Product> productsStock = new ArrayList<>();
    private Map<Integer, List<Product>> productsByCode = new HashMap();

    public VendingMachine(DatabaseService dbService, UserInterfaceService uiService){
        this.dbService = dbService;
        this.uiService = uiService;
        coinsCalculator = new CoinsCalculator(dbService, uiService);
        loadProducts();
    }

    private void loadProducts(){
        productsStock = dbService.getProductList();
        transformToMaps();
    }

    private void transformToMaps(){
        productsByCode = productsStock
                .stream()
                .collect(groupingBy(Product::getCode));
    }

    private void saveProducts(){
        List<ProductDTO> productsToStore = productsByCode
                .entrySet()
                .stream()
                .map(k -> {
                    List<Product> products = k.getValue();
                    int count = products.size();
                    Product product = products.get(0);
                    return ProductDTO.transform(product, count);
                })
                .collect(Collectors.toList());

        dbService.saveProductList(productsToStore);
    }

    private void showMenu(){
        productsStock
            .stream()
            .distinct()
            .forEach(p -> {
                uiService.displayMessage("%d - %s (%s) - %.2f",
                        p.getCode(),
                        p.getName(),
                        p.getSize(),
                        p.getPrice()
                );
            });
    }

    public void run(){
        while(isRunning){
            showMenu();
            int code = uiService.enterCode();
            if(!productsByCode.containsKey(code)){
                uiService.displayMessage("Sorry, but product with code: %d doesn't exist", code);
                continue;
            }

            Product p = productsByCode.get(code).get(0);
            int priceInCents = (int)(p.getPrice() * 100);
            List<Coin> change = coinsCalculator.requestPayment(priceInCents);
            uiService.displayMessage("Change:");
            for(Coin c : change){
                uiService.displayMessage("Coin: %d" , c.getCoin());
            }

            Command buyProduct = new Command() {
                @Override
                public void execute() {
                    Product p = productsByCode.get(code).get(0);
                    productsStock.remove(p);
                    transformToMaps();
                    saveProducts();
                }

                @Override
                public void rollback() {

                }
            };

            if(uiService.question("Are you sure?")){
                buyProduct.execute();
            }
            else {
                buyProduct.rollback();
            }

        }
    }
}
