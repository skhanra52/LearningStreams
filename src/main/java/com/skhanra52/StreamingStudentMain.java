// Setup Explained below
/*
 We be using LocalDates, and a class called Period, to get the number of months elapsed between two dates.
 The course type should have a course code, a course title, and a lecture count. We can make this an immutable class(
 that is record).
 Course Record Signature:
 -----------------------
 courseCode: String
 title: String
 LectureCount: int
-------------------------
 Each student will have a course engagement instance, for every course they're enrolled in.
 It should have the fields for the course,
  1. enrollment date
  2. engagement type
  3. last lecture
  4. last activity date

 It should have the usual getters, plus getters for calculated fields as shown here.
 -> The getMonthsSinceActive method should return the months elapsed, since the last course activity.
 -> The getPercentComplete method should use the last lecture, and the lecture count on course, to return a percentage complete.
 -> The watchLecture method would get called when a student engaged in the course, and should update fields on the
    engagement record. It takes a lecture number, and an activity date.
 Class CourseEngagement signature:
 ---------------------------------
  course: Course
  enrollmentDate: LocalDate
  engagementType: String
  lastLecture: int
  lastActivityDate: LocalDate
  methods---------------
  getCourseCode(): String
  getEnrollmentYear(): int
  getLastActivityYear(): int
  getLastActivityMonth(): String
  getMonthSinceActive(): int
  getPercentComplete(): double
  watchLecture(lecture, date)
  --------------------------------------------
 The Student class should have a student id, and demographic data, like country code, year enrolled, age at time of
 enrollment, gender, and a programming experience flag.
 Your student should also have a map of CourseEngagements, keyed by course code.

 -> Include getters for all of these fields In addition to the usual getters, add getter methods for calculated fields,
    like getYearsSinceEnrolled, and getAge.
 -> Include the getters, getMonthsSinceActive and getPercentComplete, that take a course code and return data from the
    matching CourseEngagement record.
 -> Add an overloaded version of getMonthsSinceActive, to get the least number of inactive months, from all courses.
 -> You should have overloaded addCourse methods, one that takes a specified activity date, and one that will instead
    default to the current date.
 -> Include the method watchLecture, that takes a course code, a lecture number and an activity year and month, and
    calls the method of the same name, on the course engagement record.
 -> Finally, create a static factory method on this class, getRandomStudent, that will return a new instance of Student,
    with random data, populating a student's fields.
 Make sure to pass courses to this method, and pass them to the student constructor.
 For each course, call watchLecture with a random lecture number, and activity year and month, so that each Student will
 have a different activity for each course.

 Class Student signature:
 ------------------------------
 studentId: long
 countryCode: String
 yearEnrolled: int
 ageEnrolled: int
 gender: String
 programmingExperience: boolean
 engagementMap: Map<String, CourseEngagement>
 Methods------------------------------
 addCourse(course)
 addCourse(course, enrollDate)
 getAge(): int
 getMonthSinceActive(courseCode): int
 getMonthSinceActive(): int
 getPercentComplete(courseCode): double
 getYearsSinceEnrolled(): int
 watchLecture(lectureNumber, month, year)
 static getRandomStudent(Course courses): Student

 */

