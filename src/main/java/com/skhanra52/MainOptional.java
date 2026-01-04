package com.skhanra52;

/*
What's the optional Type?
----------------------------------------------------------------------------------------------------------------
    Optional is the generic class, whose purpose is to be a container for a value which may or may not be null.
It was created by Java's engineers to address the nullPointerException, which is one of the most common errors
in java. This type is primarily intended for use as a method return, under specific condition.

No Result is valid vs. No Result is problem:
----------------------------------------------------------------------------------------------------------------
Optional tried to solve the problem when no result, or no data, is perfectly valid situation, vs. when no result
might really be an error.

We can think of many situation where data make sense.
-> Not everyone has middle initial in their name, or even a last name for that matter.
-> Similarly, No data for a birthdate may or may not be an exception.
-> Likewise, new inventory may not have a price tag.

Optional is a way of indicating that a value may not be present and can therefore be safely ignored
during processing.

Creating an instance of Optional:
------------------------------------
Optional is just another generic class, we decide like any other type, with type arguments. But we don't
construct the optional.
Instead, we use one of the static factory methods as shown below:
----------------------------------------------------------------------------------------------------------------
Factory Methods                  |  When to Use                           | Best Practice Notes
----------------------------------------------------------------------------------------------------------------
Optional<T> empty()              | Use this method to create an optional  | Never return null from a method that
                                 | that you know have no value.           | has optional as return type.
----------------------------------------------------------------------------------------------------------------
Optional<T> of(T value)          | Use this method to create an optional  | Passing null to this method raise a
                                 | that you know has value.               | NullPointerException. Use ofNullable
                                 |                                        | instead, if a possible value might
                                 |                                        | be null.
----------------------------------------------------------------------------------------------------------------
Optional<T> ofNullable(T value)  | Use this method to create an optional  |
                                 | when you are uncertain if the value is |
                                 | null or not.
----------------------------------------------------------------------------------------------------------------
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainOptional {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        List<Student> students =
                Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
                        .limit(500)
                        .collect(Collectors.toList());

        Optional<Student> o1 = getStudent(null, "first");
        System.out.println("Empty = "+o1.isEmpty()+", Present = "+ o1.isPresent());
        System.out.println("o1 : "+o1);
//        System.out.println(o1.get()); // produce .NoSuchElementException: No value present
        System.out.println("-------------o2------------------------------");

        Optional<Student> o2 = getStudent(new ArrayList<>(), "first");
        System.out.println("Empty = "+o2.isEmpty()+", Present = "+ o2.isPresent());
        System.out.println("o2 : "+o2);
//        System.out.println(o2.get()); // produce .NoSuchElementException: No value present
        o2.ifPresentOrElse(System.out::println, () -> System.out.println("----> Empty"));
        System.out.println("---------------o3------------------------------");

        Optional<Student> o3 = getStudent(students, "first");
        System.out.println("Empty = "+o3.isEmpty()+", Present = "+ o3.isPresent());
        System.out.println("o3 : "+o3);
//        System.out.println(o3.get()); // compiler warning: 'Optional.get()' without 'isPresent()' check
        o3.ifPresent(System.out::println);
        // another version of the code with optional type and ternary operator.

        // Student firstStudent = o2.isPresent() ? o2.get() : null;
        // or use functional style expression
        Student firstStudent = o3.orElse(null);
        long id = (firstStudent == null) ? -1 : firstStudent.getStudentId();
        System.out.println("First student's id is: "+id);

        // Let say instead of returning null we would like to return some dummy value if o2 is not present
        // Even though I have o3 value the getDummyStudent(jmc) getting called as I have passed it as argument
        // to "orElse(getDummyStudent(jmc))" , to avoid this we can use "orElseGet(Supplier<? extends student> supplier)"
//        Student firstStudentElseDummy = o3.orElse(getDummyStudent(jmc));
        Student firstStudentElseDummy = o3.orElseGet(() -> getDummyStudent(jmc));
        long firstStudId = firstStudentElseDummy.getStudentId();
        System.out.println("First student's id or dummy Student id is: "+id);

        /*
        Optional class, this class has methods, that seem to mirror some of the stream pipeline operations.
        set up a little code given below.
         */

        List<String> countries = students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .toList();

        Optional.of(countries)
                .map(list -> String.join(",", list))
                .filter((item) -> item.contains("FR"))
                .ifPresentOrElse(System.out::println, () -> System.out.println("FR is missing"));

        /*
        The downside of optional:
        ----------------------------------------
        -> Wrapping element in option consumes more memory and has the possibility of showing down execution.
        -> Wrapping elements in option adds complexity and reduce readability of the code.
        -> Optional is not serializable.
        -> Using option for the fields or method parameters are not recommended.
         */

    }

    private static Optional<Student> getStudent(List<Student> list, String type){

        if(list == null || list.isEmpty()) {
        //    return null; // if you return null it will throw an error "can not invoke" java.util.Optional.isEmpty()"
                         // because "o1" is null"
            return Optional.empty();
        } else if(type.equals("first")){
            return Optional.ofNullable(list.get(0));
        } else if(type.equals("last")){
            return Optional.ofNullable(list.get(list.size() - 1));
        }
        return Optional.ofNullable(list.get(new Random().nextInt(list.size())));
    }

    private static Student getDummyStudent(Course ...course){
        System.out.println("Getting dummy student");
        return new Student("No", 1, 1, "No", false, course);
    }
}
