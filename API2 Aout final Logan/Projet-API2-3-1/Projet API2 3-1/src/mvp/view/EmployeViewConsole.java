package mvp.view;

import Métier.Bureau;
import Métier.Employe;
import mvp.model.DAOBureau;
import mvp.presenter.BureauPresenter;
import mvp.presenter.EmployePresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class EmployeViewConsole implements EmployeViewInterface {


    private EmployePresenter presenter;
    private List<Employe> lemp;
    private Scanner sc = new Scanner(System.in);




    public EmployeViewConsole(){

    }


    @Override
    public void setPresenter(EmployePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Employe> employes) {
        this.lemp= employes;
        affList(lemp);
        menu();

    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }


    @Override
    public void affList(List infos) {
        affListe(infos);
    }

    public void menu(){
        do {

            int ch = choixListe(Arrays.asList("Ajout", "suppression", "recherche", "modification", "special", "fin"));
            switch (ch) {
                case 1:
                    create();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    read();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    special();
                    break;
                case 6:
                    return;
            }
        } while (true);
    }

    private void special() {
        affList(lemp);
        System.out.println("ID : ");
        int el = choixElt(lemp)-1;
        Employe employe = lemp.get(el);
        employe = presenter.read(employe.getMail());
        do {
            int ch = choixListe(Arrays.asList("Voir les mails envoyés et s'ils ont été lus", "Voir les mails reçus mais déjà lus", "Voir les mails non lus","menu principal"));

            switch (ch) {
                case 1:
                    presenter.mails_envoyes(employe);
                    break;
                case 2:
                    presenter.message_recu(employe);
                    break;
                case 3:
                    presenter.mail_non_lus(employe);
                    break;
                case 4:
                    return;

            }
        } while (true);
    }

    private void update() {
        affList(lemp);
        int ne =choixElt(lemp)-1;
        Employe employe= lemp.get(ne);
        String mail = modifyIfNotBlank("Mail : ",employe.getMail());
        String nom = modifyIfNotBlank("nom : ",employe.getNom());
        String prenom = modifyIfNotBlank("prenom : ",employe.getPrenom());
        presenter.update(new Employe(employe.getIdEmploye(),mail,nom,prenom));
        lemp =presenter.getAll();
        affList(lemp);
    }

    private void read() {
        System.out.println("mail de l'employe : ");
        String mail = sc.nextLine();
        presenter.read(mail);
        affList(lemp);
    }

    private void delete() {
        affList(lemp);
        int ne = choixElt(lemp)-1;
        Employe employe = lemp.get(ne);
        presenter.delete(employe);
        lemp=presenter.getAll();
        affList(lemp);
    }

    private void create() {

        System.out.println("Mail : ");
        String mail = sc.nextLine();
        System.out.println("Nom : ");
        String nom = sc.nextLine();
        System.out.println("Prénom : ");
        String prenom = sc.nextLine();

        System.out.println("ID du bureau: ");
        int bureau_id = sc.nextInt();
        Employe emp = new Employe(0,mail,nom,prenom,new Bureau(bureau_id));
        presenter.create(emp);

    }
}
