package com.example.sinema_bileti_yonetimi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent; // MouseEvent importu gerekli

public class SinemaBiletiYonetimi extends Application {

    // Pencere sürükleme işlemi için koordinat değişkenleri
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws Exception {
        // FXML dosyasını (arayüzü) yükler
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/sinema_bileti_yonetimi/login.fxml"));

        // Sahneyi oluşturur
        Scene scene = new Scene(root);

        // Mouse basıldığında pencerenin mevcut koordinatlarını alır
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event)->
        {
            stage.setX(event.getSceneX());
            stage.setY(event.getSceneY());
        });
        // Pencere kenarlıklarını ve üst barı (kapatma/küçültme) gizler
        stage.initStyle(StageStyle.TRANSPARENT);

        // Sahneyi pencereye ekler ve pencereyi gösterir
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Uygulamayı başlatır
        launch(args);
    }
}