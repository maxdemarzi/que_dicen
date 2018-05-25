# que_dicen
POC building subgraphs of documents

This project requires Neo4j 3.4.x

Instructions
------------ 

This project uses maven, to build a jar-file with the procedure in this
project, simply package the project with maven:

    mvn clean package

This will produce a jar-file, `target/que_dicen-1.0-SNAPSHOT.jar`,
that can be copied to the `plugin` directory of your Neo4j instance.

    cp target/que_dicen-1.0-SNAPSHOT.jar neo4j-enterprise-3.4.0/plugins/.


Edit your Neo4j/conf/neo4j.conf file by adding this line:

    dbms.security.procedures.unrestricted=com.maxdemarzi.*    
    
(Re)start Neo4j

Create the schema:

    CALL com.maxdemarzi.schema.generate;

Ingest the document by running this stored procedure for each file:

    CALL com.maxdemarzi.en.ingest(filename);

For Example:
    
    CALL com.maxdemarzi.en.ingest('data/en_sample.txt');

For Spanish Ingestion:

    CALL com.maxdemarzi.es.ingest('data/es_sample.txt');
    
If using Windows you must escape the slashes like so:

    CALL com.maxdemarzi.es.ingest('C:\\Users\\Max\\Downloads\\GitHub\\que_dicen\\data\es_sample.txt');
        