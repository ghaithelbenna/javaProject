package tn.esprit.models;

public class typePack {
    int id_typepack;
    String nomTypePack;

    public int getId_typepack() {
        return id_typepack;
    }

    public void setId_typepack(int id_typepack) {
        this.id_typepack = id_typepack;
    }

    public String getNomTypePack() {
        return nomTypePack;
    }

    public void setNomTypePack(String nomTypePack) {
        this.nomTypePack = nomTypePack;
    }

    @Override
    public String toString() {
        return

                 nomTypePack ;
    }
}
