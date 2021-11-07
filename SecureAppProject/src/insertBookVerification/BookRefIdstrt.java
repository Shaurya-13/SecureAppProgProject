package insertBookVerification;

public class BookRefIdstrt extends Book {
    public BookRefIdstrt(String message) {
        super(message);
    }

    @Override
    public boolean confirmBookRefId(String refId1, String refId2) {
        Character ch;
        String specialCharacterString="!~@#$&*?";
        boolean startingChar;
        ch=refId1.charAt(0);
        if(specialCharacterString.contains(Character.toString(ch))){
            startingChar=false;
        }
        else{
            startingChar=true;
        }
        return startingChar;
    }
}
