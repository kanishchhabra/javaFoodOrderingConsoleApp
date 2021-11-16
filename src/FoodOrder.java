import java.util.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.IOException;

public class FoodOrder {
    //Following function loads data from the .txt file into Restaurant class.
    static LinkedList<Restaurant> restaurantLoader(){
        LinkedList <Restaurant> restaurantList = new LinkedList<>();
        try   
        {
            //Stores each line of the file to be read
            String line;  
            
            //Varialbles to store attributes for an instance of the restaurant class
            String name = null;
            String category = null;
            Double deliveryCost = null;
            HashMap<Integer, FoodItem> foodItems = new HashMap<Integer, FoodItem>();
            Integer hashKey = 0;

            //Following gets the data from restaurants.txt file
            BufferedReader restaurants = new BufferedReader(new FileReader("Restaurants.txt"));
              
            while ((line = restaurants.readLine()) != null)
            //Returns a Boolean value, enabling loop to be run until data on the new line exists in the file.
            {  
                String[] restaurant = line.split(",");
                // Using comma as separator for a line
                
                //Looping through each line representing attributes of the 'Restaurant'
                for (int i = 0; i < restaurant.length; i++) {
                    //1st column stores the name of the restaurant    
                    if (i == 0) {
                        name = restaurant[i];
                    }

                    //2nd column stores the category of the restaurant
                    else if(i == 1){
                        category = restaurant[i];
                    }

                    //3rd column stores the delivery price of the restaurant
                    else if(i == 2){
                        //'$' symbol has been omitted from the data read from the file using replace method.
                        deliveryCost = Double.parseDouble(restaurant[i].replace("$", ""));
                    }

                    //4th column onwards restaurant's menu starts
                    else{
                        hashKey ++;
                        //Splitting the menu items using '-' seperator into 'name' and 'price'
                        String[] temp;
                        temp = restaurant[i].split("-"); 

                        //From the array of two string objects, and instance of new FoodItem has been created and added the to the list
                        foodItems.put(hashKey, new FoodItem(temp[0], Double.parseDouble(temp[1].replace("$", ""))));
                        //'$' symbol has been omitted from the data read from the file using replace method.
                    }
                } 
                restaurantList.add(new Restaurant(name, category, deliveryCost, (HashMap) foodItems.clone()));
                foodItems.clear();
                hashKey = 0;
            }
            //Closing the 'restaurants' file
            restaurants.close();
        }   
        
        catch(IOException e)
        {
            System.out.println("\n-----------------\nError Occured. " + e.getMessage());
            System.exit(0);
        }
        return restaurantList; 
    }
    //Following function loads data from the .txt file into Discount class.
    static LinkedList<Discount> discountLoader(){
        LinkedList<Discount> discountsList = new LinkedList<>();
        try   
        {
            //Stores each line of the file to be read
            String line;  

            //Varialbles to store attributes for an instance of the discount class
            int numberOfRestaurants = 0;
            Double minOrder = 0.00; 
            Double maxOrder = 0.00;

            //Following gets the data from discount.txt file
            BufferedReader discounts = new BufferedReader(new FileReader("Discounts.txt"));

            while ((line = discounts.readLine()) != null)
            //Returns a Boolean value, enabling loop to be run until data on the new line exists in the file.
            { 
                // Using comma as separator for a line
                String[] discount = line.split("%");
                
                for (int i = 0; i < discount.length; i++) {
                    
                    //Formatting the discount line, removing the unnecessary symbols and signs
                    discount[i] = discount[i].replace("[", "");
                    discount[i] = discount[i].replace(")", "");
                    //Spliting discount line into elements i.e. columns so that limits on order can be determined
                    String[] limits = discount[i].split(",");
                    /*
                    -> Following code checks the number of parameters to determine the discount slabs.
                    -> If the parameters are two then number of restaurants is more than 1 and discount is flat.
                    -> If the parameters are three but there is no upper limit, then minOrder = maxOrder.
                    So, as long as discounts.txt file is loaded with data in the corrct format, the program will work.
                    */
                    if (limits.length == 2) {
                        //Stores each line of the file to be read
                        String nestedLine; 
                        numberOfRestaurants = Integer.parseInt(limits[0]);
                        //Following gets the data from discount.txt file
                        BufferedReader nestedDiscounts = new BufferedReader(new FileReader("Discounts.txt"));

                        while ((nestedLine = nestedDiscounts.readLine()) != null)
                        //Returns a Boolean value, enabling loop to be run until data on the new line exists in the file.
                        { 
                            // Using comma as separator for a line
                            String[] nestedDiscount = nestedLine.split("%");
                            for (int j = 0; j < nestedDiscount.length; j++) { 
                                //Formatting the discount line, removing the unnecessary symbols and signs
                                nestedDiscount[i] = nestedDiscount[i].replace("[", "");
                                nestedDiscount[i] = nestedDiscount[i].replace(")", "");
                                //Spliting discount line into elements i.e. columns so that limits on order can be determined
                                String[] nestedLimits = nestedDiscount[i].split(","); 
                                if (nestedLimits.length == 3) 
                                {
                                    if (nestedLimits[1].isEmpty()){
                                        minOrder = Double.parseDouble(nestedLimits[0]);
                                        maxOrder = minOrder;
                                    }
                                    else{
                                        minOrder = Double.parseDouble(nestedLimits[0]);
                                        maxOrder = Double.parseDouble(nestedLimits[1]);
                                    }
                                    discountsList.add(new Discount(numberOfRestaurants, minOrder, maxOrder));
                                }
                            }
                        }
                        nestedDiscounts.close();
                    }
                    else if (limits.length == 3) {
                        if (limits[1].isEmpty()) {
                            numberOfRestaurants = 1;
                            minOrder = Double.parseDouble(limits[0]);
                            maxOrder = minOrder;
                        }
                        else {
                            numberOfRestaurants = 1;
                            minOrder = Double.parseDouble(limits[0]);
                            maxOrder = Double.parseDouble(limits[1]);
                        }
                        discountsList.add(new Discount(numberOfRestaurants, minOrder, maxOrder));
                    }
                }
            }  
            discounts.close();  
        } 
        catch (FileNotFoundException e)
        {  
            System.out.println("File not Found. " + e.getMessage());
            System.exit(0);   
        }
        catch(IOException e)
        {
            System.out.println("Error Occured. " + e.getMessage());
            System.exit(0);
        }
        return discountsList;
    }
    //Following function creates categories
    static LinkedList<String> createCategories(LinkedList <Restaurant> restaurantList){
        LinkedList<String> categories = new LinkedList<>();
        for (int i = 0; i < restaurantList.size(); i++) {
            if (!categories.contains(restaurantList.get(i).getCategory())) {
                categories.add(restaurantList.get(i).getCategory());
            }
        }
        return categories;
    }
    //Returns filtered restaurants based on category
    static LinkedList<Restaurant> restaurantsOrganizer(String category, LinkedList <Restaurant> restaurantList){
        LinkedList <Restaurant> filteredRestaurants = new LinkedList<>();
        for (int i = 0; i < restaurantList.size(); i++) {
            if (restaurantList.get(i).getCategory().equals(category)) {
                filteredRestaurants.add(restaurantList.get(i));
            }
        }
        return filteredRestaurants;
    }
    //Searches the restaurant with name
    static LinkedList<Restaurant> restaurantsSearch(String searchKey, LinkedList <Restaurant> restaurantList){
        LinkedList <Restaurant> searchedRestaurants = new LinkedList<>();
        for (int index = 0; index < restaurantList.size(); index++) {
            if (restaurantList.get(index).getName().toLowerCase().contains(searchKey.toLowerCase())) {
                searchedRestaurants.add(restaurantList.get(index));
            }
        }
        return searchedRestaurants;
    }
    
