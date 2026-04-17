package com.example.sinema_bileti_yonetimi;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;//kütüphanelerin hata verenleri import ettim
import javafx.stage.Stage;


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


