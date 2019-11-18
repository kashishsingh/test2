package com.example.test2;

public class Model_PGDetail
{
    private String YOJ, YOP, CGPA, backlog, address, SGPA1, SGPA2, SGPA3, SGPA4, SGPA5, SGPA6;

    public  Model_PGDetail()
    {
        //
    }

    public Model_PGDetail(String YOJ, String YOP, String CGPA, String backlog, String address,
                          String SGPA1, String SGPA2, String SGPA3, String SGPA4, String SGPA5, String SGPA6) {
        this.YOJ = YOJ;
        this.YOP = YOP;
        this.CGPA = CGPA;
        this.backlog = backlog;
        this.address = address;
        this.SGPA1 = SGPA1;
        this.SGPA2 = SGPA2;
        this.SGPA3 = SGPA3;
        this.SGPA4 = SGPA4;
        this.SGPA5 = SGPA5;
        this.SGPA6 = SGPA6;
    }

    public String getYOJ() {
        return YOJ;
    }

    public void setYOJ(String YOJ) {
        this.YOJ = YOJ;
    }

    public String getYOP() {
        return YOP;
    }

    public void setYOP(String YOP) {
        this.YOP = YOP;
    }

    public String getCGPA() {
        return CGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getBacklog() {
        return backlog;
    }

    public void setBacklog(String backlog) {
        this.backlog = backlog;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSGPA1() {
        return SGPA1;
    }

    public void setSGPA1(String SGPA1) {
        this.SGPA1 = SGPA1;
    }

    public String getSGPA2() {
        return SGPA2;
    }

    public void setSGPA2(String SGPA2) {
        this.SGPA2 = SGPA2;
    }

    public String getSGPA3() {
        return SGPA3;
    }

    public void setSGPA3(String SGPA3) {
        this.SGPA3 = SGPA3;
    }

    public String getSGPA4() {
        return SGPA4;
    }

    public void setSGPA4(String SGPA4) {
        this.SGPA4 = SGPA4;
    }

    public String getSGPA5() {
        return SGPA5;
    }

    public void setSGPA5(String SGPA5) {
        this.SGPA5 = SGPA5;
    }

    public String getSGPA6() {
        return SGPA6;
    }

    public void setSGPA6(String SGPA6) {
        this.SGPA6 = SGPA6;
    }
}
