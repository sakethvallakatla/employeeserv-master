package com.paypal.bfs.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.paypal.bfs.conditions.annotator.RequiredAnnotator;
import com.paypal.bfs.persistence.annotator.PersistenceAnnotator;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import org.jsonschema2pojo.AbstractAnnotator;

/**
 * Applies the list of annotators required.
 */
public class AnnotationExecutor extends AbstractAnnotator {
    RequiredAnnotator requiredAnnotator = new RequiredAnnotator();
    PersistenceAnnotator persistenceAnnotator = new PersistenceAnnotator();

    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        requiredAnnotator.propertyField(field, clazz, propertyName, propertyNode);
        persistenceAnnotator.propertyField(field, clazz, propertyName, propertyNode);
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        requiredAnnotator.propertyInclusion(clazz, schema);
        persistenceAnnotator.propertyInclusion(clazz, schema);
    }
}
