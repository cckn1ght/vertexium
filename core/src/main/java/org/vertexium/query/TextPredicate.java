package org.vertexium.query;

import org.vertexium.Property;
import org.vertexium.PropertyDefinition;
import org.vertexium.TextIndexHint;
import org.vertexium.VertexiumException;
import org.vertexium.property.StreamingPropertyValue;
import org.vertexium.type.GeoPoint;
import org.vertexium.util.IterableUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public enum TextPredicate implements Predicate {
    CONTAINS, DOES_NOT_CONTAIN;

    @Override
    public boolean evaluate(final Iterable<Property> properties, final Object second, Collection<PropertyDefinition> propertyDefinitions) {
        if (IterableUtils.count(properties) == 0 && this == DOES_NOT_CONTAIN) {
            return true;
        }
        for (Property property : properties) {
            PropertyDefinition propertyDefinition = PropertyDefinition.findPropertyDefinition(propertyDefinitions, property.getName());
            if (evaluate(property.getValue(), second, propertyDefinition)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean evaluate(Object first, Object second, PropertyDefinition propertyDefinition) {
        if (!canEvaulate(first) || !canEvaulate(second)) {
            throw new VertexiumException("Text predicates are only valid for string or GeoPoint fields");
        }

        String firstString = valueToString(first);
        String secondString = valueToString(second);

        switch (this) {
            case CONTAINS:
                if (propertyDefinition != null && !propertyDefinition.getTextIndexHints().contains(TextIndexHint.FULL_TEXT)) {
                    return false;
                }
                return firstString.contains(secondString);
            case DOES_NOT_CONTAIN:
                if (propertyDefinition != null && !propertyDefinition.getTextIndexHints().contains(TextIndexHint.FULL_TEXT)) {
                    return true;
                }
                String[] tokenizedString = firstString.split("\\W+");
                return !Arrays.asList(tokenizedString).contains(secondString);
            default:
                throw new IllegalArgumentException("Invalid text predicate: " + this);
        }
    }

    @Override
    public void validate(PropertyDefinition propertyDefinition) {
        Set<TextIndexHint> textIndexHints = propertyDefinition.getTextIndexHints();
        if (textIndexHints == null || !textIndexHints.contains(TextIndexHint.FULL_TEXT)) {
            throw new VertexiumException("Check your TextIndexHint settings. Property " + propertyDefinition.getPropertyName() + " is not full text indexed.");
        }
    }


    private String valueToString(Object val) {
        if (val instanceof GeoPoint) {
            val = ((GeoPoint) val).getDescription();
        } else if (val instanceof StreamingPropertyValue) {
            val = ((StreamingPropertyValue) val).readToString();
        }

        return ((String) val).toLowerCase();
    }

    private boolean canEvaulate(Object first) {
        if (first instanceof String) {
            return true;
        }
        if (first instanceof GeoPoint) {
            return true;
        }
        if (first instanceof StreamingPropertyValue && ((StreamingPropertyValue) first).getValueType() == String.class) {
            return true;
        }
        return false;
    }
}
