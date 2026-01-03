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
        System.out.println("-------------------------------------------");

        Optional<Student> o2 = getStudent(new ArrayList<>(), "first");
        System.out.println("Empty = "+o2.isEmpty()+", Present = "+ o2.isPresent());
        System.out.println("o2 : "+o2);
        System.out.println("---------------------------------------------");

        Optional<Student> o3 = getStudent(students, "first");
        System.out.println("Empty = "+o3.isEmpty()+", Present = "+ o3.isPresent());
        System.out.println("o3 : "+o3);
    }

    private static Optional<Student> getStudent(List<Student> list, String type){

        if(list == null || list.isEmpty()) {
        //    return null; // if you return null it will throw an error "Cannot invoke "java.util.Optional.isEmpty()"
                         // because "o1" is null"
            return Optional.empty();
        } else if(type.equals("first")){
            return Optional.of(list.get(0));
        } else if(type.equals("last")){
            return Optional.of(list.get(list.size() - 1));
        }
        return Optional.of(list.get(new Random().nextInt(list.size())));
    }
}
