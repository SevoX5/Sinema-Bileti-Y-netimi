package com.example.sinema_bileti_yonetimi;

import java.sql.Date;
import java.sql.Time;

public class musterilerData {

    private Integer musteriId;
    private String filmAdi;
    private Double toplamFiyat;
    private Date tarih;
    private Time saat;

    // Constructor (Kurucu Metot)
    public musterilerData(Integer musteriId, String filmAdi, Double toplamFiyat, Date tarih, Time saat) {
        this.musteriId = musteriId;
        this.filmAdi = filmAdi;
        this.toplamFiyat = toplamFiyat;
        this.tarih = tarih;
        this.saat = saat;
    }

    // JavaFX TableView'ın verileri okuyabilmesi için Getter metodları şarttır
    public Integer getMusteriId() {
        return musteriId;
    }

    public String getFilmAdi() {
        return filmAdi;
    }

    public Double getToplamFiyat() {
        return toplamFiyat;
    }

    public Date getTarih() {
        return tarih;
    }

    public Time getSaat() {
        return saat;
    }
}