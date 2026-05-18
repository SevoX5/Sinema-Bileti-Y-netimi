# 🎬 Sinema Bilet Rezervasyon Yönetim Sistemi

Bu proje; ekip arkadaşlarım Kadir, Eray ve Yusuf ile birlikte geliştirdiğimiz, **JavaFX** mimarisi ve **MySQL** veritabanı entegrasyonu üzerine kurulu, backend odaklı bir masaüstü otomasyon uygulamasıdır. 

Projenin temel amacı; bir sinema salonunun film yönetim süreçlerini (CRUD), vizyon ayarlarını, bilet satış operasyonlarını ve müşteri kayıtlarını tek bir panelden dinamik olarak yönetmektir. Bu süreçte ekip olarak **Git & GitHub** üzerinde ortak çalışma (branch yönetimi, conflict çözümü) pratiklerini derinlemesine deneyimledik.

---

## 🚀 Öne Çıkan Özellikler

* **Dinamik Film Yönetimi (CRUD):** Sisteme yeni film ekleme, silme, güncelleme ve afiş/görsel yükleme (`FileChooser` entegrasyonu) işlemleri.
* **Vizyon ve Gösterim Ayarları:** Filmlerin "Vizyonda" veya "Vizyondan Kalktı" durumlarını anlık güncelleme; vizyondaki filmleri bilet satış ekranına otomatik yansıtma.
* **Gelişmiş Bilet Satış Mantığı:** VIP ve Standart bilet tiplerine göre anlık fiyat hesaplama, bilet adetlerini `Spinner` bileşenleri ile kontrol etme.
* **Fiziksel Makbuz Üretimi:** Başarılı bilet satış işlemlerinin ardından, müşteriye özel detayları içeren `.txt` formatında dinamik bilet makbuzu oluşturma.
* **Canlı İstatistikler (Dashboard):** Satılan toplam bilet sayısı, toplam ciro ve mevcut aktif film sayılarını veritabanından anlık çekerek gösteren analitik panel.
* **Anlık Arama ve Filtreleme:** `FilteredList` ve `SortedList` mimarisi kullanılarak tablolar üzerinde harf duyarlı, gecikmesiz arama fonksiyonu.

---

## 🛠️ Kullanılan Teknolojiler

* **Dil:** Java (JDK 20)
* **Arayüz Framework:** JavaFX & FXML
* **Veritabanı:** MySQL / XAMPP (phpMyAdmin)
* **Geliştirme Araçları:** IntelliJ IDEA, Scene Builder, Git & GitHub



