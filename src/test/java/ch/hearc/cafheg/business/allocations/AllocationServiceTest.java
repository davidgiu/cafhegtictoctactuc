package ch.hearc.cafheg.business.allocations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class AllocationServiceTest {

  private AllocationService allocationService;
  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;

  @BeforeEach
  void setUp() {
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    allocationMapper = Mockito.mock(AllocationMapper.class);
    allocationService = new AllocationService(allocataireMapper, allocationMapper);
  }

  // 🚀 TESTS MIS À JOUR POUR COUVRIR LE NOUVEAU SCHÉMA

  @Test
  void getParentDroitAllocation_UnSeulParentTravaille_ShouldReturnParent1() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Bienne",
            true, false, true, false, false, false, false,
            BigDecimal.valueOf(2500), BigDecimal.valueOf(3000)
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_UnSeulParentAvecAutoriteParentale_ShouldReturnParent1() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Bienne",
            false, false, true, false, false, false, false,
            BigDecimal.ZERO, BigDecimal.ZERO
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_ParentsSeparesParentQuiVitAvecEnfant_ShouldReturnParent1() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Bienne",
            true, true, true, true, false, false, false,
            BigDecimal.valueOf(3000), BigDecimal.valueOf(4000)
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_ParentsSeparesUnTravailleDansCanton_ShouldReturnParent2() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Lausanne", "Neuchâtel",
            false, true, true, true, false, false, false,
            BigDecimal.valueOf(3000), BigDecimal.valueOf(4000)
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent2");
  }

  @Test
  void getParentDroitAllocation_ParentsEnsembleSalariesSalairePlusEleve_ShouldReturnParent1() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Neuchâtel",
            true, true, true, true, true, false, false,
            BigDecimal.valueOf(5000), BigDecimal.valueOf(4500)
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent1");
  }

  @Test
  void getParentDroitAllocation_ParentsEnsembleIndependantsRevenuPlusEleve_ShouldReturnParent2() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Neuchâtel",
            false, false, true, true, true, true, true,
            BigDecimal.valueOf(5000), BigDecimal.valueOf(7000)
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent2");
  }

  @Test
  void getParentDroitAllocation_AucunParentNeTravaille_ShouldReturnParent2() {
    ParentDroitAllocationRequest request = new ParentDroitAllocationRequest(
            "Neuchâtel", "Neuchâtel", "Neuchâtel",
            false, false, true, true, true, false, false,
            BigDecimal.ZERO, BigDecimal.ZERO
    );

    String result = allocationService.getParentDroitAllocation(request);
    assertThat(result).isEqualTo("Parent2");
  }
}
