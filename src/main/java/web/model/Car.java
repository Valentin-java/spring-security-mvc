package web.model;

public class Car {
    private String model;
    private String country;
    private int year;

    public Car() {}

    public Car(String model, String country, int year) {
        this.model = model;
        this.country = country;
        this.year = year;
    }




    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Автомобиль: " +
                "Модель: " + model +
                ", Страна: " + country +
                ", Год: " + year;
    }
}
