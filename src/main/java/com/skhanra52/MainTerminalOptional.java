package com.skhanra52;


/*
The Terminal Operations that returns optional.
----------------------------------------------------------------------------------------------------------------
    Return Type                     Terminal Operation
----------------------------------------------------------------------------------------------------------------
OptionalDouble          |   average() - available for primitive streams (IntStream, DoubleStream, LongStream)
Optional<T>             |   findAny()
Optional<T>             |   findFirst()
Optional<T>             |   max(Comparator<? super T> comparator)
Optional<T>             |   min(Comparator<? super T> comparator)
Optional<T>             |   reduce(BinaryOperator<T> accumulator)
 */


import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MainTerminalOptional {

    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        List<Student> studentList =
                Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
                        .limit(500)
                        .toList();

        int minAge = 21;
        studentList.stream()
                .filter((s) -> s.getAge() <= minAge)
                .findAny()
                .ifPresentOrElse((s) -> System.out.printf("Student %d from %s is %d %n",
                        s.getStudentId(), s.getCountryCode(), s.getAge()),
                        () -> System.out.println("Didn't found anyone under "+minAge));

        studentList.stream()
                .filter((s) -> s.getAge() <= minAge)
                .findFirst()        // using findFirst()
                .ifPresentOrElse((s) -> System.out.printf("Student %d from %s is %d %n",
                                s.getStudentId(), s.getCountryCode(), s.getAge()),
                        () -> System.out.println("Didn't found anyone under "+minAge));

//        studentList.stream()
//                .filter((s) -> s.getAge() <= minAge)
//                .sorted(Comparator.comparing(Student::getAge))
//                .findFirst()        // using findFirst()
//                .ifPresentOrElse((s) -> System.out.printf("Student %d from %s is %d %n",
//                                s.getStudentId(), s.getCountryCode(), s.getAge()),
//                        () -> System.out.println("Didn't find anyone under "+minAge));
        // Or we can use the min()

        studentList.stream()
                .filter((s) -> s.getAge() <= minAge)
                .min(Comparator.comparing(Student::getAge))        // using findFirst()
                .ifPresentOrElse((s) -> System.out.printf("Student %d from %s is %d %n",
                                s.getStudentId(), s.getCountryCode(), s.getAge()),
                        () -> System.out.println("Didn't find anyone under "+minAge));

        studentList.stream()
                .filter((s) -> s.getAge() <= minAge)
                .max(Comparator.comparing(Student::getAge))        // using findFirst()
                .ifPresentOrElse((s) -> System.out.printf("Student %d from %s is %d %n",
                                s.getStudentId(), s.getCountryCode(), s.getAge()),
                        () -> System.out.println("Didn't find anyone under "+minAge));

        studentList.stream()
                .filter((s) -> s.getAge() <= minAge)
                .mapToInt(Student::getAge)
                .average()
                .ifPresentOrElse(a -> System.out.printf("Average age under 21: %.2f%n", a),
                        () -> System.out.println("Didn't find anyone under " +minAge));
    }
}
