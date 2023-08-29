package mvp.view;

import Métier.Employe;
import mvp.presenter.BureauPresenter;
import mvp.presenter.EmployePresenter;

import java.util.List;

public interface EmployeViewInterface {



    public void setPresenter(EmployePresenter presenter);

    public void setListDatas(List<Employe> employes);

    public void affMsg(String msg);


    void affList(List infos);
}
