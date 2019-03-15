package tarkastaja;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

@Points("12-14.3")
public class C_EnnustajaTest {

    private Ennustaja ennustaja;

    @Before
    public void setup() {
        this.ennustaja = new Ennustaja();
    }

    @Test
    public void materiaalinEsimerkki() {
        testidata();

        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("???"));
        assertEquals(1, ennustaja.valitaanMystisellaEnnustajalla("a"));
        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("b"));
        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("a b c"));
    }

    @Test
    public void testi1() {
        testidata();

        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("a c"));
    }

    @Test
    public void testi2() {
        testidata();

        assertEquals(1, ennustaja.valitaanMystisellaEnnustajalla("a a c"));
    }

    @Test
    public void testi3() {
        testidata();

        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("d d d d"));
    }

    @Test
    public void testi4() {
        testidata();

        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla(""));
    }

    @Test
    public void testi5() {
        for (int i = 0; i < 5; i++) {
            ennustaja.lisaa(true, "a b");
            ennustaja.lisaa(false, "a b");
        }

        assertEquals(0, ennustaja.valitaanMystisellaEnnustajalla("a b"));
    }

    @Test
    public void testi6() {
        for (int i = 0; i < 5; i++) {
            ennustaja.lisaa(true, "a b");
            ennustaja.lisaa(false, "a");
            ennustaja.lisaa(false, "b");
        }

        assertEquals(-1, ennustaja.valitaanMystisellaEnnustajalla("a b"));
    }

    @Test
    public void testi7() {
        for (int i = 0; i < 5; i++) {
            ennustaja.lisaa(true, "a");
            ennustaja.lisaa(true, "b");
            ennustaja.lisaa(false, "a b");

        }

        assertEquals(1, ennustaja.valitaanMystisellaEnnustajalla("a b"));
    }

    @Test
    public void testi8() {
        for (int i = 0; i < 5; i++) {
            ennustaja.lisaa(true, "a b");
            ennustaja.lisaa(false, "a b");

        }

        assertEquals(0, ennustaja.valitaanMystisellaEnnustajalla("a"));
    }

    public void testidata() {
        for (int i = 0; i < 5; i++) {
            ennustaja.lisaa(true, "a b");
            ennustaja.lisaa(true, "a c d");
            ennustaja.lisaa(true, "b c");
            ennustaja.lisaa(true, "a a");

            ennustaja.lisaa(false, "c c d");
            ennustaja.lisaa(false, "a b d");
            ennustaja.lisaa(false, "a b");
            ennustaja.lisaa(false, "c d");
            ennustaja.lisaa(false, "c d d d");
        }
    }
}
