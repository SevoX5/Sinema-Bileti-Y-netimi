package com.example.sinema_bileti_yonetimi;

public class sliderr
{
    import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

    public class FXMLDocumentController implements Initializable {

        @FXML
        private ImageView image1;
        @FXML
        private ImageView image2;
        @FXML
        private ImageView image3;
        @FXML
        private ImageView image4;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            slider();
        }

        public void slider() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            // 1. Resmin gösterimi
                            TranslateTransition slider1 = new TranslateTransition();
                            slider1.setNode(image1);
                            slider1.setDuration(Duration.seconds(3));
                            slider1.setToX(0); // Ekrana gelir
                            slider1.play();

                            TranslateTransition slider1_out = new TranslateTransition();
                            slider1_out.setNode(image2);
                            slider1_out.setDuration(Duration.seconds(3));
                            slider1_out.setToX(600); // Ekran dışına çıkar
                            slider1_out.play();

                            TranslateTransition slider1_out2 = new TranslateTransition();
                            slider1_out2.setNode(image3);
                            slider1_out2.setDuration(Duration.seconds(3));
                            slider1_out2.setToX(600);
                            slider1_out2.play();

                            TranslateTransition slider1_out3 = new TranslateTransition();
                            slider1_out3.setNode(image4);
                            slider1_out3.setDuration(Duration.seconds(3));
                            slider1_out3.setToX(600);
                            slider1_out3.play();

                            Thread.sleep(5000); // 5 saniye bekle

                            // 2. Resmin gösterimi
                            TranslateTransition slider2 = new TranslateTransition();
                            slider2.setNode(image2);
                            slider2.setDuration(Duration.seconds(3));
                            slider2.setToX(0);
                            slider2.play();

                            TranslateTransition slider2_out = new TranslateTransition();
                            slider2_out.setNode(image1);
                            slider2_out.setDuration(Duration.seconds(3));
                            slider2_out.setToX(-600); // Sola kayarak çıkar
                            slider2_out.play();

                            Thread.sleep(5000);

                            // 3. Resmin gösterimi
                            TranslateTransition slider3 = new TranslateTransition();
                            slider3.setNode(image3);
                            slider3.setDuration(Duration.seconds(3));
                            slider3.setToX(0);
                            slider3.play();

                            TranslateTransition slider3_out = new TranslateTransition();
                            slider3_out.setNode(image2);
                            slider3_out.setDuration(Duration.seconds(3));
                            slider3_out.setToX(-600);
                            slider3_out.play();

                            Thread.sleep(5000);

                            // 4. Resmin gösterimi
                            TranslateTransition slider4 = new TranslateTransition();
                            slider4.setNode(image4);
                            slider4.setDuration(Duration.seconds(3));
                            slider4.setToX(0);
                            slider4.play();

                            TranslateTransition slider4_out = new TranslateTransition();
                            slider4_out.setNode(image3);
                            slider4_out.setDuration(Duration.seconds(3));
                            slider4_out.setToX(-600);
                            slider4_out.play();

                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
