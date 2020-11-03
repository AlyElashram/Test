package com.alyhatem.craver;

public class Client {
    private String Name,Favourite_Restaurant,PhoneNumber;
    private int Age;

    public Client(String name,int age,String Fav,String PhoneNumber){
        Name=name;
        Age=age;
        Favourite_Restaurant=Fav;
        this.PhoneNumber=PhoneNumber;


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


    public String getPhoneNumber(){
        return PhoneNumber;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

}
