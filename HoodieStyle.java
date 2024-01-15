public enum HoodieStyle {
    PULLOVER, ZIP_UP, OVER_SIZED, ATHLETIC, NA;

    public String toString(){
        return switch(this){
            case PULLOVER -> "Pullover";
            case ZIP_UP -> "Zip Up";
            case OVER_SIZED -> "Over Sized";
            case ATHLETIC -> "Athletic";
            case NA -> "Any Style";
        };
    }
}
