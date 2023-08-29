package mvp.model;

import Métier.Bureau;
import Métier.Employe;
import Métier.Message;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeModelDB implements  DAOEmploye{


    protected Connection dbConnect;

    public EmployeModelDB() {
        dbConnect = DBConnection.getConnection();
    }


    @Override
    public Employe create(Employe emp) {
        String query1 = "insert into EMPLOYE(mail, nom, prenom, idbureau) values (?,?,?,?)";
        String query2 = "select IDEMPLOYE from EMPLOYE where MAIL=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1); PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, emp.getMail());
            pstm1.setString(2, emp.getNom());
            pstm1.setString(3, emp.getPrenom());
            pstm1.setInt(4, emp.getBureau().getIdBureau());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, emp.getMail());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idemp = rs.getInt(1);
                emp.setIdEmploye(idemp);
                return emp;
            } else {
                throw new Exception("aucun employé de trouvé");
            }
        } catch (Exception e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public boolean delete(Employe empRech) {
        String req = "delete from EMPLOYE where idemploye = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, empRech.getIdEmploye());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }


    }

    @Override
    public Employe read(String mail) {
        String req = "select * from EMPLOYE where MAIL=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, mail);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idemploye = rs.getInt("IDEMPLOYE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                int idbureau = rs.getInt("IDBUREAU");
                Bureau idb = new Bureau(idbureau);
                Employe emp = new Employe(idemploye, mail, nom, prenom, idb);
                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Employe update(Employe empRech) {
        String req = "update EMPLOYE set mail=?,nom=?, prenom=? where idemploye=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, empRech.getMail());
            pstm.setString(2, empRech.getNom());
            pstm.setString(3, empRech.getPrenom());
            pstm.setInt(4, empRech.getIdEmploye());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("aucun employé n'a été mis à jour");
            }
            return read(empRech.getMail());
        } catch (Exception e) {
            return null;
        }
    }

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
    public List<Message> mail_non_lus(Employe emp) {

        List<Message> lmess = new ArrayList<>();
        String query = "SELECT * FROM messages_non_lus WHERE IDEMPLOYE = ?";
        String query2 = "UPDATE INFOS SET DATELECTURE =? WHERE IDEMPLOYE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query); PreparedStatement pstm2 = dbConnect.prepareStatement(query2)
        ) {
            pstm.setInt(1, emp.getIdEmploye());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idemploye = rs.getInt("EMETTEUR");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
                Message msg=new Message(objet,contenu,date_envoi.toLocalDate(),lemp);
                lmess.add(msg);
            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages non lus");
                return null;

            } else {
                LocalDate ajd = LocalDate.now();
                pstm2.setInt(2, emp.getIdEmploye());
                pstm2.setDate(1, Date.valueOf(ajd));
                int n = pstm2.executeUpdate();
                if (n == 0) {
                    throw new Exception("Aucun message n'a été mis à jour");
                }
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }
    }

    @Override
    public List<Message> mails_recus(Employe emp) {
        List<Message> lmess = new ArrayList<>();
        String query = "SELECT * FROM messages_lus WHERE IDEMPLOYE = ?";
// VOICI LA VUE EN QUESTION::
        //  CREATE OR REPLACE VIEW messages_lus AS
        //  SELECT e.idemploye, m.objet, m.contenu, m.date_envoi, e2.nom AS nom2, e2.idemploye AS emetteur, e2.prenom AS prenom2, e2.mail
        //  FROM employe e
        //  JOIN infos i ON e.idemploye = i.idemploye
        //  JOIN message m ON i.idmessage = m.idmessage
        //  JOIN employe e2 ON m.idemploye = e2.idemploye
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
        ) {
            pstm.setInt(1, emp.getIdEmploye());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idemploye = rs.getInt("EMETTEUR");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
                lmess.add(new Message(1, objet, contenu, date_envoi.toLocalDate(), lemp));

            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages déjà lus pour cet utilisateur");
                return null;

            } else {
                return lmess;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }
    }




    @Override
    public List<Message> mails_envoyes(Employe emp){
        List<Message> lmess = new ArrayList<>();
        String query = "select * from verif_reponse WHERE IDEMPLOYE=?";

        // SELECT e.idemploye, i.idemploye as Recepteur, i.datelecture, i.idmessage, m.date_envoi ,e2.nom AS nom2, e2.prenom AS prenom2, e2.mail
        //FROM employe e
        //JOIN message m ON e.idemploye = m.idemploye
        //JOIN infos i ON m.idmessage = i.idmessage
        //JOIN employe e2 ON i.idemploye = e2.idemploye;

        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
        ) {
            pstm.setInt(1, emp.getIdEmploye());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idemploye = rs.getInt("RECEPTEUR");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM2");
                String prenom = rs.getString("PRENOM2");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date datelecture = rs.getDate("DATELECTURE");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                Employe lemp = new Employe(idemploye, mail, nom, prenom, null);
                lmess.add(new Message(1, objet, contenu, date_envoi.toLocalDate(), lemp));


                String redText = "\u001B[31m";
                String resetText = "\u001B[0m";

                if (datelecture != null) {
                    System.out.println("\n\nObjet: " + objet + "\n Contenu du message: " + contenu + " \n Message envoyé le: " + date_envoi + "`\n à " + nom + " " + prenom + "\n Il a été lu le " + datelecture );
                } else {
                    System.out.println(redText + "\n\nObjet: " + objet + "\n Contenu du message: " + contenu + " \n Message envoyé le: " + date_envoi + "`\n à " + nom + " " + prenom +  "("+mail+ ")"+"\n Il n'a pas encore été lu" + resetText);
                }
            }
            if (lmess.isEmpty()) {
                System.out.println("Il n'y a pas de messages pour cet utilisateur");
                return null;
            } else {
                return lmess;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }


    }












}
