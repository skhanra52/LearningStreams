package com.skhanra52;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        /* Each ball is identified with either a B,I,N,G,O and followed by the number.
        A "B" ball will have a number from 1 to 15 and "I" ball will get a number between 16 through 30 and so on.
        Someone will draw a ball out of the container, and announce it.
        Every player will have five-by-five card, with some randomly generated set of these number on it.
        The first column has "B" number and second column have "I" number and so on.
        You win by being the first person to match five called number in a row, either horizontally,vertically,
        or diagonally.
         */

        List<String> bingoPool = new ArrayList<>(75);
        int start = 1;

        for(char c : "BINGO".toCharArray()){
            for(int i = start; i<(start + 15); i++){
                bingoPool.add(""+c+i);
//                System.out.println(""+c+i);
            }
            start +=15;
        }
        Collections.shuffle(bingoPool);
        for(int i=0;i<15;i++){
            System.out.print(bingoPool.get(i)+", ");
        }
        List<String> copyForStream = new ArrayList<>(bingoPool);
        System.out.println();
        System.out.println("---------------------------------------");

        List<String> firstOnes = bingoPool.subList(0,15);
        firstOnes.sort(Comparator.naturalOrder());
        firstOnes.replaceAll(s -> {
            if ((s.indexOf('G') == 0) || (s.indexOf('O') == 0)) {
                String updated = s.charAt(0) + "-" + s.substring(1);
                System.out.print(updated + " ");
                return updated;
            }
            return s;
        });
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println(firstOnes);
        /* The string manipulation has been done to the firstOnes which is a subList(0,15) of bingoPool. However, the
        same changes will be reflected in the original list, because subList refer to the same location as original list.
        subList just return the view not the new list, so use subList when the original list has to be modified.
         */
        System.out.println("---------Modified original List---------------------");
        for(int i=0;i<15;i++){
            System.out.print(bingoPool.get(i)+", ");
        }
        System.out.println();
        System.out.println("---------------------------------------");

        /* In case you do not want to modify the original List then we need to make a copy and apply the modification
        logic(i.e - replaceAll() here)
         */
        List<String> copyFirstOnes = new ArrayList<>(firstOnes);
        copyFirstOnes.sort(Comparator.naturalOrder());
        copyFirstOnes.replaceAll(s -> {
            if((s.charAt(0) == 'B') || (s.charAt(0) == 'N')){
                String updated = s.charAt(0)+ "--"+s.substring(1);
                System.out.print(updated + " ");
                return updated;
            }
            return s;
        });
        System.out.println();
        System.out.println("---------Unmodified original List----------------------------------");
        for(int i=0;i<15;i++){
            System.out.print(bingoPool.get(i)+", ");
        }
        System.out.println();
        System.out.println("---------Stream-----------------------------------------------------");
//        System.out.println(copyForStream);
//        List<String> result = copyForStream.stream()                      // start of the stream
//                .limit(15)                                                // Stream operation
//                .filter(s -> (s.charAt(0) == 'B' || s.charAt(0) == 'I'))
//                .map(s -> s.charAt(0) + "--" + s.substring(1))            // together all operation forms stream pipeline
//                .collect(Collectors.toList());                            // terminal operation
        // OR ------------------------------
//        List<String> result =
//                copyForStream.stream()
//                        .limit(15)
//                        .filter(s -> s.startsWith("B") || s.startsWith("I"))
//                        .map(s -> s.charAt(0) + "--" + s.substring(1))
//                        .sorted()
//                        .toList();
//
//        System.out.println(result);

        /*
        Streams are Lazy: ------------------------
            -> Meaning, you can start the stream. However, all the stream operation which are formed pipeline will
               not execute until the terminal operation called.
            -> So we can also store the pipeline in a variable and execute it whenever required.
            -> Streams are single-use, once we consume a stream, it does not return anything.
               It gives "IllegalStateException"
       Terminal operations:------------------------
            -> End the stream pipeline
            -> Produce a result or side effect
            -> Trigger execution
            Examples :  forEach()
                        collect()
                        toList()
                        --------
                        count()
                        max(), min(), average(), sum()
                        --------
                        findFirst()
                        allMatch()
                        anyMatch()
                        noneMatch()
                        summaryStatistics()

         */

        // Storing the pipeline as Stream<String> and executing it whenever required.
        // Stream<String> lazyResult = copyForStream.stream()
        //        .limit(15)
        //        .filter(s -> s.startsWith("B") || s.startsWith("I"))
        //        .map(s -> s.charAt(0)+"--"+s.substring(1))
        //        .sorted();

        // lazyResult.forEach(System.out::println);
        // lazyResult.count(); // IllegalStateException:  stream has already been operated upon or closed

        /*
        Correct way to avoid the "IllegalStateException" is given below:
         */

         List<String> lazyResult = copyForStream.stream()
                .limit(15)
                .filter(s -> s.startsWith("B") || s.startsWith("I"))
                .map(s -> s.charAt(0)+"--"+s.substring(1))
                .sorted()
                 .collect(Collectors.toList()); // creates mutable list

         // create a new stream each time.
        lazyResult.forEach(System.out::println);

        long count = lazyResult.size();
        System.out.println("Count: "+count);

        /* How can we create a stream directly?

         */

        String[] strings = new String[]{"One", "Two","Three"};
        // var firstStream = Arrays.stream(new String[] {"One", "Two","Three"});
        Stream<String> firstStream = Arrays.stream(strings)
                .sorted(Comparator.reverseOrder());
        System.out.println("---------------------------------");
