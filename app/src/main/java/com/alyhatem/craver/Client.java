package com.alyhatem.craver;

public class Client {
    private String Name,Favourite_Restaurant,UID;
    private int Age,Frequency;

    public Client(String name,int age,String Fav,int Freq){
        Name=name;
        Age=age;
        Favourite_Restaurant=Fav;
        Frequency=Freq;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFavourite_Restaurant() {
        return Favourite_Restaurant;
    }

    public void setFavourite_Restaurant(String favourite_Restaurant) {
        Favourite_Restaurant = favourite_Restaurant;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

}
