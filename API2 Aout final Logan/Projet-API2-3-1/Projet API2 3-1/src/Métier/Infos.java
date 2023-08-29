package Métier;

import java.time.LocalDate;

public class Infos {
    private LocalDate dateLecture;
    private Employe recepteur;

    public Infos(LocalDate dateLecture) {
        this.dateLecture = dateLecture;
    }

    public Infos(LocalDate dateLecture, Employe employe) {
        this.dateLecture = dateLecture;
        this.recepteur = employe;
    }

    public Infos() {
    }

    public LocalDate getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDate dateLecture) {
        this.dateLecture = dateLecture;
    }

    public Employe getEmploye() {
        return recepteur;
    }

    public void setEmploye(Employe employe) {
        this.recepteur = employe;
    }


}