public class Cipher {
    private String TextKey;
    private String CipheredTextKey;
    public Cipher(String TextKey, String CipheredTextKey){
        // Sets private Keys for Deciphering and Ciphering

        this.TextKey = TextKey;
        this.CipheredTextKey = CipheredTextKey;
    }
    public String DecipherString(String InputText) {
        // Takes in Ciphered String, returns Deciphered String

        return "";
    }

    public Boolean ValidateCipherStrings(){
        // Validates that TextKey and CipheredTextKey are valid ciphers (same characters and length)

        return true;
    }

    public String getCipheredTextKey() {
        return CipheredTextKey;
    }

    public String getTextKey() {
        return TextKey;
    }

    public void setTextKey(String textKey) {
        TextKey = textKey;
    }

    public void setCipheredTextKey(String cipheredTextKey) {
        CipheredTextKey = cipheredTextKey;
    }
}
