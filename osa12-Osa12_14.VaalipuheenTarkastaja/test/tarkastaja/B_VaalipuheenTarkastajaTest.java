package tarkastaja;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

@Points("12-14.2")
public class B_VaalipuheenTarkastajaTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {

            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
            System.setProperty("java.awt.headless", "false");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        VaalipuheenTarkastaja sovellus = new VaalipuheenTarkastaja();

        try {
            Application app = Application.class.cast(sovellus);
        } catch (Throwable t) {
            fail("Periihän luokka VaalipuheenTarkastaja luokan Application.");
        }

        try {
            Reflex.reflect(VaalipuheenTarkastaja.class).method("init").returningVoid().takingNoParams().invokeOn(sovellus);
        } catch (Throwable ex) {
            fail("Virhe kutsuttaessa luokan VaalipuheenTarkastaja metodia init. Virhe: " + ex.getMessage());
        }
        
        try {
            Reflex.reflect(VaalipuheenTarkastaja.class).method("start").returningVoid().taking(Stage.class).invokeOn(sovellus, stage);
        } catch (Throwable ex) {
            fail("Virhe kutsuttaessa luokan VaalipuheenTarkastaja metodi start. Virhe: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void valintaKylla1() {
        syotaJaTarkista("laajat verkostot", "Sanojen esiintymien perusteella: Kyllä");
    }
    
    @Test
    public void valintaKylla2() {
        syotaJaTarkista("valtuutettu kaupunginvaltuutettuna", "Sanojen esiintymien perusteella: Kyllä");
    }
    
    @Test
    public void valintaEi1() {
        syotaJaTarkista("olen valmis", "Sanojen esiintymien perusteella: Ei");
    }
    
    @Test
    public void valintaEi2() {
        syotaJaTarkista("muutan kaiken", "Sanojen esiintymien perusteella: Ei");
    }
    
    @Test
    public void valintaKysymys1() {
        syotaJaTarkista("", "Sanojen esiintymien perusteella: ???");
    }
    
    @Test
    public void valintaKysymys2() {
        syotaJaTarkista("ostakkee perunoita", "Sanojen esiintymien perusteella: ???");
    }


    private void syotaJaTarkista(String syotettava, String odotettu) {
        Scene scene = stage.getScene();
        assertNotNull("Stage-oliolla pitäisi olla Scene-olio. Nyt start-metodin suorituksen jälkeen stagelle tehty kutsu getScene palautti null-viitteen.", scene);
        Parent elementtienJuuri = scene.getRoot();
        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu olio (tässä BorderPane). Nyt Scene-oliolta ei löytynyt komponentteja sisältävää oliota.", elementtienJuuri);

        BorderPane borderPane = null;
        try {
            borderPane = BorderPane.class.cast(elementtienJuuri);
        } catch (Throwable t) {
            fail("Käytäthän käyttöliittymäkomponenttien asetteluun BorderPane-luokkaa.");
        }

        assertNotNull("Scene-oliolle tulee antaa parametrina käyttöliittymäkomponenttien asetteluun tarkoitettu BorderPane-olio.", borderPane);
        assertTrue("BorderPanessa tulee olla asetettuna keskiosaan TextArea-olio. Nyt keskellä oli: " + borderPane.getCenter(), borderPane.getCenter() != null && borderPane.getCenter().getClass().isAssignableFrom(TextArea.class));
        assertTrue("BorderPanessa tulee olla asetettuna alaosaan VBox-olio. Nyt alaosassa oli: " + borderPane.getBottom(), borderPane.getBottom() != null && borderPane.getBottom().getClass().isAssignableFrom(VBox.class));

        clickOn(borderPane.getCenter());
        write(syotettava);

        VBox box = (VBox) borderPane.getBottom();
        assertEquals("Odotettiin, että alalaidassa on kaksi tekstielementtiä. Nyt elementtejä oli: " + box.getChildren().size(), 2, box.getChildren().size());

        List<Node> tekstielementit = box.getChildren();
        for (Node node : tekstielementit) {
            assertTrue("VBox-oliolle lisätyt elementit pitäisi olla Label-elementtejä. Nyt eivät olleet. Löytyi: " + node, node.getClass().isAssignableFrom(Label.class));
        }

        assertEquals("Kun syötetty \"" + syotettava + "\", odotettiin että ensimmäisen tekstielementin teksti on \"" + odotettu + "\".\nNyt teksti oli: \"" + ((Label) tekstielementit.get(0)).getText() + "\".", odotettu, ((Label) tekstielementit.get(0)).getText());
    }

}
