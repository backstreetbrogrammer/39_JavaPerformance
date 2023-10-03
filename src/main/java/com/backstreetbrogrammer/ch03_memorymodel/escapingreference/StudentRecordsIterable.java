package com.backstreetbrogrammer.ch03_memorymodel.escapingreference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StudentRecordsIterable implements Iterable<Student> {

    private final Map<String, Student> records;

    public StudentRecordsIterable() {
        this.records = new HashMap<>();
    }

    public void addStudent(final Student student) {
        this.records.put(student.getName(), student);
    }

    @Override
    public Iterator<Student> iterator() {
        return records.values().iterator();
    }
}
