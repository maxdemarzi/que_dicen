package com.maxdemarzi.ingest;

import com.maxdemarzi.schema.Labels;
import com.maxdemarzi.schema.RelationshipTypes;
import com.maxdemarzi.schema.Schema;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.logging.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.maxdemarzi.ingest.Languages.ENGLISH_NER;
import static com.maxdemarzi.ingest.Languages.SPANISH_NER;

public class IngestDocumentRunnable implements Runnable {

    private String file;
    private String language;
    private GraphDatabaseService db;
    private Log log;

    public IngestDocumentRunnable(String file, String language, GraphDatabaseService db, Log log) {
        this.file = file;
        this.language = language;
        this.db = db;
        this.log = log;
    }

    @Override
    public void run()  {
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        String text = "";
        try (InputStream stream = new FileInputStream(new File(file))) {
            parser.parse(stream, handler, metadata);
            text = handler.toString();
        } catch (Exception e) {
         log.error(e.getMessage());
        }

        Map<String, Set<String>> recognized;
        switch (language) {
            case "English":
                recognized = ENGLISH_NER.recognise(text);
                break;
            case "Spanish":
                recognized = SPANISH_NER.recognise(text);
                break;
            default:
                recognized = new HashMap<>();
        }

        try(Transaction tx = db.beginTx() ) {
            Node document = db.createNode(Labels.Document);
            document.setProperty("text", text);

            // Connect the document to each entity found in each group of entities
            for (Map.Entry<String, Set<String>> entry : recognized.entrySet()) {
                Label label = Schema.LABELS.get(entry.getKey());
                for (String value : entry.getValue()) {
                    Node entity = db.findNode(label, "id", value);
                    if (entity == null) {
                        entity = db.createNode(label);
                        entity.setProperty("id", value);
                    }
                    document.createRelationshipTo(entity, RelationshipTypes.HAS);
                }
            }
            tx.success();
        }

    }

}
