package com.example.sinema_bileti_yonetimi;
import java.sql.Time;
import java.time.LocalTime;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Region;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class FXMLdashboardKontrol implements Initializable {

    // --- FXML BİLEŞENLERİ ---
    @FXML
    private DatePicker filmEkle_YayinTarihi;
    @FXML
    private AnchorPane anasayfa_form, dashboard_form, filmEkle_form, vizyondakiFilmler_form, gosterimAyarlari_form, musteriler_form;
    @FXML
    private Button dashboard_btn, filmEkle_btn, vizyondakiFilmler_btn, gosterimAyarlari_btn, musteriler_btn;
    @FXML
    private TextField filmEkle_filmAdi, filmEkle_filmSuresi, filmEkle_filmTuru;
    @FXML
    private TextField filmEkle_Search;
    @FXML
    private ImageView filmEkle_gorsel;
    @FXML
    private TableView<filmData> filmEkle_tablo;
    @FXML
    private TableColumn<filmData, String> filmEkle_kln_filmAdi, filmEkle_kln_filmTuru, filmEkle_kln_filmSuresi, filmEkle_kln_yayinTarihi;
    @FXML
    private Label username;

    // --- VİZYONDAKİ FİLMLER BİLEŞENLERİ (YENİ) ---
    @FXML private TableView<filmData> vizyondakiFilmler_tablo;
    @FXML private TableColumn<filmData, String> vizyondakiFilmler_kln_filmAdi, vizyondakiFilmler_kln_filmTuru, vizyondakiFilmler_kln_yayinTarihi;
    @FXML private TextField vizyondakiFilmler_filmAdi, vizyondakiFilmler_filmTuru, vizyondakiFilmler_tarih;
    @FXML private ImageView vizyondakiFilmler_gorsel;
    @FXML private Spinner<Integer> vizyondakiFilmler_vipAdet, vizyondakiFilmler_standartAdet;
    @FXML private Label vizyondakiFilmler_vipFiyat, vizyondakiFilmler_standartFiyat, vizyondakiFilmler_toplam, vizyondakiFilmler_baslik;


    // --- GÖSTERİM AYARLARI BİLEŞENLERİ (YENİ EKLENENLER) ---
    @FXML
    private TextField gosterimAyarlari_search;
    @FXML
    private TableView<filmData> gosterimAyarlari_tablo;
    @FXML
    private TableColumn<filmData, String> gosterimAyarlari_kln_filmAdi, gosterimAyarlari_kln_filmTuru, gosterimAyarlari_kln_filmSuresi, gosterimAyarlari_kln_durum;
    @FXML
    private ComboBox<String> gosterimAyarlari_durum;
    @FXML
    private Label gosterimAyarlari_filmAdi; // Sağ üstteki film başlığı için
    @FXML
    private ImageView gosterimAyarlari_imageView; // Sağdaki film görseli için

    //musteriler kısmı için fxml
    @FXML
    private TableView<musterilerData> musteriler_tablo;
    @FXML
    private TableColumn<musterilerData, String> musteriler_kln_biletNumarasi;

    @FXML
    private TableColumn<musterilerData, String> musteriler_kln_filmAdi;

    @FXML
    private TableColumn<musterilerData, String> musteriler_kln_islemTarihi;

    @FXML
    private TableColumn<musterilerData, String> musteriler_kln_islemSaati;
    // --- MÜŞTERİLER PANELİNDEKİ SOL KUTUCUKLAR ---
    @FXML
    private TextField musteriler_biletNo;
    @FXML
    private TextField musteriler_filmAdi;
    @FXML
    private TextField musteriler_satisTarihi;
    @FXML
    private TextField musteriler_satisSaati;
    @FXML
    private TextField musteriler_search;


    //dashboard kısmı için gereken
    @FXML
    private Label dashboard_ToplamSatilanBilet;
    @FXML
    private Label dashboard_ToplamCiro;
    @FXML
    private Label dashboard_MevcutFilmler;



    // --- VERİTABANI VE ARAÇLAR ---
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Image gorsel;
    private int updateID; // Tablodan seçilen filmin ID'sini hafızada tutar


    // Gösterim Durum Listesi (ComboBox için)
    private String[] gosterimList = {"Vizyonda", "Vizyondan Kalktı"};

    // Bir butona basınca ilgili formu açar, diğerlerini gizler
    //SAYFALAR ARASI GEÇİŞ
    public void switchForm(ActionEvent event) {
        dashboard_form.setVisible(event.getSource() == dashboard_btn);
        filmEkle_form.setVisible(event.getSource() == filmEkle_btn);
        vizyondakiFilmler_form.setVisible(event.getSource() == vizyondakiFilmler_btn);
        gosterimAyarlari_form.setVisible(event.getSource() == gosterimAyarlari_btn);
        musteriler_form.setVisible(event.getSource() == musteriler_btn);

        // Formlar arası geçişte listeleri yenileme özelliği eklendi
        if (event.getSource() == gosterimAyarlari_btn) {
            gosterimAyarlariShowData();
            gosterimAyarlariDurumList();
            gosterimAyarlariSearch();
        }

        if (event.getSource() == vizyondakiFilmler_btn) {
            vizyondakiFilmlerShowData();
            vizyondakiFilmlerSpinner();
        }

        if (event.getSource() == musteriler_btn) {//muteriler kısmı her basınca güncel veriyi çeker tabloya .
            musterilerShowData();
        }
    }


    //FİLM EKLE KISMI
    // Veritabanındaki tüm filmleri liste olarak çeker

    public ObservableList<filmData> filmEkleList() {
        ObservableList<filmData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM film";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(new filmData(
                        result.getInt("id"),
                        result.getString("filmAdi"),
                        result.getString("filmTuru"),
                        result.getString("filmSuresi"),
                        result.getString("gorsel"),
                        result.getDate("tarih"),
                        result.getString("durum")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // Çekilen film listesini arayüzdeki tabloya doldurur

    public void showfilmEkleList() {
        ObservableList<filmData> list = filmEkleList();
        filmEkle_kln_filmAdi.setCellValueFactory(new PropertyValueFactory<>("filmAdi"));
        filmEkle_kln_filmTuru.setCellValueFactory(new PropertyValueFactory<>("filmTuru"));
        filmEkle_kln_filmSuresi.setCellValueFactory(new PropertyValueFactory<>("filmSuresi"));
        filmEkle_kln_yayinTarihi.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        filmEkle_tablo.setItems(list);
    }

    // Tablodan bir filme tıklandığında bilgileri soldaki kutucuklara doldurur

    public void selectFilmEkleList() {
        filmData fData = filmEkle_tablo.getSelectionModel().getSelectedItem();
        if (fData == null) return;

        updateID = fData.getId();
        filmEkle_filmAdi.setText(fData.getFilmAdi());
        filmEkle_filmTuru.setText(fData.getFilmTuru());
        filmEkle_filmSuresi.setText(fData.getFilmSuresi());
        filmEkle_YayinTarihi.setValue(fData.getTarih().toLocalDate());
        getData.path = fData.getGorsel();

        String uri = "file:" + fData.getGorsel();
        gorsel = new Image(uri, 127, 167, false, true);
        filmEkle_gorsel.setImage(gorsel);
    }

    // Veritabanındaki en yüksek ID'yi bulur

    public void movieId() {
        String sql = "SELECT MAX(id) FROM film";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                getData.movieId = result.getInt("MAX(id)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Yeni film ekleme işlemi

    public void insertFilmEkle() {
        movieId();
        // SQL sorgusuna 'durum' alanı eklendi (6 soru işaretinden 7'ye çıkarıldı)
        String sql = "INSERT INTO film (id, filmAdi, filmTuru, filmSuresi, gorsel, tarih, durum) VALUES(?,?,?,?,?,?,?)";
        connect = database.connectDb();

        try {
            if (filmEkle_filmAdi.getText().isEmpty() || filmEkle_filmTuru.getText().isEmpty() || filmEkle_YayinTarihi.getValue() == null) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen tüm alanları doldurun!");
                return;
            }

            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, getData.movieId + 1);
            prepare.setString(2, filmEkle_filmAdi.getText());
            prepare.setString(3, filmEkle_filmTuru.getText());
            prepare.setString(4, filmEkle_filmSuresi.getText());
            prepare.setString(5, getData.path.replace("\\", "\\\\"));
            prepare.setString(6, String.valueOf(filmEkle_YayinTarihi.getValue()));
            // Yeni eklenen film varsayılan olarak "Vizyonda" durumunda başlar
            prepare.setString(7, "Vizyonda");

            prepare.executeUpdate();

            alertMesaj(Alert.AlertType.INFORMATION, "Yeni film başarıyla eklendi!");
            showfilmEkleList();
            filmEkleTemizle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //FİLM EKLE GÜNCELLE BUTONU
    // Mevcut filmi ID üzerinden günceller

    public void updateFilmEkle() {
        String uri = getData.path.replace("\\", "\\\\");
        String sql = "UPDATE film SET filmAdi = ?, filmTuru = ?, filmSuresi = ?, gorsel = ?, tarih = ? WHERE id = ?";
        connect = database.connectDb();

        try {
            if (updateID == 0) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen tablodan güncellenecek filmi seçin!");
                return;
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, filmEkle_filmAdi.getText());
            prepare.setString(2, filmEkle_filmTuru.getText());
            prepare.setString(3, filmEkle_filmSuresi.getText());
            prepare.setString(4, uri);
            prepare.setString(5, String.valueOf(filmEkle_YayinTarihi.getValue()));
            prepare.setInt(6, updateID);
            prepare.executeUpdate();

            alertMesaj(Alert.AlertType.INFORMATION, "Film bilgileri güncellendi!");
            showfilmEkleList();
            filmEkleTemizle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Seçilen filmi veritabanından tamamen siler

    public void deleteFilmEkle() {
        String sql = "DELETE FROM film WHERE id = ?";
        connect = database.connectDb();
        try {
            if (updateID == 0) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen silinecek filmi seçin!");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Silme Onayı");
            alert.setContentText("Bu filmi silmek istediğinize emin misiniz?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, updateID);
                prepare.executeUpdate();

                showfilmEkleList();
                filmEkleTemizle();
                alertMesaj(Alert.AlertType.INFORMATION, "Film başarıyla silindi.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Bilgisayardan görsel seçer

    public void importImage() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Resim Dosyaları", "*png", "*jpg", "*jpeg"));
        java.io.File file = open.showOpenDialog(filmEkle_form.getScene().getWindow());
        if (file != null) {
            getData.path = file.getAbsolutePath();
            gorsel = new Image(file.toURI().toString(), 127, 167, false, true);
            filmEkle_gorsel.setImage(gorsel);
        }
    }

    // Formdaki tüm giriş alanlarını sıfırlar

    public void filmEkleTemizle() {
        filmEkle_filmAdi.setText("");
        filmEkle_filmTuru.setText("");
        filmEkle_filmSuresi.setText("");
        filmEkle_gorsel.setImage(null);
        filmEkle_YayinTarihi.setValue(null);
        updateID = 0;
    }

    // Arama çubuğu için filtreleme fonksiyonu//

    public void searchFilmEkle() {
        if (filmEkle_Search == null) return;

        ObservableList<filmData> list = filmEkleList();
        FilteredList<filmData> filter = new FilteredList<>(list, e -> true);

        filmEkle_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateFilmData -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String searchKey = newValue.toLowerCase();
                if (predicateFilmData.getFilmAdi().toLowerCase().contains(searchKey)) return true;
                else if (predicateFilmData.getFilmTuru().toLowerCase().contains(searchKey)) return true;
                else return false;
            });
        });

        SortedList<filmData> sortData = new SortedList<>(filter);
        sortData.comparatorProperty().bind(filmEkle_tablo.comparatorProperty());
        filmEkle_tablo.setItems(sortData);
    }

    // --- GÖSTERİM AYARLARI FONKSİYONLARI ---

    public void gosterimAyarlariDurumList() {
        List<String> listD = new ArrayList<>();
        for (String data : gosterimList) {
            listD.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listD);
        gosterimAyarlari_durum.setItems(listData);
    }

    public void gosterimAyarlariShowData() {
        ObservableList<filmData> list = filmEkleList();
        gosterimAyarlari_kln_filmAdi.setCellValueFactory(new PropertyValueFactory<>("filmAdi"));
        gosterimAyarlari_kln_filmTuru.setCellValueFactory(new PropertyValueFactory<>("filmTuru"));
        gosterimAyarlari_kln_filmSuresi.setCellValueFactory(new PropertyValueFactory<>("filmSuresi"));
        gosterimAyarlari_kln_durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        gosterimAyarlari_tablo.setItems(list);
    }

    public void gosterimAyarlariSelect() {
        filmData fData = gosterimAyarlari_tablo.getSelectionModel().getSelectedItem();
        if (fData == null) return;

        updateID = fData.getId();
        gosterimAyarlari_filmAdi.setText(fData.getFilmAdi());

        String uri = "file:" + fData.getGorsel();
        gorsel = new Image(uri, 200, 200, false, true);
        gosterimAyarlari_imageView.setImage(gorsel);
    }
//VİZYONDA  veya VİZYONDAN KALDIR KISMI
    public void gosterimAyarlariGuncelle() {
        String sql = "UPDATE film SET durum = ? WHERE id = ?";
        connect = database.connectDb();
        try {
            if (updateID == 0 || gosterimAyarlari_durum.getSelectionModel().getSelectedItem() == null) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen önce bir film ve durum seçin!");
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, gosterimAyarlari_durum.getSelectionModel().getSelectedItem());
                prepare.setInt(2, updateID);
                prepare.executeUpdate();
                alertMesaj(Alert.AlertType.INFORMATION, "Durum başarıyla güncellendi!");
                gosterimAyarlariShowData();
                gosterimAyarlariTemizle(); // Güncelleme sonrası paneli temizler
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Yeni eklenen Silme fonksiyonu
    public void gosterimAyarlariSil() {
        String sql = "DELETE FROM film WHERE id = ?";
        connect = database.connectDb();
        try {
            if (updateID == 0) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen silinecek filmi seçin!");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Silme Onayı");
            alert.setContentText("Bu filmi tamamen silmek istediğinize emin misiniz?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, updateID);
                prepare.executeUpdate();
                alertMesaj(Alert.AlertType.INFORMATION, "Film başarıyla silindi!");
                gosterimAyarlariShowData();
                gosterimAyarlariTemizle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gösterim Ayarları paneli için temizleme fonksiyonu
    public void gosterimAyarlariTemizle() {
        updateID = 0;
        gosterimAyarlari_filmAdi.setText("");
        gosterimAyarlari_imageView.setImage(null);
        gosterimAyarlari_durum.getSelectionModel().clearSelection();
    }

    public void gosterimAyarlariSearch() {
        FilteredList<filmData> filter = new FilteredList<>(filmEkleList(), e -> true);
        gosterimAyarlari_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(p -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return p.getFilmAdi().toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<filmData> sort = new SortedList<>(filter);
        sort.comparatorProperty().bind(gosterimAyarlari_tablo.comparatorProperty());
        gosterimAyarlari_tablo.setItems(sort);
    }



    //vizyondaki filmler
    private int mId;
    private filmData secilenFilm;
    private double toplamOdeme;
    private double vFiyat;
    private double sFiyat;
    private int vAdet;
    private int sAdet;

    // Vizyondaki filmleri listeler (Sadece "Vizyonda" olanlar)
    public ObservableList<filmData> vizyondakiFilmlerListData() {
        ObservableList<filmData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM film WHERE durum = 'Vizyonda'";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(new filmData(result.getInt("id"), result.getString("filmAdi"),
                        result.getString("filmTuru"), result.getString("filmSuresi"),
                        result.getString("gorsel"), result.getDate("tarih"), result.getString("durum")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return listData;
    }

    public void vizyondakiFilmlerShowData() {
        ObservableList<filmData> list = vizyondakiFilmlerListData();
        vizyondakiFilmler_kln_filmAdi.setCellValueFactory(new PropertyValueFactory<>("filmAdi"));
        vizyondakiFilmler_kln_filmTuru.setCellValueFactory(new PropertyValueFactory<>("filmTuru"));
        vizyondakiFilmler_kln_yayinTarihi.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        vizyondakiFilmler_tablo.setItems(list);
    }

    @FXML
    public void vizyondakiFilmlerTableSelect() {
        // 1. Tablodan seçilen filmi al
        secilenFilm = vizyondakiFilmler_tablo.getSelectionModel().getSelectedItem();

        if (secilenFilm != null) {
            // 2. SADECE üstteki kutucukları doldur
            vizyondakiFilmler_filmAdi.setText(secilenFilm.getFilmAdi());
            vizyondakiFilmler_filmTuru.setText(secilenFilm.getFilmTuru());
            vizyondakiFilmler_tarih.setText(String.valueOf(secilenFilm.getTarih()));
        }
    }

    @FXML
    public void vizyondakiFilmlerListeleButon() {
        // Eğer tabloda bir şey seçili değilse uyarı ver
        if (secilenFilm == null) {
            alertMesaj(Alert.AlertType.WARNING, "Lütfen önce tablodan bir film seçin!");
            return;
        }

        // 1. Aşağıdaki bilet satın alma alanındaki "Label" ve "Görseli" güncelle
        // Not: Alttaki Label'ın fx:id'si 'vizyondakiFilmler_filmAdiLabel' gibi bir şey olmalı.
        // Eğer bilet kısmındaki isim label'ı farklıysa ismini ona göre düzelt.

        String uri = "file:" + secilenFilm.getGorsel();
        gorsel = new Image(uri, 130, 190, false, true);
        vizyondakiFilmler_gorsel.setImage(gorsel);

        vizyondakiFilmler_baslik.setText(secilenFilm.getFilmAdi());//filmin adınıda yazar

        // 2. Fiyatları ve adetleri sıfırla (Yeni film seçildiği için)
        vFiyat = 0; sFiyat = 0; toplamOdeme = 0;
        vizyondakiFilmler_vipFiyat.setText("0.0 TL");
        vizyondakiFilmler_standartFiyat.setText("0.0 TL");
        vizyondakiFilmler_toplam.setText("0.0 TL");

        // Spinnerları sıfırla
        vizyondakiFilmlerSpinner();
    }

    // Spinner değerlerini ve fiyatı ayarlar
    private SpinnerValueFactory<Integer> spinnerVip;
    private SpinnerValueFactory<Integer> spinnerStandart;

    public void vizyondakiFilmlerSpinner() {
        spinnerVip = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinnerStandart = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        vizyondakiFilmler_vipAdet.setValueFactory(spinnerVip);
        vizyondakiFilmler_standartAdet.setValueFactory(spinnerStandart);
    }

    public void vizyondakiFilmlerToplamFiyat() {
        vAdet = vizyondakiFilmler_vipAdet.getValue();
        sAdet = vizyondakiFilmler_standartAdet.getValue();

        vFiyat = (vAdet * 400.0);
        sFiyat = (sAdet * 200.0);
        toplamOdeme = vFiyat + sFiyat;

        // Metni formatlayarak yazıyoruz
        vizyondakiFilmler_vipFiyat.setText(String.format("%.1f TL", vFiyat));
        vizyondakiFilmler_standartFiyat.setText(String.format("%.1f TL", sFiyat));
        vizyondakiFilmler_toplam.setText(String.format("%.1f TL", toplamOdeme));

        // Label kesilme sorunu çözümü//sıkıştırma labelleri sığması iççin
        vizyondakiFilmler_vipFiyat.setMinWidth(Region.USE_PREF_SIZE);
        vizyondakiFilmler_standartFiyat.setMinWidth(Region.USE_PREF_SIZE);
        vizyondakiFilmler_toplam.setMinWidth(Region.USE_PREF_SIZE);
    }

    public void musteriIdAl() {
        String sql = "SELECT MAX(musteri_id) FROM musteri";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                mId = result.getInt("MAX(musteri_id)");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }



    public void vizyondakiFilmlerSatinAl() {
        musteriIdAl();

        String sqlMusteri = "INSERT INTO musteri (musteri_id, film_adi, toplam_fiyat, tarih, saat) VALUES(?,?,?,?,?)";
        String sqlInfo = "INSERT INTO musteri_info (musteri_id, tur, toplam_fiyat, film_adi, tarih, saat) VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            if (toplamOdeme == 0) {
                alertMesaj(Alert.AlertType.ERROR, "Lütfen bilet adeti seçin!");
                return;
            }

            LocalTime localTime = LocalTime.now();
            Time saat = Time.valueOf(localTime);
            java.sql.Date bugun = java.sql.Date.valueOf(java.time.LocalDate.now());

            // 1. Ana Musteri Tablosuna Kayıt
            prepare = connect.prepareStatement(sqlMusteri);
            prepare.setInt(1, mId + 1);
            prepare.setString(2, vizyondakiFilmler_filmAdi.getText());
            prepare.setDouble(3, toplamOdeme);
            prepare.setDate(4, bugun);
            prepare.setTime(5, saat);

            // İşlemi yürütüyoruz
            prepare.executeUpdate();

            // 2. Musteri Detay (Info) Kayıtları
            if (vAdet > 0) {
                ekleMusteriDetay(sqlInfo, mId + 1, "VIP", vFiyat, bugun, saat);
            }

            if (sAdet > 0) {
                ekleMusteriDetay(sqlInfo, mId + 1, "Standart", sFiyat, bugun, saat);
            }

            // --- BAŞARI MESAJI VE GÜNCELLEME ---
            // İşlem buraya kadar geldiyse hata almamış demektir.
            alertMesaj(Alert.AlertType.INFORMATION, "Bilet başarıyla alındı!");

            // Dashboard sayılarını anında yeniler
            dashboardDisplayData();

            // Müşteriler tablosu açıksa orayı da yeniler
            musterilerShowData();

            // Formu temizle (Opsiyonel: Satın aldıktan sonra ekranı sıfırlar)
            vizyondakiFilmlerTemizle();

        } catch (Exception e) {
            e.printStackTrace();
            alertMesaj(Alert.AlertType.ERROR, "Bir hata oluştu: " + e.getMessage());
        }
    }

        private void ekleMusteriDetay(String sql, int id, String tur, double fiyat, java.sql.Date tarih, Time saat) throws Exception {
            prepare = connect.prepareStatement(sql);

            prepare.setInt(1, id);
            prepare.setString(2, tur);
            prepare.setDouble(3, fiyat);
            prepare.setString(4, vizyondakiFilmler_filmAdi.getText());
            prepare.setDate(5, tarih);
            prepare.setTime(6,saat);
        prepare.executeUpdate();
    }

    public void vizyondakiFilmlerTemizle() {
        vizyondakiFilmler_filmAdi.setText("");
        vizyondakiFilmler_filmTuru.setText("");
        vizyondakiFilmler_tarih.setText("");
        vizyondakiFilmler_gorsel.setImage(null);
        spinnerVip = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinnerStandart = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        vizyondakiFilmler_vipAdet.setValueFactory(spinnerVip);
        vizyondakiFilmler_standartAdet.setValueFactory(spinnerStandart);
        vizyondakiFilmler_vipFiyat.setText("0.0 TL");
        vizyondakiFilmler_standartFiyat.setText("0.0 TL");
        vizyondakiFilmler_toplam.setText("0.0 TL");
    }


    //yazdır butonu chatten alındı
    public void vizyondakiFilmlerYazdir() {

        try {

            if (toplamOdeme == 0 || vizyondakiFilmler_filmAdi.getText().isEmpty()) {

                alertMesaj(Alert.AlertType.ERROR, "Lütfen önce bir bilet satın alın!");
                return;
            }

            String dosyaAdi = "Bilet_Makbuz_" + mId + ".txt";

            java.io.PrintWriter writer = new java.io.PrintWriter(dosyaAdi, "UTF-8");

            writer.println("********** SİNEMA BİLETİ MAKBUZU **********");
            writer.println("Müşteri ID: " + (mId + 1));
            writer.println("Film: " + vizyondakiFilmler_filmAdi.getText());
            writer.println("Tarih: " + java.time.LocalDate.now());

            writer.println("------------------------------------------");

            if (vAdet > 0) {
                writer.println("VIP Bilet (" + vAdet + " adet): " + vFiyat + " TL");
            }

            if (sAdet > 0) {
                writer.println("Standart Bilet (" + sAdet + " adet): " + sFiyat + " TL");
            }

            writer.println("------------------------------------------");
            writer.println("TOPLAM TUTAR: " + toplamOdeme + " TL");
            writer.println("******************************************");

            writer.close();

            alertMesaj(Alert.AlertType.INFORMATION,
                    "Bilet makbuzu oluşturuldu: " + dosyaAdi);

        } catch (Exception e) {

            e.printStackTrace();

            alertMesaj(Alert.AlertType.ERROR,
                    "Yazdırma hatası oluştu!");
        }
    }


    //Musteriler formu kısmı

    // Veritabanından verileri çekip listeye atar
    public ObservableList<musterilerData> musterilerListData() {
        ObservableList<musterilerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM musteri";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(new musterilerData(
                        result.getInt("musteri_id"),
                        result.getString("film_adi"),
                        result.getDouble("toplam_fiyat"),
                        result.getDate("tarih"),
                        result.getTime("saat")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return listData;
    }


    // Listeyi arayüzdeki tabloya bağlar
    public void musterilerShowData() {//sqlden çeker gösterir veriler
        ObservableList<musterilerData> list = musterilerListData();

        // fx:id'ler ile musterilerData sınıfındaki değişken isimlerini eşleştiriyoruz
        // Not: musterilerData sınıfında getMusteriId(), getFilmAdi() gibi metodların olduğundan emin ol
        musteriler_kln_biletNumarasi.setCellValueFactory(new PropertyValueFactory<>("musteriId"));
        musteriler_kln_filmAdi.setCellValueFactory(new PropertyValueFactory<>("filmAdi"));
        musteriler_kln_islemTarihi.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        musteriler_kln_islemSaati.setCellValueFactory(new PropertyValueFactory<>("saat"));

        // Tabloya listeyi gönderiyoruz
        musteriler_tablo.setItems(list);
    }
    public void musterilerSelectData() {// tablo içindeki veriler sol tarfda kutucuklar içinde çıkmasını sağlar.
        // 1. Tablodan seçilen satırı al
        musterilerData mData = musteriler_tablo.getSelectionModel().getSelectedItem();

        // 2. Eğer seçim boşsa (hata almamak için) metoddan çık
        if (mData == null) {
            return;
        }

        // 3. Soldaki kutucukları (TextField) doldur
        // Not: Bu fx:id'ler senin Scene Builder'da tanımladığın isimlerle aynıdır
        musteriler_biletNo.setText(String.valueOf(mData.getMusteriId()));
        musteriler_filmAdi.setText(mData.getFilmAdi());
        musteriler_satisTarihi.setText(String.valueOf(mData.getTarih()));
        musteriler_satisSaati.setText(String.valueOf(mData.getSaat()));
    }
//musteri kısmı için temizle butonu .
public void musterilerTemizle() {
    musteriler_biletNo.setText("");
    musteriler_filmAdi.setText("");
    musteriler_satisTarihi.setText("");
    musteriler_satisSaati.setText("");

    // Tablodaki seçimi de kaldırır (Mavi şerit gider)
    musteriler_tablo.getSelectionModel().clearSelection();
}
//musteri kısmı için sil butonu bu verileri siler veritabından
public void musterilerSil() {
    // 1. Veritabanı sorgusu (musteri_id'ye göre siler)
    String sql = "DELETE FROM musteri WHERE musteri_id = ?";
    connect = database.connectDb();

    try {
        // 2. Tablodan bir şey seçili mi kontrol et
        musterilerData mData = musteriler_tablo.getSelectionModel().getSelectedItem();

        if (mData == null) {
            alertMesaj(Alert.AlertType.ERROR, "Lütfen silmek istediğiniz müşteriyi tablodan seçin!");
            return;
        }

        // 3. Kullanıcıya "Emin misin?" diye sor (Yanlışlıkla silmeyi önler)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Silme Onayı");
        alert.setHeaderText(null);
        alert.setContentText("Bu müşteri kaydını silmek istediğinize emin misiniz?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, mData.getMusteriId());
            prepare.executeUpdate();

            alertMesaj(Alert.AlertType.INFORMATION, "Müşteri kaydı başarıyla silindi!");

            // 4. Tabloyu yenile ve kutucukları boşalt
            musterilerShowData();
            musterilerTemizle();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @FXML
    public void musterilerSearch() {
        // 1. Veritabanından listeyi çek
        ObservableList<musterilerData> list = musterilerListData();

        // 2. Filtreleme yapılacak boş bir liste oluştur
        ObservableList<musterilerData> filteredList = FXCollections.observableArrayList();

        // Not: TextField adının 'musteriler_search' olduğundan emin ol
        String searchKey = musteriler_search.getText().toLowerCase();

        // 3. Listeyi dön ve eşleşenleri yeni listeye ekle
        for (musterilerData data : list) {
            if (data.getFilmAdi().toLowerCase().contains(searchKey) ||
                    String.valueOf(data.getMusteriId()).contains(searchKey)) {
                filteredList.add(data);
            }
        }

        // 4. Sadece eşleşenleri tabloda göster
        musteriler_tablo.setItems(filteredList);
    }

//dashboard kısmı için gereken kod blogu
public void dashboardDisplayData() {
    String sql1 = "SELECT COUNT(id) FROM musteri";
    String sql2 = "SELECT SUM(toplam_fiyat) FROM musteri";
    String sql3 = "SELECT COUNT(id) FROM film WHERE durum = 'Vizyonda'";

    connect = database.connectDb();

    try {
        // 1. Toplam Bilet
        prepare = connect.prepareStatement(sql1);
        result = prepare.executeQuery();
        if (result.next()) {
            dashboard_ToplamSatilanBilet.setText(result.getString("COUNT(id)"));
        }

        // 2. Toplam Ciro
        prepare = connect.prepareStatement(sql2);
        result = prepare.executeQuery();
        if (result.next()) {
            dashboard_ToplamCiro.setText(result.getString("SUM(toplam_fiyat)") + " TL");
        }

        // 3. Mevcut Filmler
        prepare = connect.prepareStatement(sql3);
        result = prepare.executeQuery();
        if (result.next()) {
            dashboard_MevcutFilmler.setText(result.getString("COUNT(id)"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}







private void alertMesaj(Alert.AlertType tip, String mesaj) {
        Alert alert = new Alert(tip);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }


    //DASHBOADDA İSİM GÖSTER
    public void usernameGoster() {
        username.setText(getData.username);
    }

    public void kapama() {
        System.exit(0);
    }

    public void kucultme() {
        ((Stage) anasayfa_form.getScene().getWindow()).setIconified(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameGoster();
        showfilmEkleList();
        searchFilmEkle();
        gosterimAyarlariShowData();
        gosterimAyarlariDurumList();
        vizyondakiFilmlerShowData();
        vizyondakiFilmlerSpinner();
        musterilerShowData();
        dashboardDisplayData();

    }
}