//        firstStream.forEach(System.out::println);
        System.out.println("----------------------------------");
        Stream<String> secondStream = Stream.of("five","six","seven")
                .map(String::toUpperCase);
//        secondStream.forEach(System.out::println);
        System.out.println("---------Using Stream concatenation-----------------");
        Stream.concat(secondStream,firstStream)
                .map(s -> s.charAt(0)+" -> "+s)
                .forEach(System.out::println);
        /*
        Map is not extends the collection interface and the map and its implemented classes are categories of their
        own. The map interface does not have a stream method either, but you can use any of the maps collection views
        like keySet, entrySet, or values to generate a stream to process parts of the map.
         */

        Map<Character, int[]> mymap = new LinkedHashMap<>(); // want to keep the insertion order.
        int bingoIndex = 1;
        for(char c : "BINGO".toCharArray()){
            int[] numbers = new int[15];
            int labelNo = bingoIndex;
            Arrays.setAll(numbers, i -> i+ labelNo);
            System.out.println(Arrays.toString(numbers));
            mymap.put(c, numbers);
            bingoIndex+=15;
        }
        System.out.println(mymap.toString());

        mymap.entrySet()
                .stream()
                .map(e -> e.getKey() + " -> has range: "+ e.getValue()[0] +
                        " - "+e.getValue()[e.getValue().length -1])
                .forEach(System.out::println);

//       String[] bingo = new String[15];
//        Arrays.setAll(bingo, i -> {
//            char letter = "BINGO".charAt(i / 3);
//            System.out.println("Letter -> "+letter+" and i -> "+i+" "+" and i/3 -> "+i / 3 + " and -> i%15+1  -> "+ (i%15+1));
//            return letter + "-" + (i % 15 + 1);
//        });
//        System.out.println(Arrays.toString(bingo));

        /*-----------Static method Stream.generator() in Stream------------------------------------
        Stream has static method called generate(). This method takes a supplier (note: Supplier's functional method,
        returns a value, but does not take any arguments).

        Stream.generate(Supplier<? extends T> s)
         */
        Random random = new Random();
        Stream.generate(() -> random.nextInt(2))
                .limit(10)
                .forEach((s) -> System.out.print(s+" "));

        /*-----------Static method Stream.iterate() in Stream------------------------------------
        Stream has static method called iterate(). This method takes two arguments, first is the starting value (called
        seed) and second is the unary operator, which is a special kind of function.

         */
        System.out.println();
        // infinite Stream and limit by 20
        IntStream.iterate(1, n -> n+1)
                .filter(Main::isPrime)
                .limit(20)
                .forEach((s) -> System.out.print(s+" "));

        // Infinite Stream and limit by 100
        System.out.println();
        IntStream.iterate(1, n -> n+1)
                .limit(100)
                .filter(Main::isPrime)
                .forEach((s) -> System.out.print(s+" "));

        // Finite Stream, here the first param is seed, second param is predicate functional interface type.
        // Third param is the unary operator.
        System.out.println();
        IntStream.iterate(1, (n) -> n <= 100, n -> n+1)
