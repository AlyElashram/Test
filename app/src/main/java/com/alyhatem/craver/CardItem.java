package com.alyhatem.craver;

import org.w3c.dom.Text;

public class CardItem {
    private int imageresource;
    private String Text1;
    private String Text2;

    public CardItem(int imageresource,String Text1,String Text2){
        this.imageresource=imageresource;
        this.Text1= Text1;
        this.Text2=Text2;
    }

    public int getImageresource() {
        return imageresource;
    }

    public String getText1() {
        return Text1;
    }

    public String getText2() {
        return Text2;
    }
}
