package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class ParentDroitAllocationRequest {

    private String enfantResidence;
    private String parent1Residence;
    private String parent2Residence;
    private boolean parent1ActiviteLucrative;
    private boolean parent2ActiviteLucrative;
    private boolean parentsEnsemble;
    private BigDecimal parent1Salaire;
    private BigDecimal parent2Salaire;

    // ✅ Constructeur sans arguments pour Jackson
    public ParentDroitAllocationRequest() {
        this.parent1Salaire = BigDecimal.ZERO;
        this.parent2Salaire = BigDecimal.ZERO;
    }

    public ParentDroitAllocationRequest(String enfantResidence, String parent1Residence, String parent2Residence,
                                        boolean parent1ActiviteLucrative, boolean parent2ActiviteLucrative,
                                        boolean parentsEnsemble, BigDecimal parent1Salaire, BigDecimal parent2Salaire) {
        this.enfantResidence = enfantResidence;
        this.parent1Residence = parent1Residence;
        this.parent2Residence = parent2Residence;
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
        this.parentsEnsemble = parentsEnsemble;
        this.parent1Salaire = parent1Salaire != null ? parent1Salaire : BigDecimal.ZERO;
        this.parent2Salaire = parent2Salaire != null ? parent2Salaire : BigDecimal.ZERO;
    }

    // Getters et Setters obligatoires pour Jackson
    public String getEnfantResidence() { return enfantResidence; }
    public void setEnfantResidence(String enfantResidence) { this.enfantResidence = enfantResidence; }

    public String getParent1Residence() { return parent1Residence; }
    public void setParent1Residence(String parent1Residence) { this.parent1Residence = parent1Residence; }

    public String getParent2Residence() { return parent2Residence; }
    public void setParent2Residence(String parent2Residence) { this.parent2Residence = parent2Residence; }

    public boolean isParent1ActiviteLucrative() { return parent1ActiviteLucrative; }
    public void setParent1ActiviteLucrative(boolean parent1ActiviteLucrative) { this.parent1ActiviteLucrative = parent1ActiviteLucrative; }

    public boolean isParent2ActiviteLucrative() { return parent2ActiviteLucrative; }
    public void setParent2ActiviteLucrative(boolean parent2ActiviteLucrative) { this.parent2ActiviteLucrative = parent2ActiviteLucrative; }

    public boolean isParentsEnsemble() { return parentsEnsemble; }
    public void setParentsEnsemble(boolean parentsEnsemble) { this.parentsEnsemble = parentsEnsemble; }

    public BigDecimal getParent1Salaire() { return parent1Salaire; }
    public void setParent1Salaire(BigDecimal parent1Salaire) { this.parent1Salaire = parent1Salaire; }

    public BigDecimal getParent2Salaire() { return parent2Salaire; }
    public void setParent2Salaire(BigDecimal parent2Salaire) { this.parent2Salaire = parent2Salaire; }
}
