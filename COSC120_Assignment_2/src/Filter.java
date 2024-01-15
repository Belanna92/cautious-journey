public enum Filter {
    GARMENT_TYPE, NECKLINE, SIZE, HOODIE_STYLE, HOODIE_POCKET_TYPE, T_SHIRT_SLEEVE_TYPE, BRAND, MATERIAL;

    public String toString(){
        return switch (this) {
            case GARMENT_TYPE -> "Garment Type";
            case NECKLINE -> "Neckline";
            case SIZE -> "Size";
            case HOODIE_STYLE -> "Hoodie Style";
            case HOODIE_POCKET_TYPE -> "Hoodie Pocket Type";
            case T_SHIRT_SLEEVE_TYPE -> "T-Shirt Sleeve Type";
            case BRAND -> "Brand";
            case MATERIAL -> "Material Type";
        };
    }
}
