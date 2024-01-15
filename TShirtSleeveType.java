public enum TShirtSleeveType {
    LONG, SHORT, SLEEVELESS, BAT_WING, PUFFED, NA;

    public String toString(){
        return switch (this){
            case NA -> "Any Sleeve Type";
            case LONG -> "Long Sleeves";
            case SHORT -> "Short Sleeves";
            case PUFFED -> "Puffed Sleeves";
            case SLEEVELESS -> "Sleeveless";
            case BAT_WING -> "Bat Wing Sleeves";
        };
    }
}
