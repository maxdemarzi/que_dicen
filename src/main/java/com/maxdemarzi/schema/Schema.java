package com.maxdemarzi.schema;

import com.maxdemarzi.results.StringResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Procedure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.apache.tika.parser.ner.NERecogniser.DATE;
import static org.apache.tika.parser.ner.NERecogniser.LOCATION;
import static org.apache.tika.parser.ner.NERecogniser.MONEY;
import static org.apache.tika.parser.ner.NERecogniser.ORGANIZATION;
import static org.apache.tika.parser.ner.NERecogniser.PERCENT;
import static org.apache.tika.parser.ner.NERecogniser.PERSON;
import static org.apache.tika.parser.ner.NERecogniser.TIME;

public class Schema {

    @Context
    public GraphDatabaseService db;

    public static final Map<String, Label> LABELS = new HashMap<String, Label>() {{
        put(PERSON, Label.label("Person"));
        put(LOCATION, Label.label("Location"));
        put(ORGANIZATION, Label.label("Organization"));
        put(TIME, Label.label("Time"));
        put(DATE, Label.label("Date"));
        put(PERCENT,Label.label("Percentage"));
        put(MONEY,Label.label("Money"));
    }};


    @Procedure(name="com.maxdemarzi.schema.generate",mode= Mode.SCHEMA)
    @Description("CALL com.maxdemarzi.schema.generate() - generate schema")

    public Stream<StringResult> generate() throws IOException {

        org.neo4j.graphdb.schema.Schema schema = db.schema();
        if (!schema.getConstraints(Labels.Document).iterator().hasNext()) {
            schema.constraintFor(Labels.Document)
                    .assertPropertyIsUnique("id")
                    .create();
        }

        if (!schema.getConstraints(Labels.Person).iterator().hasNext()) {
            schema.constraintFor(Labels.Person)
                    .assertPropertyIsUnique("id")
                    .create();
        }

        if (!schema.getConstraints(Labels.Location).iterator().hasNext()) {
            schema.constraintFor(Labels.Location)
                    .assertPropertyIsUnique("id")
                    .create();
        }
        if (!schema.getConstraints(Labels.Organization).iterator().hasNext()) {
            schema.constraintFor(Labels.Organization)
                    .assertPropertyIsUnique("id")
                    .create();
        }
        if (!schema.getConstraints(Labels.Time).iterator().hasNext()) {
            schema.constraintFor(Labels.Time)
                    .assertPropertyIsUnique("id")
                    .create();
        }
        if (!schema.getConstraints(Labels.Date).iterator().hasNext()) {
            schema.constraintFor(Labels.Date)
                    .assertPropertyIsUnique("id")
                    .create();
        }
        if (!schema.getConstraints(Labels.Percentage).iterator().hasNext()) {
            schema.constraintFor(Labels.Percentage)
                    .assertPropertyIsUnique("id")
                    .create();
        }
        if (!schema.getConstraints(Labels.Money).iterator().hasNext()) {
            schema.constraintFor(Labels.Money)
                    .assertPropertyIsUnique("id")
                    .create();
        }

        return Stream.of(new StringResult("Schema Generated"));
    }

}
