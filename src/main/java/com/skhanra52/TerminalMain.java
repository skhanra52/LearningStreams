package com.skhanra52;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class TerminalMain {

    public static void main(String[] args) {

        System.out.println("----------------------Terminal Operations----------------------------------------");
        /*
        What is reduction operation?
        A reduction operation is a special type of terminal operation. Stream elements are processed to produce a single
        output. They result can be primitive type, like a long, in the case of count operation.
        They result can be a reference type, like optional  or one of the statical types.
        It can also be any type of choice, such as an Array, a list, or some other type.
         */
        System.out.println("--------Example of terminal operator summaryStatistics---------------------------");
        IntSummaryStatistics result = IntStream
                .iterate(0, i -> i < 1000, i -> i = i+100 )
                .peek(System.out::println)
                .summaryStatistics();

        System.out.println(result);

        IntSummaryStatistics leanYearData = IntStream
                .iterate(2000, i ->  i <2025, i -> i= i+1)
                .filter(i -> i%4 == 0)
                .peek(System.out::println)
                .summaryStatistics();
        System.out.println(leanYearData);


        /*
        Aggregation Terminal operations:  Terminal operation to return information about the aggregated data set.
        -------------------------------   The methods shown below have no arguments, They all return numerical data,
        either directly, or in specialized types to hold the data.
        ---------------------------------------------------------------------------------------------------------------
        Return type             |   Terminal Methods        |           Stream on which methods applies
        ---------------------------------------------------------------------------------------------------------------
        long                    | count()                   | All
        Optional                | max()                     | All
        Optional                | min()                     | All
        OptionalDouble          | average()                 | DoubleStream, InStream, LongStream
        double/int/long         | sum()                     |  DoubleStream, InStream, LongStream
        ----
        DoubleSummaryStatistics |                           | DoubleStream
        IntSummaryStatistics    | summaryStatistics()       | IntStream
        LongSummaryStatistics   |                           | LongStream


        -------------------------------------------------------------------------------------------------------------
        Matching elements in a stream based on condition:
        ------------------------------------------------
           There are three terminal operations that let you get an overall sense of what your stream elements contains,
       based on some specified condition. These all returns a boolean, and takes predicates as an argument.
       -> We can think of these as ways to ask true or false questions about the data set, the stream as a whole.
        ---------------------------------------------------------------------------------------------------------------
        Return type    |   Method                                        |          Description
        ---------------------------------------------------------------------------------------------------------------
        boolean        | allMatch(Predicate<? super T> predicate)        |-> Returns true if all stream elements meet
                       |                                                 | the condition specified.
        boolean        | anyMatch(Predicate<? super T> predicate)        |-> Returns true if there is at least one match
                       |                                                 | meet to the condition specified.
        boolean        | noneMatch(Predicate<? super T> predicate)       |-> The operation returns true if no match.


         */
    }
}
