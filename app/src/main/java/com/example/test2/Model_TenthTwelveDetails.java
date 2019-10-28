package com.example.test2;

public class Model_TenthTwelveDetails
{
    private String institution10, board10, yop10, percentage10;
    private String institution12, board12, yop12, percentage12, stream;

    public Model_TenthTwelveDetails()
    {
        //
    }
    public Model_TenthTwelveDetails(String institution10, String board10,String yop10,String percentage10,
                                    String strem, String institution12,String board12,String yop12,String percentage12)
    {

        this.institution10 = institution10;
        this.board10 = board10;
        this.yop10 = yop10;
        this.percentage10 = percentage10;

        this.stream = strem;

        this.institution12 = institution12;
        this.board12 = board12;
        this.yop12 = yop12;
        this.percentage12 = percentage12;

    }

    public String getInstitution10() {
        return institution10;
    }

    public String getBoard10() {
        return board10;
    }

    public String getYop10() {
        return yop10;
    }

    public String getPercentage10() {
        return percentage10;
    }

    public String getStream() {
        return stream;
    }

    public String getInstitution12() {
        return institution12;
    }

    public String getBoard12() {
        return board12;
    }

    public String getYop12() {
        return yop12;
    }

    public String getPercentage12() {
        return percentage12;
    }
}
