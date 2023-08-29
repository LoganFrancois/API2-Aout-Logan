package mvp;

import mvp.model.*;
import mvp.presenter.BureauPresenter;
import mvp.presenter.EmployePresenter;
import mvp.presenter.MessagePresenter;
import mvp.view.*;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.List;

public class GestProjet {
    public static void main(String[] args) {
        GestProjet gm = new GestProjet();
        gm.gestion();
    }

    private DAOEmploye mde;
    private DAOBureau mdb;
    private DAOMessage mdm;

    private EmployePresenter pre;
    private BureauPresenter prb;
    private MessagePresenter prm;

    private EmployeViewInterface vueEmp;
    private BureauViewInterface vueBur;
    private MessageViewInterface vueMes;


    public void gestion(){
        mde = new EmployeModelDB();
        //dm= new EmployeModelHyb();
        mdb = new BureauModelDB();
        mdm = new MessageModelDB();

        vueEmp = new EmployeViewConsole();
        vueBur = new BureauViewConsole();
        vueMes = new MessageViewConsole();

        pre = new EmployePresenter(mde,vueEmp);
        prb = new BureauPresenter(mdb,vueBur);
        prm = new MessagePresenter(mdm,vueMes);

        prb.setEmployePresenter(pre);

        List<String> loptions = Arrays.asList("Employes","Bureaux","Message","Fin");
        do {
            int ch = Utilitaire.choixListe(loptions);
            switch (ch){
                case 1: pre.start();
                    break;
                case 2 : prb.start();
                    break;
                case 3: prm.start();
                    break;
                case 4: System.exit(0);
            }
        }while(true);
    }

}