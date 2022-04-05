package Caractere;

public class CharacterFactory {

    public CharacterFactory() {

    }

    public static Character factory(String name, String type, String level, int exp) {
        return switch (type) {
            case "Warrior" -> new Warrior(name, level, exp);
            case "Rogue" -> new Rogue(name, level, exp);
            case "Mage" -> new Mage(name, level, exp);
            default -> null;
        };
    }

}
