import java.util.*;

/**
 * The purpose of this Class is to store all the Garment objects in a single Inventory list object.
 */
/*
It would be a poor design choice to use inheritance to make the requested updates to this program.
I could change the current Garment class to a Garment class, and change GarmentSpecs to GarmentSpecs.
And then create two new subclasses, GarmentSpecs and HoodieSpecs, which would extend the GarmentSpecs class.
However, this would require multiple new classes to be created,
and if further garments were added to the inventory in the future I would end up with a lot of classes.
Inheritance also tightly couples classes, and it might not always be appropriate as further changes to the program need to be made.

Instead of using Inheritance, using a Map data structure is a better design choice.
Using a Map data structure will reduce the number of classes that need to be created as new types of clothing are added to the inventory.
This will also allow the user to select the garment type they would like ot search for using an enum,
as well as future-proofing the program, allowing new garment types to be added to the inventory through adding an extra
type to enum object.

Overall, using a Map data structure over Inheritance will reduce the number of objects that need to be created,
and create a more robust program that will be easier to change in the future.
 */

public class Inventory {

    // Create the variable of the Inventory object.
    private final Set<Garment> allGarments = new HashSet<>();

    /**
     * The purpose of the addGarment method, is to add a Garment to the Inventory object.
     * @param garment is a Garment object that represents one item in the inventory.
     */
    public void addGarment(Garment garment){
        this.allGarments.add(garment);
    }

    /**
     * The purpose of the getAllBrands method is to return a Set of all the brands available in the inventory.
     * @return a Set of all the brands currently available in the inventory.
     */
    public Set<String> getAllBrands(){
        Set<String> allBrands = new HashSet<>();
        allBrands.add("NA");
        for(Garment garment: allGarments){
            allBrands.add(garment.getFilters().getFilterInfo(Filter.BRAND).toString());
        }
        return allBrands;
    }

    /**
     * The purpose of the getAllMaterials method is to return a Set of all the brands available in the inventory.
     * @return a Set of all the material currently available in the inventory.
     */
    public Set<String> getAllMaterials(){
        Set<String> allMaterials = new HashSet<>();
        allMaterials.add("NA");
        for(Garment garment: allGarments){
            allMaterials.add(garment.getFilters().getFilterInfo(Filter.MATERIAL).toString());
        }
        return allMaterials;
    }

    /**
     * The purpose of the findMatch method is to find all the Garments currently in the inventory that match with the users search requirements.
     * @param dreamGarment is a GarmentSpecs object that represents the user's search criteria.
     * @return a list of Garment objects currently available in the inventory that match the user's search criteria.
     */
    public List<Garment> findMatch(GarmentSpecs dreamGarment){
        List<Garment> matchingGarments = new ArrayList<>();
        for(Garment garment: allGarments){
            GarmentSpecs filters = garment.getFilters();
            if(garment.getPrice()<dreamGarment.getMinPrice()||garment.getPrice()>dreamGarment.getMaxPrice()) continue;
            if(dreamGarment.meetsCriteria(filters))
            matchingGarments.add(garment);
        }
        return matchingGarments;
    }

}
