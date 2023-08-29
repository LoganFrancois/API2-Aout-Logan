package Métier;

import java.util.ArrayList;

import java.util.List;

/**
 * Classe métier de gestion d'un centre de partage d'email
 *
 * @author Logan Francois
 */

public class Bureau {


    /**
     * identifiant unique du bureau
     */
    private int idBureau;

    /**
     * sigle UNIQUE du bureau
     */
    private String sigle;

    /**
     * tel du bureau
     */
    private String tel;

    /**
     * liste des employés du bureau
     */
    private List<Employe> listeEmploye = new ArrayList<>();

    /**
     * Constructeur par défaut
     */

    public Bureau() {
    }

    public Bureau(String sigle) {
        this.sigle = sigle;
    }

    /**
     * Constructeur paramétré
     *
     * @param idBureau   identifiant unique du bureau affecté par la base de données
     * @param sigle      sigle unique du bureau
     * @param tel        telephone du bureau
     */

    public Bureau(int idBureau, String sigle, String tel) {
        this.idBureau = idBureau;
        this.sigle = sigle;
        this.tel = tel;
    }

    public Bureau(String sigle, String tel) {
        this.sigle = sigle;
        this.tel = tel;
    }

    public Bureau(int idBureau) {
        this.idBureau = idBureau;
    }

    /**
     * getter idBureau
     *
     * @return identifiant du bureau
     */

    public int getIdBureau() {
        return idBureau;
    }

    /**
     * setter idBureau
     *
     * @param idBureau identifiant du bureau
     */


    public void setIdBureau(int idBureau) {
        this.idBureau = idBureau;
    }

    /**
     * getter sigle
     *
     * @return sigle unique du bureau
     */
    public String getSigle() {
        return sigle;
    }

    /**
     * setter sigle
     *
     * @param sigle    sigle unique du bureau
     */

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }



    /**
     * getter tel du bureau
     *
     * @return telephone du bureau
     */
    public String getTel() {
        return tel;
    }

    /**
     * setter tel du bureau
     *
     * @param tel telephone du bureau
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Employe> getListeEmploye() {
        return listeEmploye;
    }

    public void setListeEmploye(List<Employe> listeEmploye) {
        this.listeEmploye = listeEmploye;
    }

    @Override
    public String toString() {
        return "Bureau numéro " +
                idBureau +
                ": sigle=" + sigle +
                ", tel=" + tel
                ;
    }
}
