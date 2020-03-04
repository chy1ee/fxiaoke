package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import java.util.ArrayList;
import java.util.List;

public class QueryInfoFilter {
    private String field_name;
    private List<String> field_values;
    private String operator;

    public QueryInfoFilter() {

    }

    public QueryInfoFilter(String fieldName, String fieldValue) {
        this(fieldName, fieldValue, "EQ");
    }

    public QueryInfoFilter(String fieldName, String fieldValue, String operator) {
        List<String> fieldValues = new ArrayList<>();
        fieldValues.add(fieldValue);
        this.field_name = fieldName;
        this.field_values = fieldValues;
        this.operator = operator;
    }

    public QueryInfoFilter(String fieldName, List<String> fieldValues, String operator) {
        this.field_name = fieldName;
        this.field_values = fieldValues;
        this.operator = operator;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public List<String> getField_values() {
        return field_values;
    }

    public void setField_values(List<String> field_values) {
        this.field_values = field_values;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "{" +
                "'field_name':'" + field_name + "'" +
                ", 'operator':'" + operator + "'" +
                ", 'field_values':[" + field_values + "]" +
                '}';
    }
}
