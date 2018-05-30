package com.maxdemarzi;

import com.maxdemarzi.ingest.IngestDocumentCallable;
import com.maxdemarzi.results.GraphResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

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

    @Procedure(name = "com.maxdemarzi.en.ingest", mode = Mode.WRITE)
    @Description("CALL com.maxdemarzi.en.ingest")
    public Stream<GraphResult> IngestEnglishDocument(@Name("file") String file) throws Exception {
        IngestDocumentCallable callable = new IngestDocumentCallable(file, "English", db, log);
        return Stream.of(callable.call());
    }

    @Procedure(name = "com.maxdemarzi.es.ingest", mode = Mode.WRITE)
    @Description("CALL com.maxdemarzi.es.ingest")
    public Stream<GraphResult> IngestSpanishDocument(@Name("file") String file) throws InterruptedException {
        IngestDocumentCallable callable = new IngestDocumentCallable(file, "Spanish", db, log);
        return Stream.of(callable.call());
    }
}
