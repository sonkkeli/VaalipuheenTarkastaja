package tarkastaja;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

@Points("12-14.1")
public class A_EnnustajaTest {

    private Ennustaja ennustaja;

    @Before
    public void setup() {
        this.ennustaja = new Ennustaja();
    }

    @Test
    public void materiaalinEsimerkki() {
        ennustaja.lisaa(true, "minulla on rohkeutta");
        ennustaja.lisaa(true, "olen positiivisesti ajatteleva ja yhteistyökykyinen");
        ennustaja.lisaa(true, "olen valmis valtuustoon");
        ennustaja.lisaa(false, "olen ollut aktiivinen kuntapolitiikassa");
        ennustaja.lisaa(false, "olen ollut yhteiskunnan asioissa aina aktiivinen toimija");
        ennustaja.lisaa(false, "haluan olla valtuustossa");

        assertEquals(0, ennustaja.valitaanSanojenLukumaariinPerustuen("haluan valtuustoon"));
        assertEquals(-1, ennustaja.valitaanSanojenLukumaariinPerustuen("olen aktiivinen toimija"));
        assertEquals(1, ennustaja.valitaanSanojenLukumaariinPerustuen("olen yhteistyökykyinen"));
    }

    @Test
    public void testi1() {
        ennustaja.lisaa(true, "a a a a a a");
        ennustaja.lisaa(false, "a a a a");

        assertEquals(1, ennustaja.valitaanSanojenLukumaariinPerustuen("a"));
    }

    @Test
    public void testi2() {
        ennustaja.lisaa(true, "a a a");
        ennustaja.lisaa(false, "a a a a");

        assertEquals(-1, ennustaja.valitaanSanojenLukumaariinPerustuen("a"));
    }

    @Test
    public void testi3() {
        ennustaja.lisaa(true, "a a");
        ennustaja.lisaa(false, "a a");

        assertEquals(0, ennustaja.valitaanSanojenLukumaariinPerustuen("a"));
    }

    @Test
    public void testi4() {
        ennustaja.lisaa(true, "a");
        ennustaja.lisaa(true, "a");
        ennustaja.lisaa(true, "a");
        ennustaja.lisaa(false, "a a a b");

        assertEquals(0, ennustaja.valitaanSanojenLukumaariinPerustuen("a"));
    }

    @Test
    public void testi5() {
        ennustaja.lisaa(true, "a a a");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");

        assertEquals(0, ennustaja.valitaanSanojenLukumaariinPerustuen("a"));
    }

    @Test
    public void testi6() {
        ennustaja.lisaa(true, "a a a");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");

        assertEquals(-1, ennustaja.valitaanSanojenLukumaariinPerustuen("a b"));
    }

    @Test
    public void testi7() {
        ennustaja.lisaa(true, "a a a b b b b");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");
        ennustaja.lisaa(false, "a b");

        assertEquals(1, ennustaja.valitaanSanojenLukumaariinPerustuen("a b"));
    }
}
