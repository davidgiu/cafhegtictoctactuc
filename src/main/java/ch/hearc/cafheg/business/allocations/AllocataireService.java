package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.allocations.*;
import ch.hearc.cafheg.business.versements.*;
import ch.hearc.cafheg.infrastructure.persistance.*;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

public class AllocataireService {


    private static final Logger logger = LoggerFactory.getLogger(AllocataireMapper.class);
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
            logger.error("Allocataire non trouvé avec l'ID : {}", allocataireId);
            throw new RuntimeException("Allocataire non trouvé avec l'ID : " + allocataireId);
        }

        // Vérifier s'il existe des versements associés
        int countVersements = versementMapper.countVersementsByAllocataire(allocataireId);
        if (countVersements > 0) {
            logger.error("Impossible de supprimer l'allocataire car il possède des versements.");
            throw new RuntimeException("Impossible de supprimer l'allocataire car il possède des versements.");
        }

        // Supprimer l'allocataire
        allocataireMapper.deleteById(allocataireId);
        logger.info("Allocataire supprimé avec succès.");
    }

    public Allocataire modifierAllocataire(Long allocataireId, String nouveauNom, String nouveauPrenom) {
        // Vérifier si l'allocataire existe
        Allocataire allocataire = allocataireMapper.findById(allocataireId);
        if (allocataire == null) {
            logger.error("Allocataire non trouvé avec l'ID : {}", allocataireId);
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
            logger.error("Aucune modification détectée. Le nom et le prénom sont identiques.");
            throw new RuntimeException("Aucune modification détectée. Le nom et le prénom sont identiques.");
        }

        logger.info("Mise à jour de l'allocataire avec ID : {} Nouveau nom : {} Nouveau prénom : {}", allocataireId, nouveauNom, nouveauPrenom);
        // Mettre à jour l'allocataire dans la base de données
        allocataireMapper.updateAllocataire(allocataire);

        return allocataire;
    }
}


