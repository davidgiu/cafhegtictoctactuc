package ch.hearc.cafheg;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.AllocataireService;
import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ch.hearc.cafheg.infrastructure.persistance.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllocataireIT {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private IDatabaseTester databaseTester;
    private AllocataireService allocataireService;

    @BeforeAll
    void createSchema() throws Exception {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            RunScript.execute(
                    connection,
                    new InputStreamReader(
                            getClass().getClassLoader().getResourceAsStream("db/ddl/V1__ddl.sql"),
                            StandardCharsets.UTF_8
                    )
            );
        }

        databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
    }

    @BeforeEach
    void importDataSet() throws Exception {
        InputStream xml = getClass().getClassLoader().getResourceAsStream("allocataire_dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(xml);

        DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);

        AllocataireMapper allocataireMapper = new AllocataireMapper() {
            @Override
            protected Connection activeJDBCConnection() {
                try {
                    return databaseTester.getConnection().getConnection();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        VersementMapper versementMapper = new VersementMapper() {
            @Override
            protected Connection activeJDBCConnection() {
                try {
                    return databaseTester.getConnection().getConnection();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public List<VersementParentEnfant> findVersementParentEnfant() {
                return new ArrayList<>();
            }
        };
        allocataireService = new AllocataireService(allocataireMapper, versementMapper);
    }


    @Test
    void testSuppressionAllocataire() throws Exception {
        allocataireService.supprimerAllocataire(2L);


        try (Connection c = databaseTester.getConnection().getConnection()) {
            ResultSet rs = c.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM ALLOCATAIRES WHERE NUMERO=2");
            rs.next();
            assertThat(rs.getInt(1)).isEqualTo(0);
        }
    }

    @Test
    void testModificationAllocataire() throws Exception {
        allocataireService.modifierAllocataire(1L, "Delafuentes", "Julien");


        try (Connection c = databaseTester.getConnection().getConnection()) {
            ResultSet rs = c.createStatement()
                    .executeQuery("SELECT NOM, PRENOM FROM ALLOCATAIRES WHERE NUMERO=1");
            rs.next();
            assertThat(rs.getString("NOM")).isEqualTo("Delafuentes");
            assertThat(rs.getString("PRENOM")).isEqualTo("Julien");
        }
    }

}
