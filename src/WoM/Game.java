package WoM;


import Backpack.*;
import Caractere.*;
import Caractere.Character;
import Mapa.*;
import Spells.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;

import Mapa.*;

import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class Game {

    private static Game joc = null;
    public ArrayList<Account> accounts;

    enum CellEnum {EMPTY, ENEMY, SHOP, FINISH}

    Map<CellEnum, ArrayList<String>> replici;


    private Game() {
        accounts = new ArrayList<>();
        replici = new HashMap<>();
    }

    public static Game getInstance() {
        if (joc == null) {
            joc = new Game();
        }
        return joc;
    }

    public void runGrafic() {
        this.parseAccount();
        this.parseStories();
        LoginFrame start = new LoginFrame(accounts);
        start.run();
    }

    public void runHardcodat() {
        try {
            BufferedReader readder = new BufferedReader(new InputStreamReader(System.in));
            String read, email, parola;
            parseAccount();
            parseStories();
            System.out.println("Introduceti emailul si parola");
            System.out.print("email:");
//            email = "marcel@yahoo.com";
            email = readder.readLine();
            System.out.print("parola:");
//            parola = "6K7GUxjsAc";
            parola = readder.readLine();
            Account logare = null;
            for (Account conturi : accounts) {
                Credentials validare = conturi.getInfo().getCredentials();
                if (validare.getEmail().equals(email) && validare.getPassword().equals(parola)) {
                    logare = conturi;
                }
            }
            if (logare != null) {
                Character player = logare.getCharacters().get(0);
                player.inventar.money = 50;
                int n = 5, m = 5;
                Grid map = Grid.generateMapHardocat(5, 5, player);
                while (!map.get(map.caracter.x).get(map.caracter.y).gen.toCharacter().equals("F")) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++)
                            if (map.caracter.x == i && map.caracter.y == j)
                                System.out.print("P ");
                            else if (!map.get(i).get(j).checked) {
                                System.out.print("? ");
                            } else System.out.print(map.get(i).get(j).gen.toCharacter() + " ");
                        System.out.println();
                    }

                    read = readder.readLine();
                    if (read.equals("P") || read.equals("p")) {
                        if (map.goEast()) {
                            sayStory(map.get(player.x).get(player.y).gen.toCharacter());
                            {
                                sayStory(map.get(player.x).get(player.y).gen.toCharacter());
                                if (map.get(map.caracter.x).get(map.caracter.y).gen.toCharacter().equals("S")) {
                                    Shop magazin = ((Shop) map.get(map.caracter.x).get(map.caracter.y).gen);
                                    while (magazin.potiuni.size() != 0) {
                                        Potion licoare = magazin.buyPotion(0);
                                        System.out.println("Ai cumparat :" + licoare.toString());
                                        player.inventar.addPotion(licoare);
                                    }
                                }
                            }
                        } else {
                            map.goSouth();
                            if (map.get(map.caracter.x).get(map.caracter.y).gen.toCharacter().equals("E")) {
                                sayStory(map.get(player.x).get(player.y).gen.toCharacter());
                                Enemy inamic = (Enemy) (map.get(map.caracter.x).get(map.caracter.y).gen);
                                for (Spell vraja : player.abilitati) {
                                    System.out.println("Ai folosit vraja " + vraja.toString());
                                    player.useSpell(vraja, inamic);
                                    if (new Random().nextInt(0, 5) == 0)
                                        inamic.useSpell(inamic.abilitati.get(new Random().nextInt(0, inamic.abilitati.size())), player);
                                    else
                                        player.receiveDamage(inamic.getDamage());
                                    read = readder.readLine();
                                    while (!read.equals("P") && !read.equals("p")) {
                                        System.out.println("Comanda gresita");
                                        read = readder.readLine();
                                    }
                                }
                                for (int i = 0; i < 2; i++) {
                                    Potion potiune = player.inventar.removePotion(0);
                                    if (potiune.getClass().getSimpleName().equals("HealthPotion")) {
                                        System.out.println("Ai folosit potiunea de viata");
                                        player.currentLife += potiune.regenValue();
                                    } else if (potiune.getClass().getSimpleName().equals("ManaPotion")) {
                                        System.out.println("Ai folosit potiunea de mana");
                                        player.currentMana += potiune.regenValue();
                                    }
                                    if (new Random().nextInt(0, 5) == 0)
                                        inamic.useSpell(inamic.abilitati.get(new Random().nextInt(0, inamic.abilitati.size())), player);
                                    else
                                        player.receiveDamage(inamic.getDamage());
                                }
                                while (player.currentLife > 0 && inamic.currentLife > 0) {
                                    read = readder.readLine();
                                    inamic.receiveDamage(player.getDamage());
                                    if (new Random().nextInt(0, 5) == 0)
                                        inamic.useSpell(inamic.abilitati.get(new Random().nextInt(0, inamic.abilitati.size())), player);
                                    else
                                        player.receiveDamage(inamic.getDamage());
                                    while (!read.equals("P") && !read.equals("p")) {
                                        System.out.println("Comanda gresita");
                                        read = readder.readLine();
                                    }
                                }
                                if (player.currentLife > 0)
                                    System.out.println("Ai omorat inamicul");
                                else System.out.println("Ai fost omorat");
                            }
                        }
                    } else System.out.println("Comanda invalida");
                }
                System.out.println("Ai ajuns la final");
                sayStory(map.get(player.x).get(player.y).gen.toCharacter());
            }
        } catch (Exception event) {
            event.printStackTrace();
        }
    }

    public void sayInstructions(String cell) {
        if (cell.equals("S")) {
            System.out.println("Ai ajuns la un shop");
        }
    }

    public void sayStory(String cell) {
        if (cell.equals("N")) {
            System.out.println(replici.get(CellEnum.EMPTY).get(new Random().nextInt(replici.get(CellEnum.EMPTY).size())));
        } else if (cell.equals("S")) {
            System.out.println(replici.get(CellEnum.SHOP).get(new Random().nextInt(replici.get(CellEnum.SHOP).size())));
        } else if (cell.equals("E")) {
            System.out.println(replici.get(CellEnum.ENEMY).get(new Random().nextInt(replici.get(CellEnum.ENEMY).size())));
        } else if (cell.equals("F")) {
            System.out.println(replici.get(CellEnum.FINISH).get(new Random().nextInt(replici.get(CellEnum.FINISH).size())));
        }
    }

    public void parseAccount() {
        try {
            int i;
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jsonParser = new JsonFactory().createParser(new File("src\\WoM\\accounts.json"));
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String field = jsonParser.getCurrentName();
                if ("accounts".equals(field)) {
                    jsonParser.nextToken();
                    ArrayNode node = mapper.readTree(jsonParser);

                    for (JsonNode nodulet : node) {
                        Account cont = new Account();
                        Credentials credentials;
                        String name, country;
                        ArrayList<String> favorite_games = new ArrayList<>();
                        Account.Information info;

                        cont.setMaps_completed(nodulet.get("maps_completed").textValue());
                        credentials = new Credentials(nodulet.get("credentials").get("email").textValue(), nodulet.get("credentials").get("password").textValue());
                        name = (nodulet.get("name").textValue());
                        country = (nodulet.get("country").textValue());
                        for (i = 0; i < nodulet.get("favorite_games").size(); i++) {
                            favorite_games.add(nodulet.get("favorite_games").get(i).textValue());
                        }
                        for (i = 0; i < nodulet.get("characters").size(); i++) {
                            JsonNode nodeNaspa = nodulet.get("characters").get(i);
                            cont.addCharacter(CharacterFactory.factory(nodeNaspa.get("name").textValue(), nodeNaspa.get("profession").textValue(), nodeNaspa.get("level").textValue(), nodeNaspa.get("experience").intValue()));
                        }

                        Account.Information.InformationBuilder builder = new Account.Information.InformationBuilder(name, country);
                        builder.setFavorite_games(favorite_games);
                        builder.setCredentials(credentials);
                        info = builder.build();

                        cont.setInfo(info);
                        this.addAccounts(cont);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseStories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jsonParser = new JsonFactory().createParser(new File("src\\WoM\\stories.json"));
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                jsonParser.nextToken();
                String field = jsonParser.getCurrentName();
                if (field.equals("stories")) {
                    jsonParser.nextToken();
                    ArrayNode node = mapper.readTree(jsonParser);
                    for (JsonNode nodulet : node) {
                        this.addMap(nodulet.get("type").asText(), nodulet.get("value").asText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccounts(Account cont) {
        this.accounts.add(cont);
    }

    public void addMap(String cell, String reply) {
        for (CellEnum celula : CellEnum.values()) {
            if (celula.name().equals(cell)) {
                ArrayList<String> ceva = replici.get(celula);
                if (ceva == null) {
                    ceva = new ArrayList<>();
                    ceva.add(reply);
                    replici.put(celula, ceva);
                } else {
                    ceva.add(reply);
                    replici.put(celula, ceva);
                }
            }
        }
    }

    public Map getMap() {
        return replici;
    }

    @Override
    public String toString() {
        return "Game{" +
                "accounts=" + accounts.toString() +
                '}';
    }
}
