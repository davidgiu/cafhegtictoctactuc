package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.allocations.*;
import ch.hearc.cafheg.business.versements.*;
import ch.hearc.cafheg.infrastructure.persistance.*;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import org.springframework.stereotype.Service;
import java.util.List;

public class AllocataireService {

    private final AllocataireMapper allocataireMapper;
    private final VersementMapper versementMapper;

    public AllocataireService(AllocataireMapper allocataireMapper, VersementMapper versementMapper) {
        this.allocataireMapper = allocataireMapper;
        this.versementMapper = versementMapper;
    }

    public void supprimerAllocataire(Long allocataireId) {
        // Vérifier si l'allocataire existe
        Allocataire allocataire = allocataireMapper.findById(allocataireId);
        if (allocataire == null) {
            throw new RuntimeException("Allocataire non trouvé avec l'ID : " + allocataireId);
        }

        // Vérifier s'il existe des versements associés
        int countVersements = versementMapper.countVersementsByAllocataire(allocataireId);
        if (countVersements > 0) {
            throw new RuntimeException("Impossible de supprimer l'allocataire car il possède des versements.");
        }

        // Supprimer l'allocataire
        allocataireMapper.deleteById(allocataireId);
        System.out.println("Allocataire supprimé avec succès.");
    }

    public Allocataire modifierAllocataire(Long allocataireId, String nouveauNom, String nouveauPrenom) {
        // Vérifier si l'allocataire existe
        Allocataire allocataire = allocataireMapper.findById(allocataireId);
        if (allocataire == null) {
            throw new RuntimeException("Allocataire non trouvé avec l'ID : " + allocataireId);
        }

        // Vérifier si une modification est nécessaire
        boolean isModified = false;

        if (nouveauNom != null && !nouveauNom.equals(allocataire.getNom())) {
            allocataire.setNom(nouveauNom);
            isModified = true;
        }

        if (nouveauPrenom != null && !nouveauPrenom.equals(allocataire.getPrenom())) {
            allocataire.setPrenom(nouveauPrenom);
            isModified = true;
        }

        // Si aucune modification n'a été apportée, lever une exception
        if (!isModified) {
            throw new RuntimeException("Aucune modification détectée. Le nom et le prénom sont identiques.");
        }

        System.out.println("Mise à jour de l'allocataire avec ID : " + allocataireId +
                " Nouveau nom : " + nouveauNom + " Nouveau prénom : " + nouveauPrenom + "No AVS " + allocataire.getNoAVS());
        // Mettre à jour l'allocataire dans la base de données
        allocataireMapper.updateAllocataire(allocataire);

        return allocataire;
    }
}


