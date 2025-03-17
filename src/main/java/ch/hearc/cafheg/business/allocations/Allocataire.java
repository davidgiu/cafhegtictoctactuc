package ch.hearc.cafheg.business.allocations;

public class Allocataire {

  private  NoAVS noAVS;
  private  String nom;
  private  String prenom;

  public Allocataire(NoAVS noAVS, String nom, String prenom) {
    this.noAVS = noAVS;
    this.nom = nom;
    this.prenom = prenom;
  }

  public Allocataire() {
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public NoAVS getNoAVS() {
    return noAVS;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }
}
