package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.NoAVS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.mapstruct.Mapper
public class AllocataireMapper extends Mapper {

  private static final Logger logger = LoggerFactory.getLogger(AllocataireMapper.class);

  private static final String QUERY_FIND_ALL = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES";
  private static final String QUERY_FIND_WHERE_NOM_LIKE = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES WHERE NOM LIKE ?";
  private static final String QUERY_FIND_WHERE_NUMERO = "SELECT NO_AVS, NOM, PRENOM FROM ALLOCATAIRES WHERE NUMERO=?";

  public List<Allocataire> findAll(String likeNom) {
    logger.info("findAll() {}", likeNom);
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement;
      if (likeNom == null) {
        logger.debug("SQL: {}", QUERY_FIND_ALL);
        preparedStatement = connection
            .prepareStatement(QUERY_FIND_ALL);
      } else {

        logger.debug("SQL: {} {}", QUERY_FIND_WHERE_NOM_LIKE, likeNom);
        preparedStatement = connection
            .prepareStatement(QUERY_FIND_WHERE_NOM_LIKE);
        preparedStatement.setString(1, likeNom + "%");
      }
      logger.debug("Allocation d'un nouveau tableau");
      List<Allocataire> allocataires = new ArrayList<>();

      logger.debug("Exécution de la requête");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        logger.debug("Allocataire mapping");
        while (resultSet.next()) {
          logger.trace("Resultset#next");
          allocataires
              .add(new Allocataire(new NoAVS(resultSet.getString(3)), resultSet.getString(2),
                  resultSet.getString(1)));
        }
      }
      logger.info("Allocataires trouvés {}", allocataires.size());
      return allocataires;
    } catch (SQLException e) {
      logger.error("Erreur lors de la recherche des allocataires", e);
      throw new RuntimeException(e);
    }
  }

  public Allocataire findById(long id) {
    logger.info("findById() {}", id);
    Connection connection = activeJDBCConnection();
    try {
      logger.debug("SQL: {}", QUERY_FIND_WHERE_NUMERO);
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_WHERE_NUMERO);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      logger.trace("ResultSet#next");
      resultSet.next();
      logger.debug("Allocataire mapping");
      return new Allocataire(new NoAVS(resultSet.getString(1)),
          resultSet.getString(2), resultSet.getString(3));
    } catch (SQLException e) {
      logger.error("Erreur lors de la recherche de l'allocataire par son id {}", id + e.getMessage());
      throw new RuntimeException(e);
    }
  }
  public void deleteById(long allocataireId) {
    logger.info("deleteById() {}", allocataireId);
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
              "DELETE FROM ALLOCATAIRES WHERE NUMERO = ?"
      );
      preparedStatement.setLong(1, allocataireId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      logger.error("Erreur lors de la suppression de l'allocataire par son id {}", allocataireId + e.getMessage());
      throw new RuntimeException(e);
    }
  }
  public void updateAllocataire(Allocataire allocataire) {
    logger.info("updateAllocataire() {}", allocataire.getNoAVS());
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
              "UPDATE ALLOCATAIRES SET NOM = ?, PRENOM = ? WHERE NO_AVS = ?"
      );
      preparedStatement.setString(1, allocataire.getNom());
      preparedStatement.setString(2, allocataire.getPrenom());
      preparedStatement.setString(3, allocataire.getNoAVS().getValue());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
        logger.error("Erreur lors de la mise à jour de l'allocataire {}", allocataire.getNoAVS() + e.getMessage());
      throw new RuntimeException(e);
    }
  }

}
