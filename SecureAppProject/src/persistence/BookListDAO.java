package persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Books;

import java.sql.*;

public class BookListDAO {
    // Singleton Design pattern used
    private static BookListDAO onlyInstance = null;
    private BookListDAO() {
    }

    public static BookListDAO getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new BookListDAO();
        }
        return onlyInstance;
    }
    public static ObservableList<Books> getBooksList() throws SQLException {
        ObservableList<Books> bookList= FXCollections.observableArrayList();
        Connection con = persistence.DBMySQL.getInstance().getConnection();
        String query="SELECT * FROM Books";
        Statement st;
        ResultSet rs;
        try{
            st=con.createStatement();
            rs=st.executeQuery(query);
            Books books;
            while(rs.next()){
                books= new Books(rs.getString("title"), rs.getString("author"),rs.getString("bookRefID"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(books);
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Could not print list of all books");
        }
        return bookList;
    }
    public static void updateBookItem(String title, String author, String bookRefID, int year, int pages) throws SQLException {
        String query="UPDATE Books SET title=?,author=?,year=?,pages=? WHERE bookRefID=?";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, year);
                ps.setInt(4, pages);
                ps.setString(5, bookRefID);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book Update failed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book Update failed");
        }
    }
}
