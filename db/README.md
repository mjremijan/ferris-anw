# Ferris ANW DB

This is the database project for ANW. It uses Flyway to build the database.

Flyway scripts located in: `src/main/flyway`

Database created in: `${project.basedir}\\..\\production\\data\\derby-${ferris-derby-version}\\anw.db`

Run flyway: `mvn flyway:migrate`

# References

<https://documentation.red-gate.com/fd/quickstart-maven-184127578.html>

* Tutorial on getting started with Flyway

<https://www.baeldung.com/database-migrations-with-flyway>

* Tutorial on getting started with Flyway

<https://documentation.red-gate.com/flyway/reference/usage/maven-goal>

* flyway-maven-plugin documentation

<https://documentation.red-gate.com/fd/flyway-locations-setting-277579008.html>

* How to change where Flyway looks for SQL files

<https://maven.apache.org/plugins/maven-resources-plugin/examples/resource-directory.html>

* How to add a folder to Maven resources

<https://stackoverflow.com/questions/78096545/no-database-found-to-handle-error-with-flyway-postgresql>

* Error: No database found to handle jdbc:derby:...