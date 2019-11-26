package server.model;

public class NewUser {
    private String login;
    private String password;
    private String repeatedPasswrod;

    public NewUser() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPasswrod() {
        return repeatedPasswrod;
    }

    public void setRepeatedPasswrod(String repeatedPasswrod) {
        this.repeatedPasswrod = repeatedPasswrod;
    }
}
