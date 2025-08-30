package classes;

public class Session {
    private static Session instance;
    private String username;
    private int idUser;
    private String name;
    private int role;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public int getRole() {
        return role;
    }
}