import java.text.DecimalFormat;
/**
 * The purpose of the Garment class is to create instances of Garment objects.
 */
public class Garment {

    // Create variables that make up a Garment object
    private final String name;
    private final long productCode;
    private final double price;
    private final String description;
    private final GarmentSpecs filters;

    /**
     * The purpose of this constructor is to create a Garment object.
     * @param name is a String value representing the name of the Garment.
     * @param productCode is a long value representing the product code of the Garment.
     * @param price is a float value representing the price of the Garment.
     * @param description is a string value representing the description of the Garment.
     */
    public Garment(String name,long productCode, double price, String description, GarmentSpecs filters) {
        this.name=name;
        this.productCode = productCode;
        this.price = price;
        this.description = description;
        this.filters = filters;
    }

    /**
     * The purpose of this getter is to return the Garment object's name.
     * @return a string value representing the name of the Garment object.
     */
    public String getName(){
        return name;
    }

    /**
     * The purpose of this getter is to return the Garment object's product code.
     * @return a long value representing the Garment object's product code.
     */
    public long getProductCode() {
        return productCode;
    }

    /**
     * The purpose of this getter is to return the Garment object's price.
     * @return a float value representing the Garment object's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The purpose of this getter is to return the Garment object's description.
     * @return a String value representing the Garment object's description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * The purpose of this getter is to return the Garment object's GarmentSpecs information.
     * @return a GarmentSpecs objects representing the generic data all Garment objects share.
     */
    public GarmentSpecs getFilters() {
        return filters;
    }

    /**
     * The purpose of the garmentDescription method is to convert Garment object information into a user-friendly string to display to the user.
     * @return a String representing user-friendly Garment object data.
     */
    public String getGarmentInformation(){
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: "+this.getName()+"\nCaption: "+this.getDescription() +"\n\tProduct code: "+this.getProductCode()
                +filters.getDescription()+"\n\tPrice: $"+df.format(this.getPrice())+"\n";
    }
}
