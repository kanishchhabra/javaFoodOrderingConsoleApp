public class OrderLine {
    private Restaurant restaurant;
    private String restaurantName;
    private String foodName;
    private Double price;
    private Integer quantity;
    private Double total;

    //Initializer
    public OrderLine(Restaurant restaurant, Integer key, Integer quantity){
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
        this.foodName = restaurant.getFoodItem(key).getName();
        this.price = restaurant.getFoodItem(key).getPrice();
        this.quantity = quantity;
        this.total = quantity * restaurant.getFoodItem(key).getPrice();
    }

    //Getters
    public Restaurant getRestaurant(){
        return this.restaurant;
    }
    public String getRestaurantName(){
        return this.restaurantName;
    }
    public String getFoodName(){
        return this.foodName;
    }
    public Double getPrice(){
        return this.price;
    }
    public Double getTotal(){
        return this.total;
    }
    public Integer getQuantity(){
        return this.quantity;
    }

    //Set Quantity
    public void incrementQuantity(int qty){
        this.quantity += qty;
        this.total = this.quantity*price;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%.2f\n%d\n%.2f\n", this.restaurantName, this.foodName, this.price, this.quantity, this.total);
    }

}
