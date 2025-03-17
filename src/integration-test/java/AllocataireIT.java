import static org.assertj.core.api.Assertions.assertThat;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AllocataireIT {

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"; // Exemple avec H2
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private IDatabaseTester databaseTester;

    @BeforeEach
    void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester("org.h2.Driver", DB_URL, DB_USER, DB_PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("allocataire-dataset.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Test
    void testSuppressionAllocataire() throws Exception {
        // Suppression de l'allocataire avec ID 1
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM allocataire WHERE id = ?");
            stmt.setInt(1, 1);
            stmt.executeUpdate();
        }

        // Vérification que l'allocataire a bien été supprimé
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM allocataire WHERE id = ?");
            stmt.setInt(1, 1);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            assertThat(count).isEqualTo(0); // Vérifie que l'enregistrement a bien été supprimé
        }
    }

    @Test
    void testModificationAllocataire() throws Exception {
        // Modification de l'allocataire avec ID 2
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE allocataire SET email = ? WHERE id = ?");
            stmt.setString(1, "nouveau.email@example.com");
            stmt.setInt(2, 2);
            stmt.executeUpdate();
        }

        // Vérification de la modification
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT email FROM allocataire WHERE id = ?");
            stmt.setInt(1, 2);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String email = rs.getString("email");

            assertThat(email).isEqualTo("nouveau.email@example.com"); // Vérifie que l'email a été modifié
        }
    }
}
