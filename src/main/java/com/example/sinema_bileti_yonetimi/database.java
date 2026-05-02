package com.example.sinema_bileti_yonetimi;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    public static Connection connetDb() {
        Connection connect = null; // 1. Değişkeni dışarıda tanımla
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 2. Değişkeni burada doldur
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sinemabileti", "root", "");
            System.out.println("Bağlantı Başarılı!");
        } catch (Exception e) {
            System.out.println("Bağlantı Hatası!");
            e.printStackTrace();
        }
        return connect; // 3. Artık dolu olan (veya hata varsa null olan) nesneyi döndür
    }


}
