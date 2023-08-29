package mvp.presenter;

import Métier.Bureau;
import mvp.model.DAOBureau;
import mvp.view.BureauViewInterface;

import java.util.List;

public class BureauPresenter {

    private DAOBureau model;
    private BureauViewInterface view;

    //private static final Logger logger = LogManager.getLogger(BureauPresenter.class);
    private EmployePresenter employepresenter;

    public BureauPresenter(DAOBureau model,BureauViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() {
        view.setListDatas(getAll());
    }

    public List<Bureau> getAll(){
        return model.readAll();
    }

    public void create(Bureau bureau){
        Bureau br = model.create(bureau);
        if(br!=null) view.affMsg("création de :" +br);
        else view.affMsg("erreur de création");
    }

    public void delete(Bureau br){
        boolean ok = model.delete(br);
        if(ok) view.affMsg("bureau effacé");
        else view.affMsg("bureau non effacé");
    }


    public void update(Bureau bureau) {
        Bureau br =model.update(bureau);
        if(br==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : " +br);
    }

    public Bureau read(String sigle) {
        Bureau bur = model.read(sigle);
        if(bur==null) view.affMsg("recherche infructueuse");
        else view.affMsg(bur.toString());
        return bur;
    }

    public void setEmployePresenter(EmployePresenter employePresenter) {this.employepresenter = employePresenter;}
}
