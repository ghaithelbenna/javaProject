package tn.esprit.services;
import tn.esprit.utils.MyDataBase;


import tn.esprit.models.guide;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class serviceGuide {
    private Connection cnx;

    public serviceGuide(){
        cnx = MyDataBase.getInstance().getCnx();
    }



    private boolean isAlphabetical(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isLetter);
    }


    public void ajouter(guide guide) throws SQLException {
        String req ="insert into guide (nom_guide, prenom_guide, age_guide, sexe_guide) values(?,?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(req);
        ste.setString(1, guide.getNom_guide());
        ste.setString(2, guide.getPrenom_guide());
        ste.setString(3, guide.getAge_guide());
        ste.setString(4, guide.getSexe_guide());
        ste.executeUpdate();
    }


    public void modifier(guide guide) throws SQLException {
        String req = "update guide set nom_guide=?, prenom_guide=?, age_guide=?, sexe_guide=? where id_guide=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, guide.getNom_guide());
        pre.setString(2, guide.getPrenom_guide());
        pre.setString(3, guide.getAge_guide());
        pre.setString(4, guide.getSexe_guide());
        pre.setInt(5, guide.getId_guide());
        pre.executeUpdate();
    }


    public void supprimer(guide guide) throws SQLException {
        String req = "DELETE FROM guide WHERE id_guide=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, guide.getId_guide());
            pre.executeUpdate();
        }
    }



    public List<guide> afficher() throws SQLException {
        String req = "select * from guide";
        Statement ste = cnx.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<guide> list = new ArrayList<>();
        while (res.next()) {
            guide g = new guide();
            g.setId_guide(res.getInt(1));
            g.setNom_guide(res.getString("nom_guide"));
            g.setPrenom_guide(res.getString("prenom_guide"));
            g.setAge_guide(res.getString("age_guide"));
            g.setSexe_guide(res.getString("sexe_guide"));
            list.add(g);
        }
        return list;
    }



}

