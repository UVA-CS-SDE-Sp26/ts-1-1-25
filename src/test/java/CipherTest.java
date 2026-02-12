import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void ValidateSameLengthCipherDifferentCharactersTestException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cipher("abc", "mqp");
        });
    }

    @Test
    void ValidateDifferentLengthCipherThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cipher("abc", "cab3");
        });
    }
    @Test
    void ValidateDuplicateValuesCipherThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cipher("aabc", "aabc");
        });
    }



}
