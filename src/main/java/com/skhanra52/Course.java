package com.skhanra52;

public record Course(String courseCode, String title, int lectureCount) {

    public Course {

        if(lectureCount <= 0){
            lectureCount = 1;  // used to check percentage
        }
    }

    public Course(String courseCode, String title) {
        this(courseCode, title, 40);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(courseCode,title);
    }
}
