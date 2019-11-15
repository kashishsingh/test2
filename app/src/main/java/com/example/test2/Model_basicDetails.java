package com.example.test2;

public class Model_basicDetails
{
    private String name,usn,email,father,date, phone, selectedRadio, id;
    private String mImageUrl;

    public Model_basicDetails()
    {}

    public Model_basicDetails(String name, String USN, String email, String phone, String father,
                             String date, String gender, String mImageUrl, String id)
    {
        this.name = name;
        this.usn = USN;
        this.email = email;
        this.phone = phone;
        this.father = father;
        this.date = date;
        this.selectedRadio = gender;
        this.mImageUrl = mImageUrl;
        this.id = id;
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


    public void setName(String name) {
        this.name = name;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
