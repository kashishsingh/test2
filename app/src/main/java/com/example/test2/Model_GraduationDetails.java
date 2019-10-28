package com.example.test2;

public class Model_GraduationDetails
{
    private String qualification, institute, university, score, YOP, CourseStart, CourseEnd;

    public Model_GraduationDetails()
    {
        //
    }

    public Model_GraduationDetails(String qualification, String institute, String university,
                                   String score, String YOP, String CourseStart, String CourseEnd)
    {
        this.qualification = qualification;
        this.institute = institute;
        this.university = university;
        this.score = score;
        this.YOP = YOP;
        this.CourseStart = CourseStart;
        this.CourseEnd = CourseEnd;
    }

    public String getQualification() {
        return qualification;
    }

    public String getInstitute() {
        return institute;
    }

    public String getUniversity() {
        return university;
    }

    public String getScore() {
        return score;
    }

    public String getYOP() {
        return YOP;
    }

    public String getCourseStart() {
        return CourseStart;
    }

    public String getCourseEnd(){return CourseEnd;}
}
