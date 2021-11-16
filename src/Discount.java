
public class Discount {
    //Attributes of the discount
   private int numberOfRestaurants; 
   private Double minOrder;
   private Double maxOrder;
   private Double discount;
   private Double deliveryDiscount;

   //Initializer of the class
   public Discount(int numberOfRestaurants, Double minOrder, Double maxOrder){
       
        this.numberOfRestaurants = numberOfRestaurants;
        this.maxOrder = maxOrder; //max order will be minOrder for the hughest discount slab i.e. for >= $30
        this.minOrder = minOrder;

        //Following code calculates the dicount based on max, min order amount and number of restaurants
        if (numberOfRestaurants > 1) {
            this.deliveryDiscount = 50.00;
            if (minOrder == 10 && maxOrder == 20) {
            this.discount = 10.00;
            }
            else if (minOrder == 20 && maxOrder == 30) {
            this.discount = 15.00;
            }
            else if (minOrder == 30 && maxOrder == 30) {
            this.discount = 20.00;
            }
            else{
                this.discount = 0.00;
            }
        }
        else {
            this.deliveryDiscount = 0.00;
            if (minOrder == 10 && maxOrder == 20) {
            this.discount = 10.00;
            }
            else if (minOrder == 20 && maxOrder == 30) {
            this.discount = 15.00;
            }
            else if (minOrder == 30 && maxOrder == 30) {
            this.discount = 20.00;
            }
            else{
                this.discount = 0.00;
            }
        }
   }
   //returns minOrder
   public Double getMinOrder(){
       return this.minOrder;
   }
   //returns maxOrder
   public Double getMaxOrder(){
    return this.maxOrder;
    }
    //returns discount
   public Double getDiscount(){
    return this.discount;
    }
    //returns delivery discount
   public Double getDeliveryDiscount(){
    return this.deliveryDiscount;
    }
    //returns number of restaurants
   public Integer getNumberOfRestaurants(){
    return this.numberOfRestaurants;
    }

   @Override
   public String toString() {
       return String.format("Number of Restaurants %d \nMinOrder: %.2f \nMaxOrder: %.2f \nDiscount: %.2f \nDelivery Discount: %.2f", this.numberOfRestaurants, this.minOrder, this.maxOrder, this.discount, this.deliveryDiscount);
   }
}