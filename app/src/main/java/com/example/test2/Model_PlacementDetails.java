package com.example.test2;

public class Model_PlacementDetails
{
    String Company, Type, Package;
    public Model_PlacementDetails()
    {

    }

    public Model_PlacementDetails(String company, String type, String aPackage)
    {
        Company = company;
        Type = type;
        Package = aPackage;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }
}
