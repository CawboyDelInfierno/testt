# Stage 1: Build amb Maven
# Utilitza una imatge oficial de Maven amb JDK 17 com a base per a la construcció
FROM docker.io/maven:3.9-eclipse-temurin-17 AS builder

# Estableix el directori de treball dins del contenidor
WORKDIR /app

# Copia només el pom.xml per aprofitar la cache de capes de Docker
# Si el pom.xml no canvia, les dependències no es descarregaran de nou
COPY pom.xml .

# Opcional però recomanat: Descarrega les dependències abans de copiar el codi font
# Això accelera les construccions posteriors si només canvia el codi font
RUN mvn dependency:go-offline

# Copia tot el codi font de l'aplicació al contenidor
COPY src ./src

# Construeix l'aplicació i crea el fitxer JAR
# -DskipTests evita executar els tests durant la construcció de la imatge Docker
# (els tests s'executen al pipeline de CI)
RUN mvn package -DskipTests

# Stage 2: Runtime amb JRE
# Utilitza una imatge base més lleugera només amb Java Runtime Environment (JRE)
# Alpine és una distribució Linux petita, ideal per a imatges Docker
FROM docker.io/eclipse-temurin:17-jre-alpine

# Estableix el directori de treball per a l'execució
WORKDIR /app

# Copia el fitxer JAR creat a l'stage 'builder' al directori de treball actual
# Assegura't que el nom del JAR coincideix amb el definit al pom.xml (artifactId-version.jar)
COPY --from=builder /app/target/calculator-0.0.1-SNAPSHOT.jar ./app.jar

# Exposa el port en què l'aplicació Spring Boot escolta per defecte (normalment 8080)
# Això informa Docker que el contenidor escoltarà en aquest port
EXPOSE 8080

# Comanda que s'executarà quan s'iniciï el contenidor
# Executa l'aplicació Spring Boot empaquetada al JAR
CMD ["java", "-jar", "./app.jar"]
