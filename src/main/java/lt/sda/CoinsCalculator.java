package lt.sda;

import lt.sda.dto.CoinDTO;
import lt.sda.models.Coin;
import lt.sda.models.Product;
import lt.sda.services.DatabaseService;
import lt.sda.services.UserInterfaceService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CoinsCalculator {
    private DatabaseService dbService;
    private UserInterfaceService uiService;

    public CoinsCalculator(DatabaseService dbService, UserInterfaceService uiService){
        this.dbService = dbService;
        this.uiService = uiService;
    }

    private Map<Integer, List<Coin>> getGroupedCoins(List<Coin> wallet){
        return wallet
                .stream()
                .collect(groupingBy(Coin::getCoin));
    }

    private void printWallet(Map<Integer, List<Coin>> groupedCoins){
        for(Map.Entry<Integer, List<Coin>> entry : groupedCoins.entrySet()){
            uiService.displayMessage("%d x %d", entry.getKey(), entry.getValue().size());
        }
    }

    public List<Coin> requestPayment(int coinsToPay){
        int leftToPay = coinsToPay;
        List<Coin> wallet = dbService.getWallet();
        Map<Integer, List<Coin>> groupedCoins = getGroupedCoins(wallet);
        while(leftToPay > 0){
            uiService.displayMessage("Left to pay: %d", leftToPay);
            printWallet(groupedCoins);
            int code = uiService.enterCode();
            if(!groupedCoins.containsKey(code)){
                uiService.displayMessage("Incorrect coin.");
                continue;
            }

            Coin coin = groupedCoins.get(code).get(0);
            leftToPay -= coin.getCoin();
            wallet.remove(coin);

            groupedCoins = getGroupedCoins(wallet);
            dbService.saveWallet(
                    groupedCoins
                    .entrySet()
                    .stream()
                    .map(k -> {
                        List<Coin> coins = k.getValue();
                        return CoinDTO.transform(coins.get(0), coins.size());
                    })
                    .collect(Collectors.toList()));
        }

        uiService.displayMessage("Left to pay: %d", leftToPay);
        List<Coin> change = new ArrayList();
        int leftToRefund = leftToPay * -1;
        List<Integer> coinOptions = Arrays.asList(50, 20, 10);
        while(leftToRefund > 0){
            for(Integer opt : coinOptions){
                if(opt > leftToRefund){
                    continue;
                }

                change.add(new Coin(opt));
                leftToRefund -= opt;
            }
        }

        return change;
    }

}
