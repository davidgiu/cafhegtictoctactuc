package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class ParentDroitAllocationRequest {

    private String enfantResidence;
    private String parent1Residence;
    private String parent2Residence;
    private boolean parent1ActiviteLucrative;
    private boolean parent2ActiviteLucrative;
    private boolean parent1AutoriteParentale;
    private boolean parent2AutoriteParentale;
    private boolean parentsEnsemble;
    private boolean parent1Independant;
    private boolean parent2Independant;
    private BigDecimal parent1RevenuAVS;
    private BigDecimal parent2RevenuAVS;

    public ParentDroitAllocationRequest() {
        this.parent1RevenuAVS = BigDecimal.ZERO;
        this.parent2RevenuAVS = BigDecimal.ZERO;
    }

    public ParentDroitAllocationRequest(String enfantResidence, String parent1Residence, String parent2Residence,
                                        boolean parent1ActiviteLucrative, boolean parent2ActiviteLucrative,
                                        boolean parent1AutoriteParentale, boolean parent2AutoriteParentale,
                                        boolean parentsEnsemble, boolean parent1Independant, boolean parent2Independant,
                                        BigDecimal parent1RevenuAVS, BigDecimal parent2RevenuAVS) {
        this.enfantResidence = enfantResidence;
        this.parent1Residence = parent1Residence;
        this.parent2Residence = parent2Residence;
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
        this.parent1AutoriteParentale = parent1AutoriteParentale;
        this.parent2AutoriteParentale = parent2AutoriteParentale;
        this.parentsEnsemble = parentsEnsemble;
        this.parent1Independant = parent1Independant;
        this.parent2Independant = parent2Independant;
        this.parent1RevenuAVS = parent1RevenuAVS != null ? parent1RevenuAVS : BigDecimal.ZERO;
        this.parent2RevenuAVS = parent2RevenuAVS != null ? parent2RevenuAVS : BigDecimal.ZERO;
    }

    public String getEnfantResidence() {
        return enfantResidence;
    }

    public void setEnfantResidence(String enfantResidence) {
        this.enfantResidence = enfantResidence;
    }

    public String getParent1Residence() {
        return parent1Residence;
    }

    public void setParent1Residence(String parent1Residence) {
        this.parent1Residence = parent1Residence;
    }

    public String getParent2Residence() {
        return parent2Residence;
    }

    public void setParent2Residence(String parent2Residence) {
        this.parent2Residence = parent2Residence;
    }

    public boolean isParent1ActiviteLucrative() {
        return parent1ActiviteLucrative;
    }

    public void setParent1ActiviteLucrative(boolean parent1ActiviteLucrative) {
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
    }

    public boolean isParent2ActiviteLucrative() {
        return parent2ActiviteLucrative;
    }

    public void setParent2ActiviteLucrative(boolean parent2ActiviteLucrative) {
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
    }

    public boolean isParent1AutoriteParentale() {
        return parent1AutoriteParentale;
    }

    public void setParent1AutoriteParentale(boolean parent1AutoriteParentale) {
        this.parent1AutoriteParentale = parent1AutoriteParentale;
    }

    public boolean isParent2AutoriteParentale() {
        return parent2AutoriteParentale;
    }

    public void setParent2AutoriteParentale(boolean parent2AutoriteParentale) {
        this.parent2AutoriteParentale = parent2AutoriteParentale;
    }

    public boolean isParentsEnsemble() {
        return parentsEnsemble;
    }

    public void setParentsEnsemble(boolean parentsEnsemble) {
        this.parentsEnsemble = parentsEnsemble;
    }

    public boolean isParent1Independant() {
        return parent1Independant;
    }

    public void setParent1Independant(boolean parent1Independant) {
        this.parent1Independant = parent1Independant;
    }

    public boolean isParent2Independant() {
        return parent2Independant;
    }

    public void setParent2Independant(boolean parent2Independant) {
        this.parent2Independant = parent2Independant;
    }

    public BigDecimal getParent1RevenuAVS() {
        return parent1RevenuAVS;
    }

    public void setParent1RevenuAVS(BigDecimal parent1RevenuAVS) {
        this.parent1RevenuAVS = parent1RevenuAVS;
    }

    public BigDecimal getParent2RevenuAVS() {
        return parent2RevenuAVS;
    }

    public void setParent2RevenuAVS(BigDecimal parent2RevenuAVS) {
        this.parent2RevenuAVS = parent2RevenuAVS;
    }
}
