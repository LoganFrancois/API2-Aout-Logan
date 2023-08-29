package mvp.model;

import Métier.Bureau;
import Métier.Employe;
import Métier.Message;

import java.time.LocalDate;
import java.util.List;

public interface DAOEmploye {


    Employe create (Employe employe);

    boolean delete (Employe employe);

    Employe read (String mail);

    Employe update (Employe bureau);

    List<Employe> readAll();




    List<Message> mail_non_lus(Employe emp);

    List <Message> mails_envoyes(Employe emp);

    List<Message> mails_recus(Employe emp);







}
