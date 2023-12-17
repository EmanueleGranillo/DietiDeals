package com.example.dietideals24;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.dietideals24.connection.VincitoreRibassoRequest;

public class VincitoreRibassoRequestTest {

    @Test
    public void testVincitoreVuoto() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("", 1);
        assertEquals("Non c'è un vincitore.", request.validate());
    }

    @Test
    public void testVincitoreVuotoIdZero() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("", 0);
        assertEquals("Non c'è un vincitore.", request.validate());
    }

    @Test
    public void testVincitoreVuotoIdNegativo() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("", -1);
        assertEquals("Non c'è un vincitore.", request.validate());
    }

    @Test
    public void testNomeVincitoreTroppoLungo() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("NomeVincitoreTroppoLungoCheSuperaIlLimiteImpostato", 1);
        assertEquals("Nome non valido.", request.validate());
    }

    @Test
    public void testNomeVincitoreTroppoLungoIdZero() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("NomeVincitoreTroppoLungoCheSuperaIlLimiteImpostato", 0);
        assertEquals("Nome non valido.", request.validate());
    }

    @Test
    public void testNomeVincitoreTroppoLungoIdNegativo() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("NomeVincitoreTroppoLungoCheSuperaIlLimiteImpostato", -1);
        assertEquals("Nome non valido.", request.validate());
    }

    @Test
    public void testIdNegativo() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("Vincitore", -1);
        assertEquals("ID non valido.", request.validate());
    }

    @Test
    public void testIdZero() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("Vincitore", 0);
        assertEquals("I valori sono corretti.", request.validate());
    }

    @Test
    public void testValoriCorretti() {
        VincitoreRibassoRequest request = new VincitoreRibassoRequest("Vincitore", 1);
        assertEquals("I valori sono corretti.", request.validate());
    }

}
