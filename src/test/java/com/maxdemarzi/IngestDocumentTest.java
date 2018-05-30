package com.maxdemarzi;

import com.maxdemarzi.schema.Schema;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.test.server.HTTP;

import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

public class IngestDocumentTest {

    @Rule
    public final Neo4jRule neo4j = new Neo4jRule()
            .withProcedure(Procedures.class)
            .withProcedure(Schema.class);

    @Test
    public void testIngest() throws Exception {
        HTTP.POST(neo4j.httpURI().resolve("/db/data/transaction/commit").toString(), SCHEMA);
        HTTP.Response response = HTTP.POST(neo4j.httpURI().resolve("/db/data/transaction/commit").toString(), QUERY);
        assertEquals(5, response.get("results").get(0).get("data").get(0).get("row").get(0).size());
        assertEquals(4, response.get("results").get(0).get("data").get(0).get("row").get(1).size());
    }

    @Test
    public void testSpanishIngest() throws Exception {
        HTTP.POST(neo4j.httpURI().resolve("/db/data/transaction/commit").toString(), SCHEMA);
        HTTP.Response response = HTTP.POST(neo4j.httpURI().resolve("/db/data/transaction/commit").toString(), QUERY2);
        assertEquals(2, response.get("results").get(0).get("data").get(0).get("row").get(0).size());
        assertEquals(1, response.get("results").get(0).get("data").get(0).get("row").get(1).size());
    }

    private static final Map SCHEMA =
            singletonMap("statements",asList(singletonMap("statement",
                    "CALL com.maxdemarzi.schema.generate()")));

    private static final Map QUERY =
            singletonMap("statements",asList(singletonMap("statement",
                    "CALL com.maxdemarzi.en.ingest('data/en_sample.txt')")));

    private static final Map QUERY2 =
            singletonMap("statements",asList(singletonMap("statement",
                    "CALL com.maxdemarzi.es.ingest('data/es_sample.txt')")));

}
