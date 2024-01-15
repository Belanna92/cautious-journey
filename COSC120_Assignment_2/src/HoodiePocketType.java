public enum HoodiePocketType {
    KANGAROO, PATCH, ZIPPER, SLASH, FAUX, NA;

    public String toString(){
        return switch (this){
            case KANGAROO -> "Kangaroo Pocket";
            case FAUX -> "Faux Pocket";
            case PATCH -> "Patch Pocket";
            case SLASH -> "Slash Pocket";
            case ZIPPER -> "Zipper Pocket";
            case NA -> "Any Pocket Type";
        };
    }
}
