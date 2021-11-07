package Test.presentation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import insertBookVerification.BookFactory;
import insertBookVerification.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookReferenceIdTest {
    String refId1;
    String refId2;
    String message;
    BookFactory bf;
    Book b;

    @BeforeEach
    void setUp() {
        bf=new BookFactory();
    }

    @AfterEach
    void tearDown() {
        bf=null;
    }

    @DisplayName("Test book reference ID starting with special character")
    @Test
    void confirmBookRefIdStartingCharacterFalse(){

        String refId1="ABCDEFG";
        String refId2="ABCDEFG";
        b = bf.getBookRefIdTest("STRTCHR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,true);
        System.out.println("Starting character of book reference Id is not special character ");
    }
    @DisplayName("Test book reference ID starting with special character")
    @Test
    void confirmBookRefIdStartingCharacterTrue(){

        refId1="#ABCDEFG";
        refId2="$ABCDEFG";
        b = bf.getBookRefIdTest("STRTCHR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,false);
        System.out.println("Starting character of book reference Id is a special character ");
    }
    @DisplayName("Test book reference Id has all capital letters ")
    @Test
    void confirmBookRefIdAllCapsFalse(){

        String refId1="#ABCdefg";
        String refId2="$ABCdefg";
        b = bf.getBookRefIdTest("ALLCAPS");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,true);
        System.out.println("All characters of book reference Id are not captial");
    }
    @DisplayName("Test book reference Id has all capital letters ")
    @Test
    void confirmBookRefIdAllCapsTrue(){

        refId1="#ABCDEFG";
        refId2="$ABCDEFG";
        b = bf.getBookRefIdTest("ALLCAPS");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,false);
        System.out.println("All characters of book reference Id are captial");
    }
    @DisplayName("Test Length greater than 6")
    @Test
    void confirmBookRefIdLengthFalse() {

        refId1 = "ABCD";
        refId2 = "ABCD";
        b = bf.getBookRefIdTest("LENGTHCHK");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,true);
        System.out.println("Book reference Id length is less than 6 characters");
    }
    @DisplayName("Test Length greater than 6")
    @Test
    void confirmBookRefIdLengthTrue() {

        refId1 = "ABCDEFG";
        refId2 = "ABCDEFG";
        b = bf.getBookRefIdTest("LENGTHCHK");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,false);
        System.out.println("Book reference Id length is greater than 6 characters as per requirement");
    }

    @DisplayName("Test book reference Id's last character is a number ")
    @Test
    void confirmBookRefIdLastChrFalse(){

        refId1="#ABCDEFG";
        refId2="$ABCDEFG";
        b = bf.getBookRefIdTest("ENDCHR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,true);
        System.out.println("Last character of book reference Id is not a number/digit");
    }
    @DisplayName("Test book reference Id's last character is a number ")
    @Test
    void confirmBookRefIdLastChrTrue(){

        refId1="#ABCDEFG8";
        refId2="$ABCDEFG9";
        b = bf.getBookRefIdTest("ENDCHR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,false);
        System.out.println("Check last character of book reference Id is a number/digit");
    }
    @DisplayName("Test Book reference ID match")
    @Test
    void confirmBookRefIdMatchFalse() {

        refId1 = "ABCDEFG";
        refId2 = "ABCGFDAS";
        b = bf.getBookRefIdTest("SMLR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,true);
        System.out.println("Check reference Id's are not matching");

    }
    @DisplayName("Test Book reference ID match")
    @Test
    void confirmBookRefIdMatchTrue() {

        refId1 = "ABCDEFG";
        refId2 = "ABCDEFG";
        b = bf.getBookRefIdTest("SMLR");
        boolean ans=b.confirmBookRefId(refId1,refId2);
        message=b.getMessage();
        assertEquals(ans,false);
        System.out.println("Reference Id's are matching");

    }


}