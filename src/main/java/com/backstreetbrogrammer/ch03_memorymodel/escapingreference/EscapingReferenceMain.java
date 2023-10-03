package com.backstreetbrogrammer.ch03_memorymodel.escapingreference;


import java.util.Iterator;
import java.util.Map;

public class EscapingReferenceMain {

    public static void main1(final String[] args) {
        final StudentRecords studentRecords = new StudentRecords();

        studentRecords.addStudent(new Student("John", 18));
        studentRecords.addStudent(new Student("Mary", 17));

        for (final Student student : studentRecords.getStudents().values()) {
            System.out.println(student);
        }

        /*final Map<String, Student> students = studentRecords.getStudents();
        students.clear();*/
    }

    public static void main2(final String[] args) {
        final StudentRecordsIterable studentRecords = new StudentRecordsIterable();

        studentRecords.addStudent(new Student("John", 18));
        studentRecords.addStudent(new Student("Mary", 17));

        final Iterator<Student> studentIterator = studentRecords.iterator();
        studentIterator.next();
        studentIterator.remove();

        for (final Student student : studentRecords) {
            System.out.println(student);
        }
    }

    public static void main(final String[] args) {
        final StudentRecordsImmutable studentRecords = new StudentRecordsImmutable();

        studentRecords.addStudent(new Student("John", 18));
        studentRecords.addStudent(new Student("Mary", 17));

        for (final Student student : studentRecords.getStudents().values()) {
            System.out.println(student);
        }

        /*final Map<String, Student> students = studentRecords.getStudents();
        students.clear();

        for (final Student student : studentRecords.getStudents().values()) {
            System.out.println(student);
        }*/
    }

}
