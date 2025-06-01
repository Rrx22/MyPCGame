package nl.rrx.object;

public enum Shield {
    COMMON("shield-common.png", 1),
    ;


    public final String imageUri;
    public final int defence;

    Shield(String fileName, int defence) {
        this.imageUri = "/images/weapon/" + fileName;
        this.defence = defence;
    }
}
