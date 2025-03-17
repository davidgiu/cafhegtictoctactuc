package ch.hearc.cafheg.infrastructure.application;

import ch.hearc.cafheg.infrastructure.persistance.Database;
import ch.hearc.cafheg.infrastructure.persistance.Migrations;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ch.hearc.cafheg")
public class Application extends SpringBootServletInitializer {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("ch.hearc.cafheg");

  /**
   * Démarrage de l'application en mode standalone (java -jar ...)
   * @param args Arguments du programme
   */
  public static void main(String[] args) {
    //logger.info("This is an INFO log, should create cafheg.log");
    //logger.error("This is a log of ERROR, should create err.log");
    start();
    SpringApplication.run(Application.class, args);
  }

  private static void start() {
    Database database = new Database();
    Migrations migrations = new Migrations(database);

    database.start();
    migrations.start();
  }

  /**
   * Démarrage de l'application dans un serveur d'applications (Tomcat, Glassfish, Websphere...)
   */
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
    start();
  }
}
