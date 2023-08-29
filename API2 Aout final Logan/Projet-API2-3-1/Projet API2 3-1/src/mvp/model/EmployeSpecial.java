package mvp.model;

import Métier.Employe;
import Métier.Message;

import java.util.List;

public interface EmployeSpecial {


    List<Message> mail_non_lus(Employe emp);

    List <Message> mails_envoyes(Employe emp);

    List <Message> mails_recus (Employe emp);
}
