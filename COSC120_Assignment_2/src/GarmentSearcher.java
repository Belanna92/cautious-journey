import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * The purpose of the GarmentSearcher is to allow users to enter criteria of a desired Garment and then be shown options of Garments that match their search criteria.
 * This class uses the Garment, GarmentSpecs, Geek and Inventory classes, which allows users to:
 * Enter search criteria for their desired Garment.
 * Choose from Garments that are available in the inventory that match their search criteria.
 * Enter their information to place an order for a Garment.
 */
public class GarmentSearcher {

    // Create a variable to store the filepath for easy reference.
    private static final String filePath = "./inventory_v2.txt";
    // Create a variable to store an Inventory object.
    private static Inventory allGarments;
    // Create a variable to store the appName for easy reference.
    private static final String appName = "Greek Geek's Garment Getter";

    /**
     * The purpose of the main method is to request information from the user and display appropriate information back accordingly.
     * loadInventory, getFilters, and processSearchResults are called in this method.
     * @param args not required
     */
    public static void main(String[] args) {
        // Load inventory data.
        allGarments = loadInventory(filePath);
        // Get the users Garment requirements.
        GarmentSpecs usersDesiredGarment = getFilters();
        // Display search results and take the user's order.
        processSearchResults(usersDesiredGarment);
        // End program.
        System.exit(0);
    }

