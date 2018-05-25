package com.maxdemarzi;

import com.maxdemarzi.ingest.IngestDocumentRunnable;
import com.maxdemarzi.results.StringResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Procedures {

    // This field declares that we need a GraphDatabaseService
    // as context when any procedure in this class is invoked
    @Context
    public GraphDatabaseService db;

    // This gives us a log instance that outputs messages to the
    // standard log, normally found under `data/log/console.log`
    @Context
    public Log log;


    /*
    Q1: Most common skills across Industry grouped by location
    MATCH (location:Location)<-[:IN_LOCATION]-(candidate)-[:HAS_SKILL]->(skill),
          (candidate)-[:HAS_SUB_INDUSTRY]->(sub)-[:IN_INDUSTRY]->(industry)
    RETURN location.city, location.state,  industry.name, skill.name, COUNT(*)
    ORDER BY industry.name, location.state, location.city, COUNT(*) DESC;
     */

    @Procedure(name = "com.maxdemarzi.en.ingest", mode = Mode.WRITE)
    @Description("CALL com.maxdemarzi.en.ingest")
    public Stream<StringResult> IngestEnglishDocument(@Name("file") String file) throws InterruptedException {
        long start = System.nanoTime();

        Thread t1 = new Thread(new IngestDocumentRunnable(file, "English", db, log));
        t1.start();
        t1.join();

        return Stream.of(new StringResult("Document ingested in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds"));
    }

    @Procedure(name = "com.maxdemarzi.es.ingest", mode = Mode.WRITE)
    @Description("CALL com.maxdemarzi.es.ingest")
    public Stream<StringResult> IngestSpanishDocument(@Name("file") String file) throws InterruptedException {
        long start = System.nanoTime();

        Thread t1 = new Thread(new IngestDocumentRunnable(file, "Spanish", db, log));
        t1.start();
        t1.join();

        return Stream.of(new StringResult("Spanish Document ingested in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start) + " seconds"));

    }
}
