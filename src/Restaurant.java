
import java.util.*;

public class Restaurant {
    //Restaurant variables/attributes
    private String name;
    private String category;
    private Double deliveryCost;
    private HashMap<Integer, FoodItem> foodItems = new HashMap<Integer, FoodItem>();

    //Class initializer
    public Restaurant(String name, String category, Double deliveryCost, HashMap<Integer, FoodItem> foodItems){
        this.name = name;
        this.category = category;
        this.deliveryCost = deliveryCost;
        this.foodItems = foodItems;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCategory: %s\nDelivery Cost: $%.2f", this.name, this.category, this.deliveryCost);
    }
    //returns the name
    public String getName(){
        return this.name;
    }
    //returns the category
    public String getCategory(){
        return this.category;
    }
    //returns the delivery cost
    public Double getDeliveryCost(){
        return this.deliveryCost;
    }
    //returns specific food item
    public FoodItem getFoodItem(int key){
        FoodItem item = foodItems.get(key);
        return item;
    }
    //returns all the food items
    public String[] getFoodItems(){
        String[] items;
        items = new String[foodItems.size()];
        for (int key = 1; key <= foodItems.size(); key++) {
            items[key-1] = String.format("%d: %s", key, foodItems.get(key).toString());
        }
        return items;
    }

}