package insertBookVerification;

public abstract class Book {
    protected String refId1;
    protected String refId2;
    protected String refIdCheck;
    public String message;

    public Book(){this.message="";}
    public Book(String message){this.message=message;}
    public Book(String refId1,String refId2, String refIdCheck){
        this.refId1=refId1;
        this.refId2=refId2;
        this.refIdCheck=refIdCheck;
        this.message="";
    }
    public String getMessage(){return message;}
    public abstract boolean confirmBookRefId(String refId1, String refId2);
}
