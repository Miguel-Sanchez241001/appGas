# Dockerfile
FROM quay.io/wildfly/wildfly:28.0.0.Final-jdk17

# Copiar el archivo WAR a la carpeta de despliegue de WildFly
COPY appGasPedidos.war /opt/jboss/wildfly/standalone/deployments/

# Exponer el puerto 8080
EXPOSE 8080

# Arrancar el servidor WildFly en modo standalone
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
