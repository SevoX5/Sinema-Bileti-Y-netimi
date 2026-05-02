module com.example.sinema_bileti_yonetimi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.sinema_bileti_yonetimi to javafx.fxml;
    exports com.example.sinema_bileti_yonetimi;
}