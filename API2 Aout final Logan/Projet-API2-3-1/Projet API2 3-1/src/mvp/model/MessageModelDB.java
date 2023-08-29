package mvp.model;

import Métier.Employe;
import Métier.Message;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageModelDB implements  DAOMessage{


    protected Connection dbConnect;

    public MessageModelDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Message create(Message newMsg) {
        String query1 = "insert into MESSAGE (OBJET, CONTENU, DATE_ENVOI, IDEMPLOYE) values (?,?,?,?)";
        String query2 = "select IDMESSAGE from MESSAGE where OBJET=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setString(1, newMsg.getObjet());
            pstm1.setString(2, newMsg.getContenu());
            pstm1.setDate(3, Date.valueOf(newMsg.getDateEnvoi()));
            pstm1.setInt(4, newMsg.getEmetteur().getIdEmploye());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, newMsg.getObjet());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idmsg = rs.getInt(1);
                newMsg.setIdMessage(idmsg);
                return newMsg;
            } else {
                throw new Exception("Aucun message n'a été trouvé");
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Message msgRech) {
        String req = "delete from MESSAGE where IDMESSAGE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, msgRech.getIdMessage());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Message read(int idmessage) {
        String req = "select * from MESSAGE where IDMESSAGE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, idmessage);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                int emetteur = rs.getInt("IDEMPLOYE");
                Message m = new Message(idmessage, objet, contenu, date_envoi.toLocalDate(),new Employe(emetteur));
                return m;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Message update(Message msgRech) {
        String query = "update MESSAGE set OBJET=?, CONTENU=?, IDEMPLOYE=? where IDMESSAGE=? ";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {

            pstm.setString(1, msgRech.getObjet());
            pstm.setString(2, msgRech.getContenu());
            pstm.setInt(3,msgRech.getEmetteur().getIdEmploye());
            pstm.setInt(4, msgRech.getIdMessage());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("Aucun message n'a été mis à jour");
            }
            return read(msgRech.getIdMessage());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Message> readAll() {
        String query = "select * from MESSAGE order by IDMESSAGE";
        List<Message> lmsg = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(query); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idMessage = rs.getInt("IDMESSAGE");
                String objet = rs.getString("OBJET");
                String contenu = rs.getString("CONTENU");
                Date date_envoi = rs.getDate("DATE_ENVOI");
                int emetteur = rs.getInt("IDEMPLOYE");
                lmsg.add(new Message(idMessage, objet, contenu, date_envoi.toLocalDate(),new Employe(emetteur)));
            }
            if (lmsg.isEmpty()) {
                return null;
            }
            return lmsg;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public boolean emission(Employe emp, Message msg) {
        String query = "select * from MESSAGE where IDMESSAGE=?";
        String query2 = "insert into INFOS( IDEMPLOYE, IDMESSAGE) values (?,?)";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {

            pstm.setInt(1, msg.getIdMessage());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                System.out.println("ok");
                pstm2.setInt(1, emp.getIdEmploye());
                pstm2.setInt(2, msg.getIdMessage());
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
