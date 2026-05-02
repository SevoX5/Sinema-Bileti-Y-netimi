package com.example.sinema_bileti_yonetimi;
import java.sql.Date;
//import java.util.Date;

public class filmData {
    private String filmAdi;
    private String filmTuru;
    private String filmSuresi;
    private String gorsel;
    private Date tarih;
//parametreli constroctor
    public  filmData (String filmAdi,String filmTuru,String filmSuresi,String gorsel,Date tarih){

        this.filmAdi=filmAdi;
        this.filmTuru=filmTuru;
        this.filmSuresi=filmSuresi;
        this.gorsel=gorsel;
        this.tarih=tarih;

    }
    //kapsülleme
    public String getFilmAdi(){
        return filmAdi;
    }
    public String  getFilmTuru(){
        return filmTuru;
    }
    public String getFilmSuresi(){
        return filmSuresi;
    }
    public String getGorsel(){
        return gorsel;
    }
    public Date getTarih(){
        return tarih;
    }

}