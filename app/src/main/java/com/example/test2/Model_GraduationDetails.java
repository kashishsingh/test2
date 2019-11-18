package com.example.test2;

public class Model_GraduationDetails
{
    private String qualification, institute, university, score, courseStart, courseEnd;

    public Model_GraduationDetails()
    {
        //
    }

    public Model_GraduationDetails(String qualification, String institute, String university,
                                   String score, String courseStart, String courseEnd)
    {
        this.qualification = qualification;
        this.institute = institute;
        this.university = university;
        this.score = score;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
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

    public String getCourseStart() {
        return courseStart;
    }

    public String getCourseEnd(){return courseEnd;}

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }
}
