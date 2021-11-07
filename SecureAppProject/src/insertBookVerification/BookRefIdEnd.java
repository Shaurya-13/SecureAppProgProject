package insertBookVerification;

public class BookRefIdEnd extends Book{
    public BookRefIdEnd(String message) {
        super(message);
    }

    @Override
    public boolean confirmBookRefId(String refId1, String refId2) {
        Character ch;
        boolean endingChar;
        ch=refId1.charAt(refId1.length()-1);
        if(ch>='0'&&ch<='9'){
            endingChar=false;
        }
        else{
            endingChar=true;
        }
        return endingChar;
    }
}
