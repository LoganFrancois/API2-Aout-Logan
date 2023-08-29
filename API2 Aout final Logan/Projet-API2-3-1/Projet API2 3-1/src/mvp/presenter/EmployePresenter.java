package mvp.presenter;

import Métier.Employe;
import Métier.Message;
import mvp.model.DAOEmploye;
import mvp.view.EmployeViewInterface;

import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EmployePresenter {



    private DAOEmploye model;
    private EmployeViewInterface view;

  //  private static final Logger logger = LogManager.getLogger(EmployePresenter.class);

    public EmployePresenter(DAOEmploye model,EmployeViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        view.setListDatas(getAll());
    }

    public List<Employe> getAll(){
        return model.readAll();
    }

    public void create(Employe employe){
        Employe emp = model.create(employe);
        if(emp!=null) view.affMsg("Creation de : " +emp);
        else view.affMsg("erreur de création");
    }

    public void delete(Employe employe) {
        boolean ok = model.delete(employe);
        if(ok) view.affMsg("employe effacé");
        else view.affMsg("employe non effacé, des mails encore existants bloquent sa suppression");
    }

    public void update(Employe employe) {

        Employe emp =model.update(employe);
        if(emp==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : " +emp);
    }

    public Employe read(String mail) {
        Employe emp = model.read(mail);
        if(emp==null) view.affMsg("recherche infructueuse");
        else view.affMsg(emp.toString());
        return emp;
    }

    public void mails_envoyes(Employe employe){
        List<Message> lmsg = model.mails_envoyes(employe);
       if(lmsg==null || lmsg.isEmpty()) view.affMsg("aucun message trouvé");
       else view.affList(lmsg);
    }

    public void message_recu(Employe employe){
        List<Message> lmsg = model.mails_recus(employe);
        if(lmsg==null || lmsg.isEmpty()) view.affMsg("aucun message trouvé");
        else view.affList(lmsg);
    }
    public void mail_non_lus(Employe employe){
       List<Message> lmsg = model.mail_non_lus(employe);
       if(lmsg==null || lmsg.isEmpty()) view.affMsg("aucun message trouvé");
       else view.affList(lmsg);
    }





}
