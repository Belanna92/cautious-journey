public enum GarmentType {
    HOODIE, T_SHIRT;

    public String toString(){
        return switch (this){
            case HOODIE -> "Hoodie";
            case T_SHIRT -> "T-Shirt";
        };
    }
}
