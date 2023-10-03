package com.backstreetbrogrammer.ch03_memorymodel.escapingreference;

import java.util.Map;

public class StudentRecordsImmutable {

    private final Map<String, Student> records = Map.of();

    public void addStudent(final Student student) {
        this.records.put(student.getName(), student);
    }

    public Map<String, Student> getStudents() {
        return this.records;
    }

}
