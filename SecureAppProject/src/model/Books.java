package model;

public class Books {

    private String title;
    private String author;
    private int year;
    private int pages;
    private String bookRefId;


    public Books( String title, String author, String bookRefId, int year, int pages) {

        this.title = title;
        this.author = author;
        this.bookRefId=bookRefId;
        this.year = year;
        this.pages = pages;
    }

    public String getBookRefId() {
        return bookRefId;
    }

    public void setBookRefId(String bookRefiId) {
        this.bookRefId = bookRefiId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
