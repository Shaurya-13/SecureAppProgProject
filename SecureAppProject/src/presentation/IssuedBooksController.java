package presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Books;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class IssuedBooksController implements Initializable {

    @FXML
    public AnchorPane issuedAnchor;
    @FXML
    private Button returnBtn;
    @FXML
    private TextField tfTitle3;
    @FXML
    private TextField tfAuthor3;
    @FXML
    private TableColumn colAuthor3;
    @FXML
    private TableColumn colTitle3;
    @FXML
    private TableView tvBooks3;
    @FXML
    private TextField tfYear3;
    @FXML
    private TextField tfPages3;
    @FXML
    private Label messageLabel3;
    @FXML
    private TextField tfBookRefId3;

    public void backBtnAction(ActionEvent actionEvent) {

        Stage stage = (Stage) issuedAnchor.getScene().getWindow();
        stage.close();
    }

    public void returnBookAction(ActionEvent actionEvent) throws SQLException {
        if(tfTitle3.getText()==""||tfAuthor3.getText()==""){
            showMessage("No details for the book to be returned have been entered please try again!!");
        }
        else if(actionEvent.getSource()==returnBtn){
            returnBook(tfTitle3.getText(),tfAuthor3.getText(),tfBookRefId3.getText(),Integer.parseInt(tfYear3.getText()),Integer.parseInt(tfPages3.getText()));
            deleteBookItem(tfBookRefId3.getText());
        }
    }
    protected void showMessage(String msg){
        messageLabel3.setText(msg);
    }

    private  ObservableList<Books> getIssuedBooksList() throws SQLException {
        ObservableList<Books> bookList= FXCollections.observableArrayList();
        Connection con = persistence.DBMySQL.getInstance().getConnection();
        String query="SELECT * FROM IssuedBooks";
        Statement st;
        ResultSet rs;
        try{
            st=con.createStatement();
            rs=st.executeQuery(query);
            Books books;
            while(rs.next()){
                books= new Books( rs.getString("title"), rs.getString("author"),rs.getString("bookRefID"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(books);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return bookList;
    }
    public void showBooks() throws SQLException {
        ObservableList<Books>list=getIssuedBooksList();
        colTitle3.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        colAuthor3.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        tvBooks3.setItems(list);
    }
    private void returnBook(String title, String author, String bookRefID, int year, int pages) throws SQLException {
        String query="INSERT INTO Books(title,author,bookRefID,year,pages)VALUES(?,?,?,?,?)";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, bookRefID);
                ps.setInt(4, year);
                ps.setInt(5, pages);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book return failed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book return failed");
        }
        showBooks();
    }
    private void deleteBookItem(String bookRefID) throws SQLException{
        String query="DELETE FROM IssuedBooks WHERE bookRefID=?";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, bookRefID);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book return failed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book return failed");
        }
        showBooks();
    }
    public void displayAction(MouseEvent mouseEvent) {
        Books books= (Books) tvBooks3.getSelectionModel().getSelectedItem();
        if(books!=null) {
            tfTitle3.setText("" + books.getTitle());
            tfAuthor3.setText("" + books.getAuthor());
            tfYear3.setText("" + books.getYear());
            tfPages3.setText("" + books.getPages());
            tfBookRefId3.setText(""+books.getBookRefId());
        }
    }
    private void notEditableDetails(){
        tfTitle3.setEditable(false);
        tfAuthor3.setEditable(false);
        tfBookRefId3.setEditable(false);
        tfPages3.setEditable(false);
        tfYear3.setEditable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notEditableDetails();
        try {
            showBooks();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
