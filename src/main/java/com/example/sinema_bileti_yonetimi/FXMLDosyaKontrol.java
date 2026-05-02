package com.example.sinema_bileti_yonetimi;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;//kütüphanelerin hata verenleri import ettim
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class FXMLDosyaKontrol implements Initializable {

    @FXML
    private Hyperlink signUp_AlreadyHaveAccount;

    public void switchForm(ActionEvent event){//login değişde create accounte tıklarsa var olan sayfayı gizler signup sayfayını açar.
        if (event.getSource() ==signin_createAccount)//getSource() metodu, o an hangi butona basıldığını bilgisayarın anlamasını sağlar.
        {
            signin_form.setVisible(false);// login panelini kapat
            signUp_form.setVisible(true);// register panelini aç

        } else if (event.getSource()==signUp_AlreadyHaveAccount) {
            signin_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }

    @FXML
    private TextField signUp_email;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private Button signUp_kapama;

    @FXML
    private Button signUp_kucultme;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private Button signUp_registerBtn;

    @FXML
    private TextField signUp_username;

    @FXML
    private Hyperlink signin_createAccount;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_kapama;

    @FXML
    private Button signin_kucultme;

    @FXML
    private Button signin_loginBtn;

    @FXML
    private PasswordField signin_password;

    @FXML
    private TextField signin_username;


    //DATABASE için gerekli araçlar (tools)
    private Connection connect;       // Veritabanı ile kurulacak canlı bağlantıyı temsil eder.
    private PreparedStatement prepare; // SQL sorgularını güvenli ve parametreli (daha hızlı) şekilde çalıştırmak için kullanılır.
    private Statement statement;      // Veritabanına basit, parametresiz SQL komutları göndermek için kullanılır.
    private ResultSet result;         // SELECT sorgusundan dönen verileri (tablo sonuçlarını) içinde tutan nesnedir.



    //login ve register için gerekli fonksiyonlar
    @FXML
    private  void signUp()
    {
       String sql = "INSERT INTO admin (email,username,password) VALUES (?,?,?)";

       connect =database.connetDb();
       try{
           prepare=connect.prepareStatement(sql);
           prepare.setString(1,signUp_email.getText());
           prepare.setString(2,signUp_username.getText());
           prepare.setString(3,signUp_password.getText());
            
           Alert alert;
           if(signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty())
           {
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Hata Mesajı");
               alert.setHeaderText(null);
               alert.setContentText("Lütfen tüm alanları doldurunuz.");
               alert.showAndWait();
               
           } else if (signUp_password.getText().length()<8) {

               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Hata Mesajı");
               alert.setHeaderText(null);
               alert.setContentText("şifre en az 8 karakter olmalı .");
               alert.showAndWait();
           }else{
               prepare.execute();//
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Bilgi Mesajı");
               alert.setHeaderText(null);
               alert.setContentText("Yeni Kayıt Başarılı!");
               alert.showAndWait();

               //kayıt olduktan sonra içlerini sil
               signUp_email.setText("");
               signUp_username.setText("");
               signUp_password.setText("");
           }

       }catch (Exception e){e.printStackTrace();}

    }

    //login ve register için gerekli fonksiyon lar
    private double x = 0;
    private double y = 0;

    @FXML
    private void signin() {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = database.connetDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signin_username.getText().trim());
            prepare.setString(2, signin_password.getText().trim());

            result = prepare.executeQuery();
            Alert alert;

            if (signin_username.getText().isEmpty() || signin_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Tüm alanları doldurun!");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    //burası getdata classın username aldık. aslında burda giriş yaptıktan sonra orda ismi değişik ,görünücek
                    getData.username =signin_username.getText();

                    // --- KONTROL BAŞLIYOR ---
                    System.out.println("ADIM 1: Veritabanı girişi onayladı.");

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bilgi Mesajı");
                    alert.setHeaderText(null);
                    alert.setContentText("Giriş Başarılı");
                    alert.showAndWait();

                    signin_loginBtn.getScene().getWindow().hide();
                    System.out.println("ADIM 2: Giriş penceresi gizlendi.");

                    try {
                        // FXML Yükleme denemesi
                        System.out.println("ADIM 3: YanPanel.fxml yüklenmeye çalışılıyor...");
                        Parent root = FXMLLoader.load(getClass().getResource("/com/example/sinema_bileti_yonetimi/YanPanel.fxml"));

                        System.out.println("ADIM 4: FXML dosyası başarıyla bulundu ve yüklendi.");

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.initStyle(StageStyle.UNDECORATED);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x); // Pencereyi düzgün kaydırmak için ScreenX/Y daha iyidir
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.setScene(scene);
                        stage.show();
                        System.out.println("ADIM 5: YanPanel ekrana başarıyla yansıtıldı.");

                    } catch (Exception e) {
                        // Eğer 3. adımdan sonra burası çalışırsa dosya yolunda veya FXML içeriğinde hata vardır
                        System.out.println("KRİTİK HATA: YanPanel yüklenemedi!");
                        e.printStackTrace();
                    }
                    // --- KONTROL BİTTİ ---

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata Mesajı");
                    alert.setHeaderText(null);
                    alert.setContentText("Yanlış Kullanıcı Adı veya Password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signin_kapama() //kapama için metot yazdım sonra scene builderden bunu seçtim .
    {
        System.exit(0);
    }

    public void signin_kucultme() {
        Stage stage = (Stage) signin_form.getScene().getWindow();
        // Pencereyi simge durumuna küçültür
        stage.setIconified(true);
    }


    public void signUp_kapama()
    {
        System.exit(0);
    }
    public void signUp_kucultme() {
        // Sahneyi (Stage) mevcut bir bileşen üzerinden yakalıyoruz
        Stage stage = (Stage) signUp_form.getScene().getWindow();
        // Pencereyi simge durumuna küçültür
        stage.setIconified(true);
    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}



