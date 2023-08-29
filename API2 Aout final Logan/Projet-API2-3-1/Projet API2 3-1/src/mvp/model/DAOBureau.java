package mvp.model;

import Métier.Bureau;

import java.util.List;

public interface DAOBureau  {



    Bureau create (Bureau bureau);

    boolean delete (Bureau bureau);

    Bureau read (String sigle);

    Bureau update (Bureau bureau);

    List<Bureau> readAll ();
}
