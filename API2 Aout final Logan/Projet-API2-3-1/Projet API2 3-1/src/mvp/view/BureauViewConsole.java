package mvp.view;

import Métier.Bureau;
import mvp.presenter.BureauPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class BureauViewConsole implements BureauViewInterface {

    public BureauPresenter presenter;
    private List<Bureau> lbr;
    private Scanner sc = new Scanner(System.in);

    public BureauViewConsole(){}

    @Override
    public void setPresenter(BureauPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Bureau> bureaux){
        this.lbr=bureaux;
        affList(lbr);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" +msg);
    }

    @Override
    public void affList(List infos) {
        affListe(infos);
    }
    public void menu(){
        do{
            int ch = choixListe(Arrays.asList("ajout", "retrait", "rechercher", "modifier", "fin"));

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
        affList(lbr);
        int nbr = choixElt(lbr);
        Bureau br = lbr.get(nbr-1);
        String sigle = modifyIfNotBlank("Sigle du bureau ",br.getSigle());
        String tel = modifyIfNotBlank("Numero de telephone ",br.getTel());
        presenter.update(new Bureau(br.getIdBureau(),sigle,tel));
        lbr=presenter.getAll();
        affList(lbr);
    }

    private void read() {
        System.out.println("identifiant du bureau: ");
        String sigle = sc.nextLine();
        presenter.read(sigle);
        affList(lbr);
    }

    private void delete() {
        affList(lbr);
        int nbr = choixElt(lbr);
        Bureau br = lbr.get(nbr-1);
        presenter.delete(br);
        lbr=presenter.getAll();
        affListe(lbr);
    }

    private void create() {
        System.out.println("Sigle : ");
        String sigle = sc.nextLine();
        System.out.println("Numero de telephone : ");
        String tel = sc.nextLine();
        presenter.create(new Bureau(0,sigle,tel));
        lbr=presenter.getAll();
        affList(lbr);
    }









}
