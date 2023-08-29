package mvp.view;

import Métier.Bureau;
import mvp.presenter.BureauPresenter;

import java.util.List;

public interface BureauViewInterface {




    public void setPresenter(BureauPresenter presenter);
    public void setListDatas(List<Bureau> bureaux);
    public void affMsg(String msg);

    public void affList(List infos);


}
