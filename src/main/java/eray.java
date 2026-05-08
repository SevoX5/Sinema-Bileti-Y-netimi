import com.example.sinema_bileti_yonetimi.database;
import javafx.collections.FXCollections;
// 1. Veritabanındaki "movie" tablosundan verileri çekip bir listeye atayan metod
public ObservableList<moviesData> availableMoviesList() {

    ObservableList<moviesData> listMovies = FXCollections.observableArrayList();
    String sql = "SELECT * FROM movie";

    connect = database.connectDb(); // Veritabanı bağlantısı sınıfınız

    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        moviesData movD;

        while (result.next()) {
            // moviesData constructor yapınıza göre ilgili veritabanı sütun isimleri
            movD = new moviesData(result.getInt("id"),
                    result.getString("movieTitle"),
                    result.getString("genre"),
                    result.getString("duration"),
                    result.getString("image"),
                    result.getDate("date"),
                    result.getString("current"));

            listMovies.add(movD);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return listMovies;
}

        private ObservableList<moviesData> availableMoviesList;

        // 2. Çekilen listeyi (ObservableList) TableView'daki sütunlara yansıtan metod
        public void availableMoviesShowListData() {
            availableMoviesList = availableMoviesList();

            // PropertyValueFactory içindeki değerler, moviesData içindeki değişken isimlerinizle eşleşmeli
            availableMovies_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            availableMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            availableMovies_col_showingDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Verileri tabloya yerleştirme
            availableMovies_tableView.setItems(availableMoviesList);
        }

        // 3. Tablodan bir filme tıklandığında (seçildiğinde) bilgileri diğer form alanlarına dolduran metod
        public void availableMoviesSelect() {
            moviesData movD = availableMovies_tableView.getSelectionModel().getSelectedItem();
            int num = availableMovies_tableView.getSelectionModel().getSelectedIndex();

            // Herhangi bir seçim yapılmadıysa hata vermemesi için koruma
            if ((num - 1) < -1) {
                return;
            }

            // Seçilen satırdaki bilgileri TextField ve Label gibi alanlara atama
            availableMovies_movieTitle.setText(movD.getTitle());
            availableMovies_genre.setText(movD.getGenre());
            availableMovies_date.setText(String.valueOf(movD.getDate()));

            // Resim yolunu ve film başlığını diğer formlara aktarmak üzere getData adındaki statik sınıf değişkenine atama
            getData.path = movD.getImage();
            getData.title = movD.getTitle();
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            // Form açıldığında tabloda filmlerin direkt gelmesi için çağırılır
            availableMoviesShowListData();
        }


        public void buyTicketSelectMovie() {
            // Tablodan seçilen satırı moviesData objesi olarak alıyoruz
            moviesData movD = buyTicket_tableView.getSelectionModel().getSelectedItem();
            int num = buyTicket_tableView.getSelectionModel().getSelectedIndex();

            // Seçim kontrolü (Hata önleyici)
            if ((num - 1) < -1) {
                return;
            }

            // Formdaki Label'lara verileri aktarma
            buyTicket_movieTitle.setText(movD.getTitle());
            buyTicket_genre.setText(movD.getGenre());
            buyTicket_date.setText(String.valueOf(movD.getDate()));

            // Resim dosyasının yolunu alıp ImageView'da gösterme
            String uri = "file:" + movD.getImage();
            image = new Image(uri, 134, 171, false, true);
            buyTicket_imageView.setImage(image);

            // Seçilen film bilgilerini veri taşıyıcı (getData) sınıfa kaydetme
            getData.path = movD.getImage();
            getData.title = movD.getTitle();
        }


        private int qty;
        private double total;

        public void buyTicketGetSpinnerValue() {
            // Spinner'dan adet bilgisini al
            qty = buyTicket_spinner.getValue();

            // Sabit bir fiyat (örneğin 10$) üzerinden toplam hesaplama
            double price = 10.0;
            total = (qty * price);

            // Toplamı ekrandaki etikete yansıtma
            buyTicket_total.setText("$" + String.valueOf(total));
        }


        public void buyTicket() {
            // Veritabanı Insert Sorgusu
            String sql = "INSERT INTO customer (movieTitle, genre, quantity, total, date) VALUES(?,?,?,?,?)";

            connect = database.connectDb();

            try {
                if (buyTicket_movieTitle.getText().isEmpty() || qty == 0) {
                    // Boş alan uyarısı
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata");
                    alert.setHeaderText(null);
                    alert.setContentText("Lütfen bir film seçin ve bilet adedini belirleyin.");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, buyTicket_movieTitle.getText());
                    prepare.setString(2, buyTicket_genre.getText());
                    prepare.setString(3, String.valueOf(qty));
                    prepare.setString(4, String.valueOf(total));

                    // Mevcut tarihi alma (java.sql.Date)
                    long millis = System.currentTimeMillis();
                    java.sql.Date date = new java.sql.Date(millis);
                    prepare.setString(5, String.valueOf(date));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Başarılı");
                    alert.setHeaderText(null);
                    alert.setContentText("Bilet başarıyla satın alındı!");
                    alert.showAndWait();

                    // Tabloyu ve alanları sıfırlama
                    buyTicketShowListData();
                    buyTicketClear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