    /**
     * The purpose of the getFilters method is to get the users search criteria
     * @return a GarmentSpecs object that represents the Garment the user desires.
     */
    public static GarmentSpecs getFilters(){

        // Create a map to store the user's search Criteria
        Map<Filter, Object> filterObjectMap = new HashMap<>();

        GarmentType garmentType = (GarmentType) JOptionPane.showInputDialog(null,"Welcome to the Greek Geek's Garment Getter!\n\nPlease select your preferred garment type:",appName, JOptionPane.QUESTION_MESSAGE,null,GarmentType.values(),GarmentType.HOODIE);
        if(garmentType==null)System.exit(0);
        filterObjectMap.put(Filter.GARMENT_TYPE, garmentType);

        String material = (String) JOptionPane.showInputDialog(null,"Please select your preferred material or select NA:",appName, JOptionPane.QUESTION_MESSAGE,null,allGarments.getAllMaterials().toArray(), "");
        if(material==null)System.exit(0);
        if(!material.equals("NA")) filterObjectMap.put(Filter.MATERIAL, material);

        Size size = (Size) JOptionPane.showInputDialog(null,"Please select your preferred size (XS - 4XL):",appName, JOptionPane.QUESTION_MESSAGE,null,Size.values(),Size.M);
        if(size==null)System.exit(0);
        filterObjectMap.put(Filter.SIZE, size);

        String brand = (String) JOptionPane.showInputDialog(null,"Please select your preferred brand:",appName, JOptionPane.QUESTION_MESSAGE,null,allGarments.getAllBrands().toArray(), "");
        if(brand==null)System.exit(0);
        if(!brand.equals("NA")) filterObjectMap.put(Filter.BRAND, brand);

        // Use an if statement to check the garment type selected by the user, and then display appropriate search criteria options.
        if(garmentType == GarmentType.HOODIE){

            HoodieStyle hoodieStyle = (HoodieStyle) JOptionPane.showInputDialog(null,"Please select your preferred Hoodie Style or select NA:",appName, JOptionPane.QUESTION_MESSAGE,null,HoodieStyle.values(),HoodieStyle.NA);
            if(hoodieStyle==null)System.exit(0);
            if(!hoodieStyle.equals(HoodieStyle.NA)) filterObjectMap.put(Filter.HOODIE_STYLE, hoodieStyle);

            HoodiePocketType hoodiePocketType = (HoodiePocketType) JOptionPane.showInputDialog(null,"Please select your preferred Hoodie Pocket Type or select NA:",appName, JOptionPane.QUESTION_MESSAGE,null,HoodiePocketType.values(),HoodiePocketType.NA);
            if(hoodiePocketType==null)System.exit(0);
            if(!hoodiePocketType.equals(HoodiePocketType.NA)) filterObjectMap.put(Filter.HOODIE_POCKET_TYPE, hoodiePocketType);

        } else if(garmentType == GarmentType.T_SHIRT){

            TShirtSleeveType tShirtSleeveType = (TShirtSleeveType) JOptionPane.showInputDialog(null,"Please select your preferred T-Shirt Sleeve Type or select NA:",appName, JOptionPane.QUESTION_MESSAGE,null,TShirtSleeveType.values(),TShirtSleeveType.NA);
            if(tShirtSleeveType==null)System.exit(0);
            if(!tShirtSleeveType.equals(TShirtSleeveType.NA)) filterObjectMap.put(Filter.T_SHIRT_SLEEVE_TYPE, tShirtSleeveType);

            Neckline neckline = (Neckline) JOptionPane.showInputDialog(null,"Please select your preferred neckline or select NA:",appName, JOptionPane.QUESTION_MESSAGE,null,Neckline.values(),Neckline.CREW);
            if(neckline==null)System.exit(0);
            if(!neckline.equals(Neckline.NA)) filterObjectMap.put(Filter.NECKLINE, neckline);
        }

        int minPrice=-1,maxPrice = -1;
        while(minPrice<0) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter your lowest preferred price", appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null)System.exit(0);
            try {
                minPrice = Integer.parseInt(userInput);
                if(minPrice<0) JOptionPane.showMessageDialog(null,"Price must be >= 0.",appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        while(maxPrice<minPrice) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter your highest preferred price", appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null)System.exit(0);
            try {
                maxPrice = Integer.parseInt(userInput);
                if(maxPrice<minPrice) JOptionPane.showMessageDialog(null,"Price must be >= "+minPrice,appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }

        }

        return new GarmentSpecs(filterObjectMap, minPrice, maxPrice);
    }

    /**
     * The purpose of the processSearchResults method is to display a message to the user to show them compatible Garments.
     * Alternatively the user will be displayed a message stating that there were no compatible Garments.
     * The user will then be able to select one of the Garments to order.
     * The user will then need to enter their personal information to place the order. The calls the getUserContactInfo method to do so.
     * The user will then be displayed a message confirming their order.
     * @param dreamGarment a GarmentSpecs object representing the user's search criteria for their 'Dream Garment.'
     */
    public static void processSearchResults(GarmentSpecs dreamGarment){
        List<Garment> matchingGarments = allGarments.findMatch(dreamGarment);

        // Display all the compatible Garments to the user and ask the user which one they would like to purchase. The user can select the appropriate Garment from a drop-down menu.
        if(matchingGarments.size()>0) {
            Map<String, Garment> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following garments meet your criteria: \n");
            for (Garment matchingGarment : matchingGarments) {
                infoToShow.append(matchingGarment.getGarmentInformation());
                options.put(matchingGarment.getName(), matchingGarment);
            }
            // Take the users Garment choice and have them enter their personal information for the order.
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which garment you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, null, options.keySet().toArray(), "");
            if(choice==null) System.exit(0);
            Garment chosenGarment = options.get(choice);
            submitOrder(getUserContactInfo(),chosenGarment, (Size) dreamGarment.getFilterInfo(Filter.SIZE));
            JOptionPane.showMessageDialog(null,"Thank you! Your order has been submitted. "+
                    "One of our friendly staff will be in touch shortly.",appName, JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            // If there are no compatible shirt in the inventory then the user will be notified with a JOptionPane message.
            JOptionPane.showMessageDialog(null,"Unfortunately none of our garments meet your criteria :("+
                    "\n\tTo exit, click OK.",appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * The purpose of the getUserContactInfo method is to collect the user's information when they place an order
     * @return a Geek object representing the user and their associated contact information.
     */
    public static Geek getUserContactInfo(){
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your name.", appName, JOptionPane.QUESTION_MESSAGE);
            if(name==null) System.exit(0);
            if(name.length()==0) JOptionPane.showMessageDialog(null, "Invalid entry. You must enter your name.",appName, JOptionPane.ERROR_MESSAGE);
        }while (name.length()==0);

        long phoneNumber = 0;
        String phoneNumberInput = "";
        while(phoneNumberInput.length()!=10 || phoneNumber<=0){
            phoneNumberInput = JOptionPane.showInputDialog(null,"Please enter your 10-digit phone number:",appName,JOptionPane.QUESTION_MESSAGE);
            if (phoneNumberInput == null) System.exit(0);
            try {
                phoneNumber = Long.parseLong(phoneNumberInput);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid entry. Please enter numbers only. No special characters!",appName, JOptionPane.ERROR_MESSAGE);
                phoneNumber=0;
            }
            if(phoneNumber!=0 && phoneNumberInput.length()!=10 || phoneNumber<0)
                JOptionPane.showMessageDialog(null, "Invalid entry. Phone number must be comprised of 10 positive integers.",appName, JOptionPane.ERROR_MESSAGE);
        }
        return new Geek(name,phoneNumber);
    }

    /**
     * The purpose of the submitOrder method is to write the user's Garment order to a text file.
     * @param geek a Geek object representing the user and their information.
     * @param garment a Garment object representing the Garment the user has selected to purchase.
     * @param size a Size enum representing the users size.
     */
    public static void submitOrder(Geek geek, Garment garment, Size size) {
        String filePath = geek.getName().replace(" ","_")+"_"+garment.getProductCode()+".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" +
                "Name: "+geek.getName()+
                "\n\tPhone number: 0"+geek.getPhoneNumber()+
                "\n\tItem: "+garment.getName()+" ("+garment.getProductCode()+")" +
                "\n\tSize: "+size;
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("Order could not be placed. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

    /**
     * The purpose of the loadInventoryData method is to load the inventory data from a text file.
     * @param filePath is a String that is taken representing the file location of the inventory text file.
     * @return an Inventory object which is a Set of Garment objects.
     */
    public static Inventory loadInventory(String filePath) {
        Inventory allGarments = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("File could not be found");
            System.exit(0);
        }

        for(int i=1;i<fileContents.size();i++){
            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");
            String sizesRaw = info[1].replace("]","");
            String description = info[2].replace("]","");

            GarmentType garmentType = GarmentType.HOODIE;
            try {
                garmentType = GarmentType.valueOf(singularInfo[0].toUpperCase().replace("-","_"));
            }catch (IllegalArgumentException e) {
                System.out.println("Error in file. Garment Type could not be parsed for garment on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                System.exit(0);
            }

            String name = singularInfo[1];

            long productCode = 0;
            try{
                productCode = Long.parseLong(singularInfo[2]);
            }catch (NumberFormatException n) {
                System.out.println("Error in file. Product code could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            double price = 0;
            try{
                price = Double.parseDouble(singularInfo[3]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Price could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String brand = singularInfo[4];

            String material = singularInfo[5];

            Neckline neckline = Neckline.NA;
            try{
                neckline = Neckline.valueOf(singularInfo[6].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Neckline data could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            TShirtSleeveType tShirtSleeveType = TShirtSleeveType.NA;
            try{
                    tShirtSleeveType = TShirtSleeveType.valueOf(singularInfo[7].toUpperCase().replace(" ", "_"));
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Sleeve Style data could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
                }

            HoodiePocketType hoodiePocketType = HoodiePocketType.NA;
            try{
                hoodiePocketType = HoodiePocketType.valueOf(singularInfo[8].toUpperCase().replace(" ", "_"));
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Hoodie Pocket data could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            HoodieStyle hoodieStyle = HoodieStyle.NA;
            try{
                hoodieStyle = HoodieStyle.valueOf(singularInfo[9].toUpperCase().replace(" ", "_"));
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Hoodie Style data could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            Set<Size> sizes = new HashSet<>();
            for(String s: sizesRaw.split(",")){
                Size size = Size.S;
                try {
                    size = Size.valueOf(s);
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Size data could not be parsed for garment on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
                }
                sizes.add(size);
            }
            /*
            Using an Object type for values in the map will allow for adding anything to the map.
             */
            Map<Filter, Object> filterObjectMap = new LinkedHashMap<>();
            filterObjectMap.put(Filter.GARMENT_TYPE, garmentType);
            filterObjectMap.put(Filter.SIZE, sizes);
            if(!brand.equals("NA")) filterObjectMap.put(Filter.BRAND, brand);
            if(!material.equals("NA")) filterObjectMap.put(Filter.MATERIAL, material);
            if(!neckline.equals(Neckline.NA)) filterObjectMap.put(Filter.NECKLINE, neckline);
            if(!tShirtSleeveType.equals(TShirtSleeveType.NA)) filterObjectMap.put(Filter.T_SHIRT_SLEEVE_TYPE, tShirtSleeveType);
            if(!hoodiePocketType.equals(HoodiePocketType.NA)) filterObjectMap.put(Filter.HOODIE_POCKET_TYPE, hoodiePocketType);
            if(!hoodieStyle.equals(HoodieStyle.NA)) filterObjectMap.put(Filter.HOODIE_STYLE, hoodieStyle);

            GarmentSpecs garmentSpecs = new GarmentSpecs(filterObjectMap);
            Garment garment = new Garment(name, productCode, price, description, garmentSpecs);

            allGarments.addGarment(garment);
        }
        return allGarments;
    }

}
