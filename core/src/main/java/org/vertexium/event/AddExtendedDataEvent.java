package org.vertexium.event;

import org.vertexium.Element;
import org.vertexium.Graph;
import org.vertexium.Visibility;

import java.util.Objects;

public class AddExtendedDataEvent extends GraphEvent {
    private final Element element;
    private final String tableName;
    private final String row;
    private final String columnName;
    private final String key;
    private final Object value;
    private final Visibility visibility;

    public AddExtendedDataEvent(
            Graph graph,
            Element element,
            String tableName,
            String row,
            String columnName,
            String key,
            Object value,
            Visibility visibility
    ) {
        super(graph);
        this.element = element;
        this.tableName = tableName;
        this.row = row;
        this.columnName = columnName;
        this.key = key;
        this.value = value;
        this.visibility = visibility;
    }

    public Element getElement() {
        return element;
    }

    public String getTableName() {
        return tableName;
    }

    public String getRow() {
        return row;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public String toString() {
        return "AddExtendedDataEvent{" +
                "element=" + element +
                ", tableName='" + tableName + '\'' +
                ", row='" + row + '\'' +
                ", columnName='" + columnName + '\'' +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", visibility=" + visibility +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddExtendedDataEvent that = (AddExtendedDataEvent) o;
        return Objects.equals(element, that.element) &&
                Objects.equals(tableName, that.tableName) &&
                Objects.equals(row, that.row) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(key, that.key) &&
                Objects.equals(value, that.value) &&
                Objects.equals(visibility, that.visibility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, tableName, row, columnName, key, value, visibility);
    }
}
