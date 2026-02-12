import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CipherTest {
    private String TextKey = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private String CipheredTextKey = "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a";

    @Test
    void SimpleDecipherTest() {
        Cipher cipher = new Cipher(TextKey,CipheredTextKey);
        assertEquals("abc", cipher.DecipherString("bcd"));
    }

    @Test
    void DecipherNumberTest() {
        Cipher cipher = new Cipher(TextKey,CipheredTextKey);
        assertEquals("123", cipher.DecipherString("234"));
    }

    @Test
    void DecipherEmptyTest() {
        Cipher cipher = new Cipher(TextKey,CipheredTextKey);
        assertEquals("", cipher.DecipherString(""));
    }

    @Test
    void DecipherComplexTest() {
        Cipher cipher = new Cipher(TextKey,CipheredTextKey);
        assertEquals("Carnivore, later renamed DCS1000,", cipher.DecipherString("Dbsojwpsf, mbufs sfobnfe EDT2aaa,"));
    }

    @Test
    void ValidateSameLengthCipherDifferentCharactersTest() {
        Cipher cipher = new Cipher("abc","mqp");
        assertEquals(false, cipher.ValidateCipherStrings());
    }

    @Test
    void ValidateCorrectCipherTest() {
        Cipher cipher = new Cipher(TextKey,CipheredTextKey);
        assertEquals(true, cipher.ValidateCipherStrings());
    }


}
