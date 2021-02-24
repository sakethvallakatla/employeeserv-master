package com.paypal.bfs.conditions.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import org.jsonschema2pojo.AbstractAnnotator;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Adds @NonNull annotation to required fields as jsonchema2pojo only adds comment.
 */
public class RequiredAnnotator extends AbstractAnnotator {
    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        super.propertyField(field, clazz, propertyName, propertyNode);
        if (Objects.nonNull(propertyNode.findValue("notNull"))) {
            field.annotate(NotNull.class);
        }
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        schema.get("properties").fieldNames().forEachRemaining(a -> System.out.println("field: " + a));
        if (schema.get("properties").has("notNull")) {
            ((ObjectNode) schema.get("properties")).remove("notNull");
        }
    }
}
