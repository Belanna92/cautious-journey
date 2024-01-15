import java.util.*;

/**
 * The purpose of the GarmentSpecs class it to create instances of GarmentSpecs Objects.
 * GarmentSpecs objects represent the generic features of Garments that users may use as search criteria.
 */
public class GarmentSpecs {

    // Create variables that make up a Garment object
    private final Map<Filter, Object> filterObjectMap;
    private double minPrice;
    private double maxPrice;

    /**
     * The purpose of this constructor is to create instances of GarmentSpecs objects that include the minimum and maximum amount the user is willing to spend.
     * @param filterObjectMap is a map that contains the generic features the user may with to use as search criteria. These features are listed in the Filter enum, and also used as keys.
     * @param minPrice is a double value that represents the minimum amount the user is willing to spend on a garment.
     * @param maxPrice is a double value that represents the maximum amount the user is willing to spend on a garment.
     */
    public GarmentSpecs(Map<Filter, Object> filterObjectMap, double minPrice, double maxPrice) {
        this.filterObjectMap = filterObjectMap;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * The purpose of this constructor is to create instances of GarmentSpecs objects that represent the generic features of the garments in the inventory.
     * @param filterObjectMap is a map that contains the generic features the user may with to use as search criteria. These features are listed in the Filter enum, and also used as keys.
     */
    public GarmentSpecs(Map<Filter, Object> filterObjectMap) {
        this.filterObjectMap = new HashMap<>(filterObjectMap);
    }

    /**
     * The purpose of this getter is to get a map which contains the features of GarmentSpecs.
     * @return a map that contains the generic features the user may with to use as search criteria. These features are listed in the Filter enum, and also used as keys.
     */
    public Map<Filter, Object> getFilterObjectMap() {
        return new HashMap<>(filterObjectMap);
    }

    /**
     * The purpose of this getter is to return a value stored in the map of garment features.
     * @param key a Filter enum.
     * @return the value stored at the key.
     */
    public Object getFilterInfo(Filter key){
        return getFilterObjectMap().get(key);
    }

    /**
     * The purpose of this getter is to return the value determined by the user, that is the minimum amount they are willing to spend on a Garment.
     * @return an integer value representing the minimum price user is willing to spend on a Garment.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * The purpose of this getter is to return the value determined by the user, that is the maximum amount they are willing to spend on a Garment.
     * @return an integer value representing the maximum price user is willing to spend on a Garment.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * The purpose of this getter is to return an easy-to-read String of the GarmentSpecs object.
     * @return a String representing user-friendly GarmentSpecs object data.
     */
    public String getDescription(){
        StringBuilder garmentDescription = new StringBuilder();
        for(Filter key: filterObjectMap.keySet()){
            garmentDescription.append("\n").append(key).append(": ").append(getFilterInfo(key));
        }
        return garmentDescription.toString();
    }

    /**
     * The purpose of this method is to compare two GarmentSpecs features with each other to see if the criteria matches.
     * @param actualGarment a GarmentSpecs object representing the generic features of an actual Garment in the inventory.
     * @return true if the features match the criteria.
     */
    public boolean meetsCriteria(GarmentSpecs actualGarment) {
        for(Filter key : actualGarment.getFilterObjectMap().keySet()) {
            if(this.getFilterObjectMap().containsKey(key)){
                if(actualGarment.getFilterInfo(key) instanceof Collection<?> && !(getFilterInfo(key) instanceof Collection<?>)){
                    if(!((Collection<?>) actualGarment.getFilterInfo(key)).contains(getFilterInfo(key))) return false;
                }
                else if(!getFilterInfo(key).equals(actualGarment.getFilterInfo(key))) return false;
            }
        }
        return true;
    }


}
