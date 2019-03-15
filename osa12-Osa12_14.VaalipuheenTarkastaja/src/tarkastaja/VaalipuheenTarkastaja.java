package tarkastaja;

import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VaalipuheenTarkastaja extends Application {
    private Ennustaja ennustaja;
    
    @Override
    public void init() throws Exception {
        this.ennustaja = new Ennustaja();
        
        try {
            Files.lines(Paths.get("vaalidata.csv"))
                    .map(rivi -> rivi.split(";"))
                    .forEach(rivi -> ennustaja.lisaa(rivi[0].equals("1"), rivi[1]));
            
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen epäonnistui.");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        TextArea tekstikentta = new TextArea();
        tekstikentta.setWrapText(true);
        Label esiintymaEnnustaja = new Label("Sanojen esiintymien perusteella: ???");
        Label mystinenEnnustaja = new Label("Mystisen ennustajan perusteella: ???");
        VBox alapalkki = new VBox();
        alapalkki.getChildren().addAll(esiintymaEnnustaja, mystinenEnnustaja);
        alapalkki.setPadding(new Insets(20,20,20,20));
        
        BorderPane komponenttiryhma = new BorderPane();
        komponenttiryhma.setCenter(tekstikentta);    
        komponenttiryhma.setBottom(alapalkki);
        Scene nakyma = new Scene(komponenttiryhma);
        stage.setScene(nakyma);
        stage.show();
        
        tekstikentta.textProperty().addListener((muutos, vanhaArvo, uusiArvo) -> {
            String lause = tekstikentta.getText();
            
            int esiintyminen = ennustaja.valitaanSanojenLukumaariinPerustuen(lause);
            int mystinen = ennustaja.valitaanMystisellaEnnustajalla(lause);
            
            if (esiintyminen == 1){
                esiintymaEnnustaja.setText("Sanojen esiintymien perusteella: Kyllä");
            }
            if (esiintyminen == -1){
                esiintymaEnnustaja.setText("Sanojen esiintymien perusteella: Ei");
            }
            if (esiintyminen == 0){
                esiintymaEnnustaja.setText("Sanojen esiintymien perusteella: ???");
            }            
            
            if (mystinen == 1){
                mystinenEnnustaja.setText("Mystisen ennustajan perusteella: Kyllä");
            }
            if (mystinen == -1){
                mystinenEnnustaja.setText("Mystisen ennustajan perusteella: Ei");
            }
            if (mystinen == 0){
                mystinenEnnustaja.setText("Mystisen ennustajan perusteella: ???");
            }            
        });
    }

    public static void main(String[] args) {
        launch(VaalipuheenTarkastaja.class);
        Ennustaja ennustaja = new Ennustaja();
        
    }
}
