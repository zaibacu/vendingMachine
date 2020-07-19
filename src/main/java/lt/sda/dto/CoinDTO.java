package lt.sda.dto;

import lt.sda.models.Coin;
import lt.sda.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoinDTO implements Serializable {
    private int coin;
    private int count;

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Coin> transform(){
        List<Coin> coins = new ArrayList<>();
        for(int i = 0; i<count; i++){
            coins.add(new Coin(coin));
        }
        return coins;
    }

    public static CoinDTO transform(Coin coin, int count){
        CoinDTO coinDTO = new CoinDTO();
        coinDTO.setCoin(coin.getCoin());
        coinDTO.setCount(count);
        return coinDTO;
    }
}
