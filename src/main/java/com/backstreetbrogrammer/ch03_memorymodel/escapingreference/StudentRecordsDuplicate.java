package com.backstreetbrogrammer.ch03_memorymodel.escapingreference;

import java.util.HashMap;
import java.util.Map;

public class StudentRecordsDuplicate {

    private final Map<String, Student> records;

    public StudentRecordsDuplicate() {
        this.records = new HashMap<>();
    }

    public void addStudent(final Student student) {
        this.records.put(student.getName(), student);
    }

    public Map<String, Student> getStudents() {
        // return new HashMap<>(this.records);
        return Map.copyOf(this.records);
    }

}
