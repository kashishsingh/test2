package com.example.test2;

public class TestBasicDetails
{
    private String name,usn,email,father,date, phone, selectedRadio;
    private String mImageUrl;

    public TestBasicDetails()
    {}

    public TestBasicDetails(String name, String USN, String email, String phone, String father,
                            String date, String SelectedRadio, String mImageUrl)
    {
        this.name = name;
        this.usn = USN;
        this.email = email;
        this.phone = phone;
        this.father = father;
        this.date = date;
        this.selectedRadio = SelectedRadio;
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
        return selectedRadio;
    }

    public String getmImageUrl()
    {
        return mImageUrl;
    }
}
