package com.example.sinema_bileti_yonetimi;
import java.sql.Date;
//import java.util.Date;


public class filmData {
    private Integer id;
    private String filmAdi;
    private String filmTuru;
    private String filmSuresi;
    private String gorsel;
    private Date tarih;
    private String durum;
//parametreli constroctor
    public  filmData (Integer id,String filmAdi,String filmTuru,String filmSuresi,String gorsel,Date tarih,String durum){
        this.id=id;
        this.filmAdi=filmAdi;
        this.filmTuru=filmTuru;
        this.filmSuresi=filmSuresi;
        this.gorsel=gorsel;
        this.tarih=tarih;
        this.durum=durum;

    }
    //kapsülleme

    public String getDurum(){
        return durum;
    }
    public Integer getId(){
        return id;
    }
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