package nl.rrx.object;

public enum Weapon {
    COMMON_SWORD("sword-common.png", 1),
    ;

    public final String imageUri;
    public final int attack;

    Weapon(String fileName, int attack) {
        this.imageUri = "/images/weapon/" + fileName;
        this.attack = attack;
    }
}