//                .limit(100)
                .filter(Main::isPrime)
                .forEach((s) -> System.out.print(s+" "));

        // range(start inclusive, end exclusive)
        System.out.println();
        IntStream.range(1, 100)
                .filter(Main::isPrime)
                .forEach((s) -> System.out.print(s+" "));

        // rangeClosed(start inclusive, end inclusive)
        System.out.println();
        IntStream.rangeClosed(1, 100)
                .filter(Main::isPrime)
                .forEach((s) -> System.out.print(s+" "));
        System.out.println();
        /*
        Supplier, Predicate, Function, Consumer are part of Java’s functional interfaces (introduced in Java 8) and
        are core to lambdas, streams, and modern Java.

        Methods available to create Stream, we called those as Stream Source.
          -> Collection.stream()
          -> Arrays.stream(T[])
          -> Stream.of(T...)
          -> Stream.iterate(T seed, UnaryOperator<T> f)
          -> Stream.iterate(T seed, Predicate<? Super T> p, UnaryOperator<T> f)
          -> Stream.generate(Supplier<? extends T> s)
          -> IntStream.range(int startInclusive, int endExclusive);
          -> IntStream.rangeClosed(int startInclusive, int startInclusive);
          -> LongStream.range(int startInclusive, int endExclusive);
          -> LongStream.rangeClosed(int startInclusive, int startInclusive)

          The most common intermediate operations:-------------------------------

          1. Set of Operation that changes the number of elements in the Stream:
             -> distinct():     Removes duplicate values from the stream and return Stream<T>
             ------------
             -> filter(Predicate<? super T> predicate)    | These methods allow you to reduce the elements in the output
             -> takeWhile(Predicate<? super T> predicate) | Stream, elements that matches the predicate(condition) will
             -> dropWhile(Predicate<? super T> predicate) | be kept in the stream in case of filter & takeWhile, and in
                case of dropWhile if the predicate(condition) meet then drop the element from the stream. returns Stream<T>
            -------------
            -> limit(long maxSize) : This reduces the stream size specified in the argument. Returns Stream<T>
            -> skip(long n)        : This method skip elements, meaning the element wouldn't be part of output stream.
            -> peak(Consumer<? super T> action): This function does not change the stream, but allows us to perform some
                interim consumer function while the pipeline is processing.
            -> map(Function<? super T, extends R> mapper): We read "<? super T, extends R>" mapper as  "map takes a
                function that can accept T or any of its superclasses, and return R or any of its subclasses".
                 This function applies to every element in the stream. Because it's a function, the return type can be
                different, which has the effort of transforming the stream to a different stream of different type.
            -> sorted(): It uses the natural order sort, which means element in the stream must implement comparable.
            -> sorted(Comparator<? super T> comparator) : If your element don't use Comparable, you'll want to use
                sorted and pass a comparator.

            Important: takeWhile() and dropWhile() is different from the filter() method. takeWhile()/dropWhile() stops
            iteration the moment given predicate(condition) satisfied, and it stops the stream. it never iterates through
            all remaining elements. In case the given condition is failed in the starting itself then takeWhile()/dropWhile()
            will not return anything. Example: in the below,
            .takeWhile(i -> i <= 'E')   works fine because it is satisfying condition will A to E,
            .takeWhile(i -> i > 'E')   does not work because the iteration starts from A, and A > E is false and it stops.

         */
        System.out.println("------intermediate methods explanation---------------------");
         IntStream.iterate((int) 'A', n -> n <= (int) 'z', n -> n+1)
                 .filter(Character::isAlphabetic)
//                 .filter(i -> Character.toUpperCase(i) > 'E' )
//                 .skip(5)
                 .dropWhile(i -> Character.toUpperCase(i) <= 'E')    // 'E'  → char → numeric value 69, And char is an unsigned
                 .takeWhile(i -> i < 'a')                            // 16-bit integer so i <= 69
                 .forEach(d -> System.out.printf("%c ", d)); // d is an int, %c tells printf to treat that int as a
                                                                // Unicode character

        System.out.println();
        System.out.println("----------------distinct example---------------------");
        Random random1 = new Random();
        Stream.generate(() -> random1.nextInt((int) 'A', (int) 'Z' +1))
                .limit(50)
                .distinct()
                .forEach((item) -> System.out.printf("%c ",item));
        /*
        Primitive Streams-----------------------
        In addition to the generic Streams, that lets you stream any reference type, java has three primitive streams.

      Special primitive|      Mapping from reference type                 |        Mapping from primitive type
        streams        |      to primitive                                |        to reference type
        ---------------------------------------------------------------------------------------------------------------
        DoubleStream   | mapToDouble(ToDoubleFunction<? super T>  mapper) | mapToObj(DoubleFunction<? extends U> mapper)
                       |                                                  | boxed()
       ----------------------------------------------------------------------------------------------------------------
       IntStream       | mapToInt(ToIntFunction<? super T> mapper)        | mapToObj(IntFunction<? extends U> mapper)
                       |                                                  | boxed()
       ----------------------------------------------------------------------------------------------------------------
       LongStream      |mapToLong(ToLongFunction<? super T> mapper)       | mapToObj(LongFunction<? extends U> mapper)
                       |                                                  | boxed()

         */
        System.out.println("--------Seat number Example-----------------");

        int maxSeats = 100;
        int seatsInRow = 10;
        var stream = Stream.iterate(0, i -> i < maxSeats, i -> i+1)
                .map(i -> new Seat((char) ('A' + i / seatsInRow), i % seatsInRow + 1))
                .skip(5)
                .limit(10)
                .peek(s -> System.out.println("--> "+s)) // for debugging purpose we can use peek()
                        .sorted(Comparator.comparing(Seat::price)
                                .thenComparing(Seat::toString));
//                .mapToDouble(Seat::price)
//                .boxed()
//                .mapToObj("%.2f"::formatted);  // final conversion to string while not using boxed()
//                .map("%.2f"::formatted); // normal map while using boxed()
        stream.forEach(System.out::println);
    }

    /**
     * Checks prime number
     * @return boolean
     */
    public static boolean isPrime(int number){
        if(number<=2) return (number == 2);

        for(int i=2; i<number ; i++){
            if(number % i == 0) return false;
        }
        return true;
    }
}