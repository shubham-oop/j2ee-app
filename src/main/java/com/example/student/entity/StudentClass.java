package com.example.student.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum StudentClass {
    LKG("LKG"),
    UKG("UKG"),
    CLASS_1("1"),
    CLASS_2("2"),
    CLASS_3("3"),
    CLASS_4("4"),
    CLASS_5("5"),
    CLASS_6("6"),
    CLASS_7("7"),
    CLASS_8("8"),
    CLASS_9("9"),
    CLASS_10("10"),
    CLASS_11("11"),
    CLASS_12("12");

    private final String dbValue;

    StudentClass(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static StudentClass fromDbValue(String dbValue) {
        for (StudentClass c : StudentClass.values()) {
            if (c.dbValue.equalsIgnoreCase(dbValue)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown class value: " + dbValue);
    }
}