    //Following methods are used to display information on the console.

   //Lists down the restaurants falling into user's selected category
    static LinkedList <Restaurant> displayRestaurants(String key, LinkedList<String> categories, LinkedList <Restaurant> filteredRestaurants, LinkedList <Restaurant> restaurantList){
        Boolean flag = true;
        Scanner userInput = new Scanner(System.in);
        while (flag) {
            try {
                if (Integer.parseInt(key) <= categories.size()) {
    
                    filteredRestaurants = restaurantsOrganizer(categories.get(Integer.parseInt(key) - 1), restaurantList);
                    System.out.println("----------------------------------");
                    for (int i = 0; i < filteredRestaurants.size(); i++) {
                        System.out.println(
                                String.format("%d. %s", i+1, filteredRestaurants.get(i).getName())
                            );
                    }
                    System.out.println("----------------------------------");
                    return filteredRestaurants;
                    
                }else{
                    
                    System.out.println("----------------------------------\nPlease Enter a Valid Option");
                    key = userInput.nextLine();
                }
            } catch (Exception e) {
                System.out.println("----------------------------------\nInvalid Input");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------\n");
                System.out.println("Enter 0 to go back to the menu or 'exit' to quit the program.");
                System.out.println("Please Select Again: ");
                key = userInput.nextLine();
            }
        }
        userInput.close();
        return filteredRestaurants;
    }
    //Lists down the food items available at the restaurant
    static Restaurant displayFoodItems(String key, LinkedList <Restaurant> filteredRestaurants){
        Boolean flag = true;
        Scanner userInput = new Scanner(System.in);
        while (flag) {
        try {
            if (Integer.parseInt(key) <= filteredRestaurants.size()) {

                System.out.println("----------------------------------");
                Restaurant selectedRestaurant = filteredRestaurants.get(Integer.parseInt(key) - 1);
                System.out.println("Ordering from " + selectedRestaurant.getName());
                for (int i = 0; i < selectedRestaurant.getFoodItems().length; i++) {
                    System.out.println(
                            String.format("%s", selectedRestaurant.getFoodItems()[i].toString())
                        );
                }
                System.out.println("----------------------------------");
                return selectedRestaurant;
                
            }else{
                System.out.println("----------------------------------\nPlease Enter a Valid Option");
                key = userInput.nextLine();
            }
        } catch (Exception e) {
            System.out.println("----------------------------------\nInvalid Input");
            System.out.println(e.getMessage());
            System.out.println("----------------------------------\n");
            System.out.println("Enter 0 to go back to the menu or 'exit' to quit the program.");
            System.out.println("Please Select Again: ");
            key = userInput.nextLine();
        }
    }
    userInput.close();
    return null;
    }
    
