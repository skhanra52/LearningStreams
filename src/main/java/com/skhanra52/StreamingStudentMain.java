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

import java.util.stream.Stream;

public class StreamingStudentMain {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");
        Student tim = new Student("AU", 2019, 30, "M",
                true, jmc, pymc);

        System.out.println(tim);
        tim.watchLecture("JMC", 10, 5, 2019);
        tim.watchLecture("PYMC", 7, 7, 2020);
        System.out.println(tim);

        Stream.generate(() -> Student.getRandomStudent(pymc,jmc))
                .limit(10)
                .forEach(System.out::println);

    }
}
