package com.example.dell.navbot;

public class Itemdata {
    public  String title;
    public String detail;
    public String detail2;
    public String num_worker;
    public String id_company;
    public int image;
    public Itemdata(String title,String detail,String detail2,String num_worker,int image,String idcomp)
    {
        this.title=title;
        this.detail=detail;
        this.detail2=detail2;
        this.num_worker=num_worker;
        this.image=image;
        id_company = idcomp;
    }

}
