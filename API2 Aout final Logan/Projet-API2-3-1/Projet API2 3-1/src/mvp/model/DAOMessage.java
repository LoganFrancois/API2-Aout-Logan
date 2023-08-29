package mvp.model;

import Métier.Employe;
import Métier.Message;

import java.util.List;

public interface DAOMessage {

    Message create (Message message);

    boolean delete (Message message);

    Message read (int idmessage);

    Message update (Message message);

    List<Message> readAll();

    boolean emission(Employe emp, Message msg);
}
