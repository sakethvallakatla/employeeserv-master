package com.paypal.bfs.persistence.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import org.jsonschema2pojo.AbstractAnnotator;

import javax.persistence.*;

/**
 * Adds @Entity
 */
public class PersistenceAnnotator extends AbstractAnnotator {
    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        super.propertyField(field, clazz, propertyName, propertyNode);

        if (field.name().endsWith("id")) {
            field.annotate(Id.class);
            field.annotate(GeneratedValue.class).param("strategy", GenerationType.AUTO);
        }

        if (field.name().equalsIgnoreCase("address")) {
            field.annotate(OneToOne.class).param("cascade", CascadeType.ALL);
        }

    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        if (schema.get("properties").has("persistence")) {
            clazz.annotate(Entity.class);
            clazz.annotate(Table.class);
            ((ObjectNode) schema.get("properties")).remove("persistence");
        }
    }
}
