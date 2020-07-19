package lt.sda.models;

public class Product {
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public int getCode() {
        return code;
    }

    private final String name;
    private final String size;
    private final double price;
    private final int code;

    public Product(String name, String size, double price, int code) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.code = code;
    }

    @Override
    public String toString(){
        return String.format("<Product code: %d name: %s size: %s price: %.2f>", code, name, size, price);
    }

    @Override
    public boolean equals(Object other){
        return code == ((Product)other).getCode();
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(code);
    }
}
