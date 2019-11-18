package com.example.test2;

public class Model_basicDetails
{
    private String name,usn,personalEmail,father,date, phone, selectedRadio, id, collegeEmail;
    private String mImageUrl;

    public Model_basicDetails()
    {}

    public Model_basicDetails(String name, String USN, String personalEmail, String phone, String father,
                             String date, String gender, String mImageUrl, String id, String collegeEmail)
    {
        this.name = name;
        this.usn = USN;
        this.personalEmail = personalEmail;
        this.phone = phone;
        this.father = father;
        this.date = date;
        this.selectedRadio = gender;
        this.mImageUrl = mImageUrl;
        this.id = id;
        this.collegeEmail = collegeEmail;
    }

    public String getCollegeEmail() {
        return collegeEmail;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUsn() {
        return usn;
    }

    public String getEmail() {
        return personalEmail;
    }

    public String getPhone() {
        return phone;
    }

    public String getFather() {
        return father;
    }

    public String getDate() {
        return date;
    }

    public String getSelectedRadio() {
        return selectedRadio;
    }

    public String getmImageUrl()
    {
        return mImageUrl;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.selectedRadio = gender;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setCollegeEmail(String collegeEmail) {
        this.collegeEmail = collegeEmail;
    }


}
