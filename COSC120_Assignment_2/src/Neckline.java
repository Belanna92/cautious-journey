/**
 * The purpose of this enumerator class is to store the various Neckline styles that are stocked in the inventory.
 */
/*
 T-Shirt necklines never change.
 Therefore, storing the necklines as enumerator constants will assist to keep consistency and avoid errors when referring to them.
 Using an Enum prevents bad data from being entered into the program, which in turn prevents the need to have the input validated when the user enters string data.
 It is appropriate to use it for t-shirt necklines because the necklines are a finite, well-defined set.
*/
public enum Neckline {
    CREW, HIGH, SCOOP, V, NA;

    /**
     * The purpose of this method is to return easier to read versions of the neckline enumerators.
     * @return String version of enumerator.
     */
    public String toString(){
        return switch (this){
            case CREW -> "Crew Neck";
            case HIGH -> "High Neck";
            case SCOOP -> "Scoop Neck";
            case V -> "V Neck";
            case NA -> "Any Neckline";
        };
    }
}
