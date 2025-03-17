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
    if (request.isParent1ActiviteLucrative() && !request.isParent2ActiviteLucrative()) {
      return PARENT_1;
    }

    if (request.isParent2ActiviteLucrative() && !request.isParent1ActiviteLucrative()) {
      return PARENT_2;
    }

    return request.getParent1Salaire().compareTo(request.getParent2Salaire()) > 0 ? PARENT_1 : PARENT_2;
  }

}
