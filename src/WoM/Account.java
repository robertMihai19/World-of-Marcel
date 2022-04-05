package WoM;

import Caractere.Character;

import java.util.*;

public class Account {

    private Information info;
    private String maps_completed;
    private ArrayList<Character> characters;


    public Account() {
        info = new Information();
        characters = new ArrayList<>();
    }

    public static class Information {
        Credentials credentials;
        ArrayList<String> favorite_games;
        String name, country;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favorite_games = builder.favorite_games;
            this.name = builder.name;
            this.country = builder.name;
        }

        public Information() {
            credentials = new Credentials();
            favorite_games = new ArrayList<>();
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public ArrayList<String> getFavorite_games() {
            return favorite_games;
        }


        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setFavorite_games(ArrayList<String> favorite_games) {
            this.favorite_games = favorite_games;
        }

        public void addFavourite_games(String favorite_games) {
            this.favorite_games.add(favorite_games);
        }

        public static class InformationBuilder {
            private Credentials credentials;
            private ArrayList<String> favorite_games;
            private String name, country;

            public InformationBuilder(String name, String country) {
                this.name = name;
                this.country = country;
            }

            public void setCredentials(Credentials credentials) {
                this.credentials = credentials;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setFavorite_games(ArrayList<String> favorite_games) {
                this.favorite_games = favorite_games;
            }

            public Information build() {
                return new Information(this);
            }
        }

        @Override
        public String toString() {
            return ", credentials=" + credentials +
                    ", name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    ", favorite_games=" + favorite_games.toString();
        }

    }

    public Information getInfo() {
        return info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public void setMaps_completed(String maps_completed) {
        this.maps_completed = maps_completed;
    }

    public String getMaps_completed() {
        return maps_completed;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void addCharacter(Character caracter) {
        characters.add(caracter);
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    @Override
    public String toString() {
        return "Account{" +
                "maps_completed='" + maps_completed + '\'' +
                ", characters=" + characters.toString() +
                ", Information=" + info +
                '}';
    }
}
