package Métier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {


    private int idMessage;
    private String objet;
    private String contenu;
    private LocalDate dateEnvoi;
    private Employe emetteur;
    private List<Infos> listeInfos = new ArrayList<>();

    public Message(int idMessage, String objet, String contenu) {
        this.idMessage = idMessage;
        this.objet = objet;
        this.contenu = contenu;
    }

    public Message(int idMessage, String objet, String contenu, LocalDate dateEnvoi) {
        this.idMessage = idMessage;
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
    }

    public Message(String objet, String contenu, LocalDate dateEnvoi) {
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
    }

    public Message(int idMessage, String objet, String contenu, LocalDate dateEnvoi, Employe emetteur, List<Infos> listeInfos) {
        this.idMessage = idMessage;
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.emetteur = emetteur;
        this.listeInfos = listeInfos;
    }

    public Message(int idMessage, String objet, String contenu, LocalDate dateEnvoi, Employe emetteur) {
        this.idMessage = idMessage;
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.emetteur = emetteur;
    }


    public Message(String objet, String contenu, LocalDate dateEnvoi, Employe emetteur) {
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.emetteur = emetteur;
    }

    public Message(int idMessage,String objet, String contenu,Employe emetteur) {
        this.idMessage=idMessage;
        this.objet=objet;
        this.contenu=contenu;
        this.emetteur=emetteur;
    }

    public Message(String objet, String contenu, Employe emetteur) {
        this.objet = objet;
        this.contenu = contenu;
        this.emetteur = emetteur;
    }

    public Message(String objet, String contenu) {
        this.objet = objet;
        this.contenu = contenu;
    }

    public Message(String objet) {
        this.objet = objet;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Employe getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Employe emetteur) {
        this.emetteur = emetteur;
    }

    public List<Infos> getListeInfos() {
        return listeInfos;
    }

    public void setListeInfos(List<Infos> listeInfos) {
        this.listeInfos = listeInfos;
    }

    @Override
    public String toString() {
        return
                "\n\n --- objet: --- \n" + objet + '\'' +
                        "\n--- contenu:  ---\n'" + contenu + '\'' +
                        " \nenvoyé le :" + dateEnvoi +
                        " \n: " + emetteur;
    }




}
