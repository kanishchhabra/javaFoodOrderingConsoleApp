
public class FoodItem {
    //Food items' attributes/variables
    private String name;
    private Double price;

    //Class initializer
    public FoodItem(String name, Double price){
        this.name = name;
        this.price = price;
    }
    //returns the price of the food item
    public Double getPrice(){
        return this.price;
    }

    //returns the price of the food item
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f", this.name, this.price);
    }
}
