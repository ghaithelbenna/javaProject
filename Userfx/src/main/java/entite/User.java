package entite;

public class User {
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String roles;

    public User(int id, String nom, String prenom, String email, String password, String roles) {
        this.id = id;

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User( String nom, String prenom, String email, String password, String roles) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}

