package com.example.test2;

public class Model_PGDetail
{
    private String YOJ, YOP, semester, SGPA, CGPA, backlog, address, collegeEmail;

    public  Model_PGDetail()
    {
        //
    }

    public Model_PGDetail(String YOJ, String YOP, String semester, String SGPA, String CGPA,
                          String backlog, String address, String collegeEmail) {
        this.YOJ = YOJ;
        this.YOP = YOP;
        this.semester = semester;
        this.SGPA = SGPA;
        this.CGPA = CGPA;
        this.backlog = backlog;
        this.address = address;
        this.collegeEmail = collegeEmail;
    }

    public String getYOJ() {
        return YOJ;
    }

    public String getYOP() {
        return YOP;
    }

    public String getSemester() {
        return semester;
    }

    public String getSGPA() {
        return SGPA;
    }

    public String getCGPA() {
        return CGPA;
    }

    public String getBacklog() {
        return backlog;
    }

    public String getAddress() {
        return address;
    }

    public String getCollegeEmail() {
        return collegeEmail;
    }

    public void setYOJ(String YOJ) {
        this.YOJ = YOJ;
    }

    public void setYOP(String YOP) {
        this.YOP = YOP;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSGPA(String SGPA) {
        this.SGPA = SGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public void setBacklog(String backlog) {
        this.backlog = backlog;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCollegeEmail(String collegeEmail) {
        this.collegeEmail = collegeEmail;
    }
}
