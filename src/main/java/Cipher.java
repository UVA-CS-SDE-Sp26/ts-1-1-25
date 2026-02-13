import org.w3c.dom.Text;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

public class Cipher {
    private String TextKey;
    private String CipheredTextKey;
    private HashMap<Character,Character> DecipherHashMap;
    public Cipher(String TextKey, String CipheredTextKey){
        // Sets private Keys for Deciphering and Ciphering

        this.TextKey = TextKey;
        this.CipheredTextKey = CipheredTextKey;

        if (!ValidateCipherStrings()) {
            throw new IllegalArgumentException("Invalid cipher strings");
        }

        DecipherHashMap = new HashMap<>();

        // Fills DecipherHashMap with corresponding deciphered values

        char[] TextKeyArray = TextKey.toCharArray();
        char[] CipheredTextKeyArray = CipheredTextKey.toCharArray();

        for (int i = 0; i < CipheredTextKey.length(); i++) {
            DecipherHashMap.put(CipheredTextKeyArray[i], TextKeyArray[i]);
        }
    }
    public String DecipherString(String CipheredInput) {
        // Takes in Ciphered String, returns Deciphered String
        StringBuilder DecipheredStringBuilder = new StringBuilder();
        for (char c: CipheredInput.toCharArray()) {
            if (DecipherHashMap.containsKey(c)) {
                DecipheredStringBuilder.append(DecipherHashMap.get(c));
            }
            else{
                DecipheredStringBuilder.append(c);
            }
        }

        return DecipheredStringBuilder.toString();
    }

    public Boolean ValidateCipherStrings(){
        // Validates that TextKey and CipheredTextKey are valid ciphers (same characters and length)

        if (TextKey.length() != CipheredTextKey.length()){
            return false;
        }

        char[] TextKeyArray = this.TextKey.toCharArray();
        char[] CipheredTextKey = this.CipheredTextKey.toCharArray();

        Arrays.sort(TextKeyArray);
        Arrays.sort(CipheredTextKey);

        if (!Arrays.equals(TextKeyArray, CipheredTextKey)){
            return false;
        }

        HashSet<Character> seen = new HashSet<>();
        for (char c: TextKeyArray){
            if (seen.contains(c)){
                return false;
            }
            seen.add(c);
        }



        return true;
    }

    public String getCipheredTextKey() {
        return CipheredTextKey;
    }

    public String getTextKey() {
        return TextKey;
    }
}
