package insertBookVerification;

public class BookRefIdSimilar extends Book{

    public BookRefIdSimilar(String message) {
        super(message);
    }

    @Override
    public boolean confirmBookRefId(String refId1, String refId2) {
        boolean similar;
        if(refId1.equals(refId2)){
            similar=false;
        }
        else{
            similar=true;
        }
        return similar;
    }
}
