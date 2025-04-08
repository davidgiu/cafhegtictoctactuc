package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AllocationService {

  private static final Logger logger = LoggerFactory.getLogger(AllocationService.class);

  private static final String PARENT_1 = "Parent1";
  private static final String PARENT_2 = "Parent2";

  private final AllocataireMapper allocataireMapper;
  private final AllocationMapper allocationMapper;

  public AllocationService(
      AllocataireMapper allocataireMapper,
      AllocationMapper allocationMapper) {
    this.allocataireMapper = allocataireMapper;
    this.allocationMapper = allocationMapper;
  }

  public List<Allocataire> findAllAllocataires(String likeNom) {
    logger.info("Rechercher tous les allocataires");
    return allocataireMapper.findAll(likeNom);
  }

  public List<Allocation> findAllocationsActuelles() {
    return allocationMapper.findAll();
  }

  public String getParentDroitAllocation(ParentDroitAllocationRequest request) {
    logger.info("Déterminer quel parent a le droit aux allocations");

    // Cas (a) - Un seul parent avec activité lucrative
    if (request.isParent1ActiviteLucrative() && !request.isParent2ActiviteLucrative()) {
      return PARENT_1;
    }
    if (request.isParent2ActiviteLucrative() && !request.isParent1ActiviteLucrative()) {
      return PARENT_2;
    }

    // Cas (b) - Un seul parent avec autorité parentale
    if (request.isParent1AutoriteParentale() && !request.isParent2AutoriteParentale()) {
      return PARENT_1;
    }
    if (request.isParent2AutoriteParentale() && !request.isParent1AutoriteParentale()) {
      return PARENT_2;
    }

    // Cas (c) - Les parents sont séparés, l'enfant vit avec un parent
    if (!request.isParentsEnsemble()) {
      return request.getParent1Residence().equals(request.getEnfantResidence()) ? PARENT_1 : PARENT_2;
    }

    // Cas (d) - Un parent travaille dans le canton de domicile de l'enfant
    if (!request.isParentsEnsemble()) {
      if (request.getParent1Residence().equals(request.getEnfantResidence()) && request.isParent1ActiviteLucrative()) {
        return PARENT_1;
      }
      if (request.getParent2Residence().equals(request.getEnfantResidence()) && request.isParent2ActiviteLucrative()) {
        return PARENT_2;
      }
    }

    // Cas (e) - Les deux parents sont salariés
    if (!request.isParent1Independant() && !request.isParent2Independant()) {
      return request.getParent1RevenuAVS().compareTo(request.getParent2RevenuAVS()) > 0 ? PARENT_1 : PARENT_2;
    }

    // Cas (f) - Les deux parents sont indépendants
    return request.getParent1RevenuAVS().compareTo(request.getParent2RevenuAVS()) > 0 ? PARENT_1 : PARENT_2;
  }

}
