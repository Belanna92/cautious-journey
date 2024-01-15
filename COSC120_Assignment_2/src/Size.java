/**
 * The purpose of this enumerator class is to store the various T-Shirt sizes that are stocked in the inventory.
 */
/*
 No matter the brand the T-Shirt sizes never change.
 Therefore, storing the sizes as enumerator constants will assist to keep the consistency and avoid errors when referring to them.
 Using an Enum prevents bad data from being entered into the program, which in turn prevents the need to have the input validated when the user enters string data.
 It is appropriate to use it for t-shirt sizes because the sizes are a finite, well-defined set.
*/
public enum Size {
    XS,S,M,L,XL,XXL,XXXL,XXXXL;

    /**
     * The purpose of this method is to return easier to read versions of the Size enumerators.
     * @return String version of enumerator.
     */
    public String toString(){
        return switch (this) {
            case XS -> "Extra small";
            case S -> "Small";
            case M -> "Medium";
            case L -> "Large";
            case XL -> "Extra large (XL)";
            case XXL -> "2XL";
            case XXXL -> "3XL";
            case XXXXL -> "4XL";
        };
    }
}
