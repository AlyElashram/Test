package com.alyhatem.craver;

public class Client {
    private String Name,Favourite_Restaurant,Frequency,UID;
    private int Age;

    public Client(String name,int age,String Fav,String Freq,String Uid){
        Name=name;
        Age=age;
        Favourite_Restaurant=Fav;
        Frequency=Freq;
        UID=Uid;
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

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
    public String getUID(){
        return UID;
    }
}
