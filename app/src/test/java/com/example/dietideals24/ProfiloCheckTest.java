package com.example.dietideals24;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.dietideals24.models.ProfiloCheck;

public class ProfiloCheckTest {

    @Test
    public void testCampiVuoti() {
        ProfiloCheck profilo = new ProfiloCheck("", "");
        assertFalse(profilo.check());
    }

    @Test
    public void testNicknameVuotoPasswordCorretta() {
        ProfiloCheck profilo = new ProfiloCheck("", "password");
        assertFalse(profilo.check());
    }

    @Test
    public void testNicknameVuotoPasswordLunga() {
        ProfiloCheck profilo = new ProfiloCheck("", "PasswordTroppoLungaCheSuperaILLimiteDi50CaratteriBlaBlaBla");
        assertFalse(profilo.check());
    }

    @Test
    public void testPasswordVuotaNicknameCorretto() {
        ProfiloCheck profilo = new ProfiloCheck("nickname", "");
        assertFalse(profilo.check());
    }

    @Test
    public void testPasswordVuotaNicknameLungo() {
        ProfiloCheck profilo = new ProfiloCheck("NicknameTroppoLungoCheSuperaILLimiteDi30Caratteri", "");
        assertFalse(profilo.check());
    }

    @Test
    public void testNicknameTroppoLungo() {
        ProfiloCheck profilo = new ProfiloCheck("NicknameTroppoLungoCheSuperaILLimiteDi30Caratteri", "password");
        assertFalse(profilo.check());
    }

    @Test
    public void testNicknameLungoPasswordLunga() {
        ProfiloCheck profilo = new ProfiloCheck("NicknameTroppoLungoCheSuperaILLimiteDi30Caratteri", "PasswordTroppoLungaCheSuperaILLimiteDi50CaratteriBlaBlaBla");
        assertFalse(profilo.check());
    }

    @Test
    public void testPasswordTroppoLunga() {
        ProfiloCheck profilo = new ProfiloCheck("nickname", "PasswordTroppoLungaCheSuperaILLimiteDi50CaratteriBlaBlaBla");
        assertFalse(profilo.check());
    }

    @Test
    public void testInputValido() {
        ProfiloCheck profilo = new ProfiloCheck("NicknameValido", "PasswordValida");
        assertTrue(profilo.check());
    }

    @Test
    public void testInputLimite() {
        // Verifica il comportamento al limite dei caratteri consentiti
        String maxNickname = "a".repeat(30);
        String maxPassword = "a".repeat(50);
        ProfiloCheck profilo = new ProfiloCheck(maxNickname, maxPassword);
        assertTrue(profilo.check());
    }

    @Test
    public void testNicknameEsattoLimite() {
        // Verifica il comportamento con un nickname di lunghezza esatta (30 caratteri)
        String exactLengthNickname = "a".repeat(30);
        ProfiloCheck profilo = new ProfiloCheck(exactLengthNickname, "password");
        assertTrue(profilo.check());
    }

    @Test
    public void testPasswordLimiteInferiore() {
        // Verifica il comportamento con una password appena al di sotto del limite (49 caratteri)
        String belowMaxLengthPassword = "a".repeat(49);
        ProfiloCheck profilo = new ProfiloCheck("nickname", belowMaxLengthPassword);
        assertTrue(profilo.check());
    }

    @Test
    public void testPasswordLimiteSuperiore() {
        // Verifica il comportamento con una password appena al di sopra del limite (51 caratteri)
        String aboveMaxLengthPassword = "a".repeat(51);
        ProfiloCheck profilo = new ProfiloCheck("nickname", aboveMaxLengthPassword);
        assertFalse(profilo.check());
    }

    @Test
    public void testInputLimiteSuperiore() {
        // Verifica il comportamento con un nickname al limite e una password appena al di sopra del limite
        String maxNickname = "a".repeat(30);
        String aboveMaxLengthPassword = "a".repeat(51);
        ProfiloCheck profilo = new ProfiloCheck(maxNickname, aboveMaxLengthPassword);
        assertFalse(profilo.check());
    }

}

