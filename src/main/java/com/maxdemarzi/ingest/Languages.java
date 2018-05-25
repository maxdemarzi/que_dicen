package com.maxdemarzi.ingest;

import org.apache.tika.parser.ner.opennlp.OpenNLPNERecogniser;

import java.util.HashMap;
import java.util.Map;

import static org.apache.tika.parser.ner.NERecogniser.DATE;
import static org.apache.tika.parser.ner.NERecogniser.LOCATION;
import static org.apache.tika.parser.ner.NERecogniser.MONEY;
import static org.apache.tika.parser.ner.NERecogniser.ORGANIZATION;
import static org.apache.tika.parser.ner.NERecogniser.PERCENT;
import static org.apache.tika.parser.ner.NERecogniser.PERSON;
import static org.apache.tika.parser.ner.NERecogniser.TIME;

public class Languages {

    private static final Map<String, String> ENGLISH = new HashMap<String, String>() {{
        put(PERSON, "models/en-ner-person.bin");
        put(LOCATION, "models/en-ner-location.bin");
        put(ORGANIZATION, "models/en-ner-organization.bin");

        put(TIME, "models/en-ner-time.bin");
        put(DATE, "models/en-ner-date.bin");
        put(PERCENT,"models/en-ner-percentage.bin");
        put(MONEY,"models/en-ner-money.bin");
    }};

    static final OpenNLPNERecogniser ENGLISH_NER = new OpenNLPNERecogniser(ENGLISH);

    private static final Map<String, String> SPANISH = new HashMap<String, String>() {{
        put(PERSON, "models/es-ner-person.bin");
        put(LOCATION, "models/es-ner-location.bin");
        put(ORGANIZATION, "models/es-ner-organization.bin");

        put(TIME, "models/en-ner-time.bin");
        put(DATE, "models/en-ner-date.bin");
        put(PERCENT,"models/en-ner-percentage.bin");
        put(MONEY,"models/en-ner-money.bin");
    }};

    static final OpenNLPNERecogniser SPANISH_NER = new OpenNLPNERecogniser(SPANISH);
}
