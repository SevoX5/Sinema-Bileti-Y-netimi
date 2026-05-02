package com.example.sinema_bileti_yonetimi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLdashboardKontrol implements Initializable {

    @FXML
    private AnchorPane anasayfa_form;

    @FXML
    private Button anasayfa_kapatma;

    @FXML
    private Button anasayfa_kucult;

    @FXML
    private Label dashboard_MevcutFilmler;

    @FXML
    private Label dashboard_ToplamCiro;

    @FXML
    private Label dashboard_ToplamSatilanBilet;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TextField filmEkle_Search;

    @FXML
    private TextField filmEkle_YayinTarihi;

    @FXML
    private Button filmEkle_btn;

    @FXML
    private Button filmEkle_ekleBtn;

    @FXML
    private TextField filmEkle_filmAdi;

    @FXML
    private TextField filmEkle_filmSuresi;

    @FXML
    private TextField filmEkle_filmTuru;

    @FXML
    private AnchorPane filmEkle_form;

    @FXML
    private ImageView filmEkle_gorsel;

    @FXML
    private Button filmEkle_guncelleBtn;

    @FXML
    private Button filmEkle_importBtn;

    @FXML
    private TableColumn<?, ?> filmEkle_kln_filmSuresi;

    @FXML
    private TableColumn<?, ?> filmEkle_kln_filmTuru;

    @FXML
    private TableColumn<?, ?> filmEkle_kln_filmAdi;

    @FXML
    private TableColumn<?, ?> filmEkle_kln_yayinTarihi;

    @FXML
    private Button filmEkle_silBtn;

    @FXML
    private TableView<?> filmEkle_tablo;

    @FXML
    private Button filmEkle_temizleBtn;

    @FXML
    private Label gosterimAyarlari_baslık;

    @FXML
    private Button gosterimAyarlari_btn;

    @FXML
    private ComboBox<?> gosterimAyarlari_durum;

    @FXML
    private AnchorPane gosterimAyarlari_form;

    @FXML
    private ImageView gosterimAyarlari_gorsel;

    @FXML
    private Button gosterimAyarlari_guncelleBtn;

    @FXML
    private TableColumn<?, ?> gosterimAyarlari_kln_durum;

    @FXML
    private TableColumn<?, ?> gosterimAyarlari_kln_filmAdi;

    @FXML
    private TableColumn<?, ?> gosterimAyarlari_kln_filmSuresi;

    @FXML
    private TableColumn<?, ?> gosterimAyarlari_kln_filmTuru;

    @FXML
    private TextField gosterimAyarlari_search;

    @FXML
    private Button gosterimAyarlari_silBtn;

    @FXML
    private TableView<?> gosterimAyarlari_tablo;

    @FXML
    private TextField musteriler_biletNo;

    @FXML
    private Button musteriler_btn;

    @FXML
    private TextField musteriler_filmAdi;

    @FXML
    private AnchorPane musteriler_form;

    @FXML
    private TableColumn<?, ?> musteriler_kln_biletNumarasi;

    @FXML
    private TableColumn<?, ?> musteriler_kln_filmAdi;

    @FXML
    private TableColumn<?, ?> musteriler_kln_islemSaati;

    @FXML
    private TableColumn<?, ?> musteriler_kln_islemTarihi;

    @FXML
    private TableColumn<?, ?> musteriler_kln_toplamTutar;

    @FXML
    private TextField musteriler_satisSaati;

    @FXML
    private TextField musteriler_satisTarihi;

    @FXML
    private TextField musteriler_search;

    @FXML
    private Button musteriler_silBtn;

    @FXML
    private TableView<?> musteriler_tablo;

    @FXML
    private Button musteriler_temizleBtn;

    @FXML
    private TextField musteriler_toplamTutar;

    @FXML
    private Button signOut_btn;

    @FXML
    private Label username;

    @FXML
    private Label vizyondakiFilmler_baslik;

    @FXML
    private Button vizyondakiFilmler_btn;

    @FXML
    private TextField vizyondakiFilmler_filmAdi;

    @FXML
    private TextField vizyondakiFilmler_filmTürü;

    @FXML
    private AnchorPane vizyondakiFilmler_form;

    @FXML
    private ImageView vizyondakiFilmler_gorsel;

    @FXML
    private TableColumn<?, ?> vizyondakiFilmler_kln_filmAdi;

    @FXML
    private TableColumn<?, ?> vizyondakiFilmler_kln_filmTuru;

    @FXML
    private TableColumn<?, ?> vizyondakiFilmler_kln_yayinTarihi;

    @FXML
    private Button vizyondakiFilmler_listeleBtn;

    @FXML
    private Button vizyondakiFilmler_satinAlBtn;

    @FXML
    private Spinner<?> vizyondakiFilmler_standartAdet;

    @FXML
    private Label vizyondakiFilmler_standartFiyat;

    @FXML
    private TableView<?> vizyondakiFilmler_tablo;

    @FXML
    private TextField vizyondakiFilmler_tarih;

    @FXML
    private Button vizyondakiFilmler_temizleBtn;

    @FXML
    private Label vizyondakiFilmler_toplam;

    @FXML
    private Spinner<?> vizyondakiFilmler_vipAdet;

    @FXML
    private Label vizyondakiFilmler_vipFiyat;

    @FXML
    private Button vizyondakiFilmler_yazdirBtn;


   //bir btn basınca diğer form ekranları gizlensin //istenilen form sayfası açilsin
    public void switchForm(ActionEvent event){
      if(event.getSource()==dashboard_btn){
          dashboard_form.setVisible(true);
          filmEkle_form.setVisible(false);
          vizyondakiFilmler_form.setVisible(false);
          gosterimAyarlari_form.setVisible(false);
          musteriler_form.setVisible(false);

      } else if (event.getSource()==filmEkle_btn) {
          dashboard_form.setVisible(false);
          filmEkle_form.setVisible(true);
          vizyondakiFilmler_form.setVisible(false);
          gosterimAyarlari_form.setVisible(false);
          musteriler_form.setVisible(false);

      } else if (event.getSource()==vizyondakiFilmler_btn) {
          dashboard_form.setVisible(false);
          filmEkle_form.setVisible(false);
          vizyondakiFilmler_form.setVisible(true);
          gosterimAyarlari_form.setVisible(false);
          musteriler_form.setVisible(false);

      } else if (event.getSource()==gosterimAyarlari_btn) {
          dashboard_form.setVisible(false);
          filmEkle_form.setVisible(false);
          vizyondakiFilmler_form.setVisible(false);
          gosterimAyarlari_form.setVisible(true);
          musteriler_form.setVisible(false);

      } else if (event.getSource()==musteriler_btn) {
          dashboard_form.setVisible(false);
          filmEkle_form.setVisible(false);
          vizyondakiFilmler_form.setVisible(false);
          gosterimAyarlari_form.setVisible(false);
          musteriler_form.setVisible(true);
      }
    }



    //getdata'dan aldığı ismi burda göstericek
    public void usernameGoster(){
        username.setText(getData.username);
    }

    public void kapama() //kapama için metot yazdım sonra scene builderden bunu seçtim .
    {
        System.exit(0);
    }

    public void kucultme() {
        Stage stage = (Stage) anasayfa_form.getScene().getWindow();
        // Pencereyi simge durumuna küçültür
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        usernameGoster();
    }
}
