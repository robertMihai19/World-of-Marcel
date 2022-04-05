package WoM;

public class Credentials {
    private String email, password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