package com.skhanra52;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingStudentMain {

    public static void main(String[] args) {

        /*
        Terminal operation examples:
        ----------------------------------------------------------------------------------------------------------------
        Return Type         |   Terminal Operations
        ----------------------------------------------------------------------------------------------------------------
        long                |   count()
        DoubleStatistics    |   summaryStatistics()
        boolean             |   allMatch(Predicate<? super T> predicate)
        boolean             |   anyMatch(Predicate<? super T> predicate)
        boolean             |   noneMatch(Predicate<? super T> predicate)
        ----------------------------------------------------------------------------------------------------------------
        The terminal operation challenges:
        1. Create a source of stream of Student. - done
        2. Use the static method as the supplier. - done
        3. Use a large enough number to get a variety of student data.
        4. Use a combination of the intermediate and terminal operations to answer the following questions:
              i> How many male and female students are there in the group.
             ii> How many students fall in the three age ranges, less than 30,between 30 and 60 years, over 60 years.
            iii> Use summaryStatistics on the Student's age, to get better idea of how old the student population is.
             iv> What countries are the student from, print a distinct list of the country codes.
              v> Are there students that are still active and have enrolled more than 7 years? Use one of the match
                 terminal operation to answer this question.
             vi> Select 5 of the students above and print their information out.

         */



        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");
        Student tim = new Student("AU", 2019, 30, "M",
                true, jmc, pymc);
        System.out.println("Student:  "+tim);
        tim.watchLecture("JMC", 10, 5, 2019);
        tim.watchLecture("PYMC", 7, 7, 2020);
        System.out.println(tim);

        // 3. Use a large enough number to get a variety of student data.
        List<Student> randomStudent =  Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
                .limit(20)
                //.collect(Collectors.toList());
                .toList();
        
        int maleCount = (int) randomStudent.stream()
                .filter(student -> student.getGender().equals("M"))
                .count();

        int femaleStudent = (int) randomStudent.stream()
                .filter((student) -> student.getGender().equals("F"))
                .count();

        System.out.println("Number of male student: "+ maleCount + " number of female student: "+femaleStudent);

        // ----------------or----------------------

        Map<String, Long> groupGender = randomStudent.stream()
                .collect(Collectors.groupingBy(
                        Student::getGender,
                        Collectors.counting()));

//        long maleStudentCount = (groupGender.getOrDefault("M",0L));
//        long femaleStudentCount = groupGender.getOrDefault("F",0L);
//        System.out.println("Male Student Count: "+maleStudentCount +" and female student cont: "+femaleStudentCount);
        //--------- OR-----------------------------
        for(String gender: new String[]{"M","F","U"}){
            System.out.println(gender+" -> "+groupGender.getOrDefault(gender, 0L));
        }

        // ii> How many students fall in the three age ranges, less than 30,between 30 and 60 years, over 60 years.

        long ageBelowThirty = randomStudent.stream()
                .filter((student) -> student.getAge() < 30)
                .count();

        long ageBetweenThirtyToSixty = randomStudent.stream()
                .filter((student -> student.getAge() > 30 && student.getAge() < 60))
                .count();

        long ageAboveSixty = randomStudent.stream()
                .filter((student -> student.getAge() > 60))
                .count();

        System.out.println("Student below thirty: "+ageBelowThirty +
                ", student in between thirty to sixty: "+ageBetweenThirtyToSixty
                + ", student above sixty: "+ageAboveSixty);

//        List<Predicate<Student>>  list = List.of(
//                (s) -> s.getAge() < 30,
//                (s) -> s.getAge() > 30 && s.getAge() < 60,
//                (s) -> s.getAge() > 60
//        );

//        for (Predicate<Student> studentPredicate : list) {
//            long counts = randomStudent.stream()
//                    .filter(studentPredicate)
//                    .count();
//            System.out.println(studentPredicate + " count -> " + counts);
//        }
        // ---------OR we can use enum here-----------------------
        for(AgeGroup group : AgeGroup.values()){
            long counts = randomStudent.stream()
                    .filter(group.predicate)
                    .count();
            System.out.println(group.description + " count -> " + counts);
        }

        IntStream ageStream = randomStudent.stream()
                .mapToInt(Student::getAgeEnrolled);
        System.out.println("Stats for enrollment age: "+ageStream.summaryStatistics());

        IntStream currentAgeStream = randomStudent.stream()
                .mapToInt(Student::getAge);
        System.out.println("Stats for current age: "+currentAgeStream.summaryStatistics());

        //  iv> What countries are the student from, print a distinct list of the country codes.
        randomStudent.stream()
                .map(Student::getCountryCode)
                .distinct()
                .sorted()
                .forEach((country) -> System.out.print(country+", "));

        // v> Are there students that are still active and have enrolled more than 7 years? Use one of the match
        //    terminal operation to answer this question.

        System.out.println();
        var olderActiveStudent = randomStudent.stream()
                .anyMatch((student) -> (student.getAge() - student.getAgeEnrolled()) >= 7 &&
                        student.getMonthsSinceActive() < 12);
        System.out.println("Do we have older active student than 7 years? "+olderActiveStudent);

    }
}

enum AgeGroup {
    /*
    Here BELOW_30, BETWEEN_30_60, and ABOVE_60 are the three values of enum AgeGroup which we can access through
    AgeGroup.values() provided by compiler(values() is a compiler generated method).
     -> Internally the BELOW_30, BETWEEN_30_60, and ABOVE_60 are the instance objects(how we instantiate a class in
     main or another class by invoking the new constructor_name(args....))
     -> Only changes here is the instances variables created with in the enum itself. so actually it looks like below
        AgeGroup BELOW_30 = new AgeGroup("Age < 30", s -> s.getAge() < 30);
        Predicate<Student> predicate = AgeGroup.BELOW_30.predicate;
        System.out.println(predicate) // output: s -> s.getAge() < 30
     */
    BELOW_30("Age < 30", s -> s.getAge() < 30),
    BETWEEN_30_60("Age between 30 to 60",  s -> s.getAge() > 30 && s.getAge() < 60),
    ABOVE_60("Age above 60", s -> s.getAge() > 60);

    final String description;
    final Predicate<Student> predicate;
    AgeGroup(String description, Predicate<Student> predicate){
        this.description = description;
        this.predicate = predicate;
    }
}
