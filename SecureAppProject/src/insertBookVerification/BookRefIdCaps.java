package insertBookVerification;

public class BookRefIdCaps extends Book{
    public BookRefIdCaps(String message) {
        super(message);
    }

    @Override
    public boolean confirmBookRefId(String refId1, String refId2) {
        boolean allUpper = false;
        Character ch;
        for(int i=0;i<refId1.length();i++){
            ch=refId1.charAt(i);
            if(Character.isLowerCase(ch)){
                allUpper=true;
            }
            else{
                allUpper=false;
            }
        }
        return allUpper;
    }
}
