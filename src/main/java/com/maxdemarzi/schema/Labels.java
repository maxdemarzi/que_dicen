package com.maxdemarzi.schema;

import org.neo4j.graphdb.Label;

public enum Labels implements Label {
    Document,
    Person,
    Location,
    Organization,
    Time,
    Date,
    Percentage,
    Money
}