    public static void main (String[] args) { 
        LinkedList <Restaurant> restaurantList = restaurantLoader();
        LinkedList <Discount> discountsList = discountLoader();
        LinkedList <String> categories = createCategories(restaurantList);
        LinkedList <Restaurant> filteredRestaurants = new LinkedList<>();
        LinkedList <OrderLine> orderLines = new LinkedList<>();
        LinkedList <Integer> orderedKeys = new LinkedList<>();

        //Scanner object to get user-input
        Scanner userInput = new Scanner(System.in);

        //Checks if the outermost while loop needs to be repeated
        Boolean repeater = false;

        //Prints out the user-interface
        System.out.println("Welcome to Melbourne Eats");
        
        while (true) {
            repeater = false;
            System.out.println("----------------------------\nSelect from the Menu\n----------------------------");
            System.out.println("1. Search Restaurant\n2. Search by Category\n3. Checkout\n4. Exit");
            System.out.println("Please Select: ");

            String key = userInput.nextLine();
            ////Lists the categories
            Boolean flag = true; // Flags the while loop when to when to stop i.e. when the correct option is eetered by the user
            while(flag){
                switch (key) {
                    case "1":
                        System.out.println("Type Name: ");
                        String searchKey = userInput.nextLine();
                        filteredRestaurants = restaurantsSearch(searchKey, restaurantList);
                        if (filteredRestaurants.isEmpty()) {
                            System.out.println("Nothig Found.");
                            repeater = true;
                        }
                        else{
                            System.out.println("----------------------------------");
                            for (int i = 0; i < filteredRestaurants.size(); i++) {
                                System.out.println(
                                        String.format("%d. %s", i+1, filteredRestaurants.get(i).getName())
                                    );
                            }
                            System.out.println("----------------------------------");
                        }
                        flag = false;
                        break;
                    
                    case "2":
                        System.out.println("----------------------------------");
                        for (int i = 0; i < categories.size(); i++) {
                            System.out.println(
                                String.format("%d. %s", i+1, categories.get(i))
                            );
                        }
                        System.out.println("Enter 0 to go back to the menu or 'exit' to quit the program.");
                        System.out.println("Please Select: ");
                        key = userInput.nextLine();
                        if(key.equals("0")) continue;
                        if(key.equals("exit")) System.exit(0);
                        //Lists down the restaurants falling into user's selected category
                        filteredRestaurants = displayRestaurants(key, categories, filteredRestaurants, restaurantList);   
                        flag = false;
                        break;
                    
                    case "3":
                        if (orderLines.isEmpty()) {
                            System.out.println("You haven't ordered anything yet.\nPlease order something before checkout.");
                            repeater = true;
                            flag = false;
                            break;
                        }
                        else{
                            Order order = new Order(orderLines, discountsList);
                            order.checkout();
                            System.exit(0);
                        }
                    case "4":
                        System.exit(0);
                        break;
                    
                    default:
                        System.out.println("----------------------------------\nPlease Enter a Valid Option");
                        key = userInput.nextLine();
                        break;
                }     
            }
            if (repeater) {
                continue;
            }

            System.out.println("Enter 0 to go back to the menu or 'exit' to quit the program.");
            System.out.println("Please Select: ");
            key = userInput.nextLine();
            if(key.equals("0")) continue;
            if(key.equals("exit")) System.exit(0);

            //Lists down the food items available at the restaurant
            Restaurant selectedRestaurant = displayFoodItems(key, filteredRestaurants);
            
            //Keeps taking order until user goes back to the menu
            while (true) {
                System.out.println("Enter 0 to go back to the menu or 'exit' to quit the program.");
                System.out.println("Please Select: ");
                key = userInput.nextLine();
                //Takes Order
                try {
                    if(key.equals("0")) break;
                    else if(key.equals("exit")) System.exit(0);
                    else if (Integer.parseInt(key) <= selectedRestaurant.getFoodItems().length){
                        while (true) {
                            System.out.println("Enter Quantity: ");
                            String quantity = userInput.nextLine();
                            try {
                                if(key.equals("0")) break;
                                if(key.equals("exit")) System.exit(0);

                                //This function checks if the item is being re-ordered.
                                //If re-oredered following code will be executed.
                                if (orderedKeys.contains(Integer.parseInt(key))) {

                                    for (int index = 0; index < orderLines.size(); index++) {
                                        if (orderLines.get(index).getFoodName() == selectedRestaurant.getFoodItem(Integer.parseInt(key)).getName()) {
                                            orderLines.get(index).incrementQuantity(Integer.parseInt(quantity));;
                                        }
                                    }
                                }
                                //If the item is being ordered for the first time.
                                else{
                                    orderLines.add(new OrderLine(selectedRestaurant, Integer.parseInt(key), Integer.parseInt(quantity)));
                                    orderedKeys.add(Integer.parseInt(key));
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("----------------------------------\nInvalid Input");
                                System.out.println(e.getMessage());
                                System.out.println("----------------------------------");
                                continue;
                            }
                        }
                    }
                    else{
                        System.out.println("----------------------------------\nInvalid Option");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("----------------------------------\nInvalid Input");
                    System.out.println(e.getMessage());
                    System.out.println("----------------------------------");
                    continue;
                }
            }
        }
    }
}