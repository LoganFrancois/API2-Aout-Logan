package mvp.view;

import Métier.Employe;
import Métier.Message;
import mvp.presenter.MessagePresenter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;


public class MessageViewConsole implements MessageViewInterface {


    private MessagePresenter presenter;
    private List<Message> lmsg;

    private Scanner sc= new Scanner(System.in);

    public MessageViewConsole(){

    }
    @Override
    public void setMessage(MessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Message> messages) {

        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:"+msg);
    }

    public void menu(){
        do{
            int ch = choixListe(Arrays.asList("Envoyer un message", "Suppression", "Rechercher un message ","modification", "fin"));

            switch(ch){
                case 1: create();
                    break;
                case 2 : delete();
                    break;
                case 3: read();
                    break;
                case 4 : update();
                    break;
                case 5 : return;
            }
        }while(true);
    }

    private void update() {
        affListe(lmsg);
        int nl = choixElt(lmsg);
        Message msg = lmsg.get(nl-1);
        String objet = modifyIfNotBlank("objet : ",msg.getObjet());
        String contenu = modifyIfNotBlank("contenu : ",msg.getContenu());
        int emetteur = Integer.parseInt(modifyIfNotBlank("emetteur : ", String.valueOf(msg.getEmetteur().getIdEmploye())));
        presenter.update(new Message (objet,contenu,new Employe(emetteur)));
        lmsg=presenter.getAll();
        affListe(lmsg);
    }

    private void read() {
        System.out.println("identifiant du message : ");
        int idmessage= sc.nextInt();
        presenter.read(idmessage);
    }

    private void delete() {
        affListe(lmsg);
        int nl = choixElt(lmsg);
        Message msg =lmsg.get(nl-1);
        presenter.delete(msg);
        lmsg=presenter.getAll();
        affListe(lmsg);
    }



    private void create() {
        System.out.println("Objet : ");
        String objet = sc.nextLine();
        System.out.println("Contenu : ");
        String contenu = sc.nextLine();
        LocalDate date_envoi = LocalDate.now();
        System.out.println("Identifiant de l'emetteur : ");
        int emmeteur = sc.nextInt();
        presenter.create(new Message(0,objet,contenu,date_envoi,new Employe(emmeteur)));
        lmsg=presenter.getAll();
        affListe(lmsg);

    }



}
