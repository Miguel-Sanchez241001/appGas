package pe.com.app.appgaspedidos.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;

@Singleton
@DataSourceDefinition(
        name = "java:app/jdbc/perciPOSTGREL",
        className = "org.postgresql.ds.PGSimpleDataSource",
        url = "jdbc:postgresql://localhost:5432/bd_gas",
        user = "bd_gas",
        password = "bd_gas"
)
public class DataSourceConfig {
 }
