package mvp.model;

import Métier.Bureau;
import Métier.Employe;
import Métier.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeModelHyb extends EmployeModelDB {

    public  EmployeModelHyb() {
        super();
    }

    protected Connection dbConnect;



    @Override
    public List<Employe> readAll() {
        String req = "select * from EMPLOYE order by IDEMPLOYE";
        List<Employe> lemp = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idemploye = rs.getInt("IDEMPLOYE");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int idbureau = rs.getInt("IDBUREAU");
                Bureau idb = new Bureau(idbureau);
                lemp.add(new Employe(idemploye, mail, nom, prenom, idb));
            }
            if (lemp.isEmpty()) {
                return null;
            }
            return lemp;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List <Message> mail_non_lus(Employe employe) {return employe.mail_non_lus();}

@Override
    public List <Message> mails_envoyes(Employe employe) {return  employe.mails_envoyes();}

    @Override
    public List <Message> mails_recus(Employe employe) {return  employe.mails_recus();}
}
