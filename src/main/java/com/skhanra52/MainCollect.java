package com.skhanra52;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainCollect {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        List<Student> studentList =
                Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
                .limit(500)
                .toList();

        // How many Australian students have enrolled the course?
        Set<Student> australianStudents = studentList.stream()
                .filter(student -> student.getCountryCode().equals("AU"))
                .collect(Collectors.toSet());
        System.out.println("# of Australian Student is: "+australianStudents.size());

        // How many of the students are under 30 years of old?
        Set<Student> underThirty = studentList.stream()
                .filter(student -> student.getAge() < 30)
                .collect(Collectors.toSet());
        System.out.println("# of student under thirties: "+underThirty.size());

        // How many of students are under thirty and australian?
        Set<Student> aussiesUnderThirty = studentList.stream()
                .filter(student -> student.getCountryCode().equals("AU"))
                .filter(student -> student.getAge() < 30)
                .collect(Collectors.toSet());
        System.out.println("# of Australian student under thirties: "+aussiesUnderThirty.size());

        // Or--------------------------------------------------
        Set<Student> youngAussies = new TreeSet<>(Comparator.comparing(Student::getStudentId));
        youngAussies.addAll(australianStudents);
        youngAussies.retainAll(underThirty);
        System.out.println("# of Australian student under thirties: "+youngAussies.size());

        youngAussies.forEach(s -> System.out.print(s.getStudentId() + " " ));
        System.out.println();
        // Here the output is un-ordered as its getting HashSet from the Collectors.toSet()
        // To make it order we have to set up the Collector manually which is given below
        aussiesUnderThirty.forEach(s -> System.out.print(s.getStudentId() + " " ));
        System.out.println();
        // Need to check the below code as it's not compiling
//        Set<Student> aussiesUnderThirtyOrdered = studentList.stream()
//                .filter(student -> student.getCountryCode().equals("AU"))
//                .filter(student -> student.getAge() < 30)
//                .sorted()
//                .collect(TreeSet::new, TreeSet::add, TreeSet:: addAll);
//        aussiesUnderThirtyOrdered.forEach(s -> System.out.print(s.getStudentId() + " " ));
//        System.out.println();

    }
}
