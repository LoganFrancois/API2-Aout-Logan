package mvp.presenter;

import Métier.Employe;
import Métier.Infos;
import Métier.Message;
import mvp.model.DAOMessage;
import mvp.view.MessageViewInterface;

import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MessagePresenter {


    private DAOMessage model;
    private MessageViewInterface view;
    private Infos info = new Infos();

    public MessagePresenter(DAOMessage model, MessageViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setMessage(this);
    }



    public void start() {
        view.setListDatas(getAll());
    }

    public List<Message> getAll(){
        return model.readAll();
    }

    public void create(Message message) {
        Message msg = model.create(message);
        if(msg != null && info.getEmploye() != null && msg.getEmetteur() != null
                && msg.getEmetteur().getIdEmploye() != info.getEmploye().getIdEmploye()) view.affMsg("création de :"+msg);
        else view.affMsg("erreur de création");
    }

    public void delete(Message msg) {
        boolean ok = model.delete(msg);
        if(ok) view.affMsg("message effacé");
        else view.affMsg("message non effacé");
    }




    public void update(Message message) {
        Message msg =model.update(message);
        if(msg==null) view.affMsg("mise à jour infructueuse");
        else view.affMsg("mise à jour effectuée : "+msg);
    }

    public Message read(int id_message) {
        Message msg = model.read(id_message);
        if(msg==null) view.affMsg("recherche infructueuse");
        else view.affMsg(msg.toString());
        return msg;
    }


}
