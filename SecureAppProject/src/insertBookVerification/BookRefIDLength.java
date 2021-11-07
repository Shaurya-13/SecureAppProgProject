package insertBookVerification;

public class BookRefIDLength extends Book{

    public BookRefIDLength(String message) {
        super(message);
    }

    @Override
    public boolean confirmBookRefId(String refId1, String refId2) {
        boolean length;
        if(refId1.length()>6){
            length=false;
        }
        else {
            length = true;
        }
        return length;
    }
}
