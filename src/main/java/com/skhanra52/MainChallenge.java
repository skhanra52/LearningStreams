package com.skhanra52;
/*
    1. Copy the two courses, jmc, pymc from the MainCollect's main method, passing both and additional argument for
       lecture count, 50 for pymc, 100 for jmc.
    2. Add a third course, title "Create Gaming Java". You don't have to pass a lecture count for this one.
    3. Use Stream.generate() or Stream.iterate() to generate 5000 random students and create a list of those.
    4. Use the getPercentageComplete() method, to calculate the average percentage completed for all the students for
       just the "Java Masterclass" using the reduce terminal operation.
    5. Use the result, multiplying by 1.25, to collect a group of students(either as a list or a set). These would be
       student who has completed more than three quarters of that average percentage.
    6. Sort by the longest enrolled students who are still active, because you are going to offer your new course to
       10 of the students for a trial run.
    7. Add a new course to these ten students.

    Make one change to the Student's getRandomStudent(), using minimum lecture of 30.

 */

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MainChallenge {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);
        Course cgj = new Course("CGJ", "Create Gaming Java");

        List<Student> studentList = Stream.generate(() -> Student.getRandomStudent(pymc, jmc))
                .limit(100)
                .toList();
        studentList.forEach(System.out::println);

        // Use the getPercentageComplete() method, to calculate the average percentage completed for all the students
        // for just the "Java Masterclass" using the reduce terminal operation.
        double completedPercentSum = studentList.stream()
                .map(student -> student.getEngagementMap().get("JMC"))
                .map(CourseEngagement::getPercentComplete)
                .reduce(0.0, Double::sum);
        double avgCompletedPercentage = completedPercentSum / studentList.size();
        System.out.println("Average JMC complete percentage: "+avgCompletedPercentage+" %");

        // Use the result, multiplying by 1.25, to collect a group of students(either as a list or a set). These would
        // be student who has completed more than three quarters of that average percentage.

       var completedStudentList = studentList.stream()
               .filter((student) -> {
                   double completedPercent = student.getEngagementMap().get("JMC").getPercentComplete();
                   return completedPercent > avgCompletedPercentage * 1.25;
               })
               .sorted(Comparator.comparing(student -> {
                      int abc =  student.getEngagementMap()
                               .get("JMC")
                               .getLastActivityYear();
                      return abc;
                    }
                )
               )
//               .sorted(Comparator.comparing(student -> {
//                   return student.getEngagementMap().get("JMC").getLastActivityMonth();
//               }))
               .toList();
       System.out.println("----------------------------------");
        completedStudentList.forEach(System.out::println);

        // Sort by the longest enrolled students who are still active, because you are going to offer your new course to
        // 10 of the students for a trial run.

    }
}
