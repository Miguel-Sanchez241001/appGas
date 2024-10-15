package pe.com.app.appgaspedidos.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Data
@Singleton
public class DataSourceConfig {



    private EntityManagerFactory entityManagerFactory;





    public EntityManagerFactory createEntityManagerFactory() {
        // Valores por defecto (sin variables de entorno)
        String dbHost = "localhost";
        String dbName = "bd_gas";
        String dbUser = "bd_gas";
        String dbPassword = "bd_gas";

        // Intentar obtener las variables de entorno (si están definidas)
        if (System.getenv("DB_HOST") != null) {
            dbHost = System.getenv("DB_HOST");
        }
        if (System.getenv("DB_NAME") != null) {
            dbName = System.getenv("DB_NAME");
        }
        if (System.getenv("DB_USER") != null) {
            dbUser = System.getenv("DB_USER");
        }
        if (System.getenv("DB_PASSWORD") != null) {
            dbPassword = System.getenv("DB_PASSWORD");
        }

        // Crear un mapa para las propiedades de conexión
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://" + dbHost + ":5432/" + dbName);
        properties.put("jakarta.persistence.jdbc.user", dbUser);
        properties.put("jakarta.persistence.jdbc.password", dbPassword);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");

        // Crear EntityManagerFactory con las propiedades dinámicas
        return Persistence.createEntityManagerFactory("perciPOSTGREL", properties);
    }

    @PostConstruct
    public void init() {
        // Llamamos al método createEntityManagerFactory para inicializar la conexión
        entityManagerFactory = new DataSourceConfig().createEntityManagerFactory();
    }

    @PreDestroy
    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
 }
