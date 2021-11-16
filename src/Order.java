import java.util.LinkedList;


public class Order {
    private static Integer orderNumber = 100000;
    private LinkedList <OrderLine> orderLines = new LinkedList<>();
    private LinkedList <String> restaurantNames = new LinkedList<>();
    private Double discount;
    private Double total;
    private Double grandTotal;
    private Double deliveryCharge;

    public Order(LinkedList <OrderLine> orderLines, LinkedList <Discount> discountsList){
        Order.orderNumber ++;
        this.orderLines = orderLines;
        this.total = 0.00;
        this.deliveryCharge = 0.00;
        this.grandTotal = 0.00;
        //Iterating over orderlines assign values to the class variables.
        for (int i = 0; i < orderLines.size(); i++) {
            this.total += orderLines.get(i).getTotal();
            
            //Adding unique restaurant names.
            if (!this.restaurantNames.contains(orderLines.get(i).getRestaurantName())){
                restaurantNames.add(orderLines.get(i).getRestaurantName());
                this.deliveryCharge += orderLines.get(i).getRestaurant().getDeliveryCost();
            }
        }
        //Iterating over the discount list to find the correct discount
        for (int i = 0; i < discountsList.size(); i++) {
            Discount discount = discountsList.get(i);
            if (restaurantNames.size() == discount.getNumberOfRestaurants()) {
                if (this.total >= discount.getMinOrder()) {
                    if (this.total < discount.getMaxOrder()) {
                        this.discount = discount.getDiscount();
                        this.deliveryCharge -= this.deliveryCharge * (discount.getDeliveryDiscount()/100);
                    }
                    if (discount.getMinOrder() == discount.getMaxOrder()){
                        this.discount = discount.getDiscount();
                        this.deliveryCharge -= this.deliveryCharge * (discount.getDeliveryDiscount()/100);
                    }
                }
            }
            //When order has more than 2 restaurants
            if (restaurantNames.size() > discount.getNumberOfRestaurants()) {
                if (this.total >= discount.getMinOrder()) {
                    if (this.total < discount.getMaxOrder()) {
                        this.discount = discount.getDiscount();
                        this.deliveryCharge -= this.deliveryCharge * (discount.getDeliveryDiscount()/100);
                    }
                    if (discount.getMinOrder() == discount.getMaxOrder()){
                        this.discount = discount.getDiscount();
                        this.deliveryCharge -= this.deliveryCharge * (discount.getDeliveryDiscount()/100);
                    }
                }
            }
        }
        this.grandTotal = this.total - (this.discount/100*this.total) + this.deliveryCharge;
    }

    //Getters
    public Integer getOrderNumber(){
        return Order.orderNumber;
    }
    public LinkedList <OrderLine> getOrderLines(){
        return this.orderLines;
    }
    public LinkedList <String> getRestaurantNames(){
        return this.restaurantNames;
    }
    public Double getTotal(){
        return this.total;
    }
    public Double getDiscount(){
        return this.discount;
    }
    public Double getGrandTotal(){
        return this.grandTotal;
    }

    public void checkout() {
        double discountAmount = this.total*this.discount/100.00;
        System.out.println(String.format(
            "**************************************************\n\nOrder Number: %d\n", Order.orderNumber));
        for (int i = 0; i < restaurantNames.size(); i++) {
            String restaurant = restaurantNames.get(i);
            System.out.println("--------------------------------------------------");
            System.out.println(restaurant);
            for (int j = 0; j < orderLines.size(); j++) {
                OrderLine orderline = orderLines.get(j);
                if(orderline.getRestaurantName() == restaurant){
                    System.out.println(String.format(
                        "%s | $%.2f | %d unit(s) | $%.2f |", orderline.getFoodName(), orderline.getPrice(), orderline.getQuantity(), orderline.getTotal()));
                }
            }
            System.out.println("--------------------------------------------------");
        }
        System.out.println(String.format("\nTotal: $%.2f\nDiscount: %.2f%%\nDiscount Amt: $%.2f\nDelivery Charge: $%.2f\n\n--------------------------------------------------\nGrand Total: $%.2f", this.total, this.discount, discountAmount,this.deliveryCharge, this.grandTotal));
        System.out.println("\n**************************************************\n");
    }
}
