package lt.sda;

import lt.sda.dto.ProductDTO;
import lt.sda.models.Product;
import lt.sda.services.ConsoleUserInterfaceService;
import lt.sda.services.DatabaseService;
import lt.sda.services.JsonDatabaseService;
import lt.sda.services.UserInterfaceService;

import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String resourcesName = System.getenv("JSON_DATABASE_PATH");
        if(resourcesName == null){
            resourcesName = "data.json";
        }

        DatabaseService dbService = new JsonDatabaseService(resourcesName);
        UserInterfaceService uiService = new ConsoleUserInterfaceService();

        VendingMachine vendingMachine = new VendingMachine(dbService, uiService);
        vendingMachine.run();
    }
}
