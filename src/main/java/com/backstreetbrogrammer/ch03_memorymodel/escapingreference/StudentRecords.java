package com.backstreetbrogrammer.ch03_memorymodel.escapingreference;

import java.util.HashMap;
import java.util.Map;

public class StudentRecords {

    private final Map<String, Student> records;

    public StudentRecords() {
        this.records = new HashMap<>();
    }

    public void addStudent(final Student student) {
        this.records.put(student.getName(), student);
    }

    public Map<String, Student> getStudents() {
        return this.records;
    }

}
