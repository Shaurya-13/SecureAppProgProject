package insertBookVerification;

public class BookFactory{
    public Book getBookRefIdTest(String refIdType){
        if(refIdType==null){
            return null;
        }
        else if(refIdType.equalsIgnoreCase("STRTCHR")){
            return new BookRefIdstrt("Book reference Id does not start with a special character, Please try again");
        }
        else if(refIdType.equalsIgnoreCase("ALLCAPS")){
            return new BookRefIdCaps("Book reference Id should contain all capital letter");
        }
        else if(refIdType.equalsIgnoreCase("LENGTHCHK")){
            return new BookRefIDLength("Book reference Id should be longer than 6 characters");
        }
        else if(refIdType.equalsIgnoreCase("ENDCHR")){
            return new BookRefIdEnd("Book reference Id should have a number as its last character");
        }
        else if(refIdType.equalsIgnoreCase("SMLR")){
            return new BookRefIdSimilar("Book reference Id's do not match!");
        }
        return null;
    }
}
