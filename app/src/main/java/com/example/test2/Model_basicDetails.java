package com.example.test2;

public class Model_basicDetails
{
    private String name,usn,email,father,date, phone, gender;
    private String mImageUrl;

    public Model_basicDetails()
    {}

    public Model_basicDetails(String name, String USN, String email, String phone, String father,
                             String date, String gender, String mImageUrl)
    {
        this.name = name;
        this.usn = USN;
        this.email = email;
        this.phone = phone;
        this.father = father;
        this.date = date;
        this.gender = gender;
        this.mImageUrl = mImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getUsn() {
        return usn;
    }

    public String getEmail() {
        return email;
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
        return gender;
    }

    public String getmImageUrl()
    {
        return mImageUrl;
    }
}
