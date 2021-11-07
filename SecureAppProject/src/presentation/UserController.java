package presentation;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Books;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label messageLabel;
    @FXML
    private TextField tfBookRefId2;
    @FXML
    private TextField tfFilterField;
    @FXML
    private BorderPane userBorderPane;
    @FXML
    private TextField tfYear2;
    @FXML
    private TextField tfPages2;
    @FXML
    private TextField tfTitle2;
    @FXML
    private TextField tfAuthor2;
    @FXML
    private TableView<Books> tvBooks2;
    @FXML
    private TableColumn<Books,String> colTitle;
    @FXML
    private TableColumn<Books,String> colAuthor;
    @FXML
    private TableColumn<Books,Integer> colYear;
    @FXML
    private TableColumn<Books,Integer> colPages;
    @FXML
    private Button issueBtn;




    public void btnBackAction(){
        System.out.println("User pressed back");
        goToLoginPage();
        closeWindow();

    }
    public void btnIssuedBooksAction(ActionEvent actionEvent) {
        System.out.println("User wants to check personal issued books");
        goToCustomerBookIssuedPage();
    }
    public void issueBtnAction(ActionEvent actionEvent) throws SQLException {
        if(tfTitle2.getText()==""||tfAuthor2.getText()==""){
            showMessage("Please enter the details or select the book to be issued!!");
        }
        else if(actionEvent.getSource()==issueBtn){
            issueBook(tfTitle2.getText(),tfAuthor2.getText(),tfBookRefId2.getText(),Integer.parseInt(tfYear2.getText()),Integer.parseInt(tfPages2.getText()));
            removeBook(tfBookRefId2.getText());
        }
    }
    protected void showMessage(String msg){
        messageLabel.setText(msg);
    }
    public void goToLoginPage(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    public void goToCustomerBookIssuedPage(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("issuedBooks.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    public void closeWindow(){
        Stage stage = (Stage) userBorderPane.getScene().getWindow();
        stage.close();
    }
    public void showBooks() throws SQLException {
        ObservableList<Books>list=persistence.BookListDAO.getInstance().getBooksList();
        colTitle.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));
        tvBooks2.setItems(list);
    }
    private void issueBook(String title, String author, String bookRefID, int year, int pages) throws SQLException {
        String query="INSERT INTO IssuedBooks(title,author,bookRefID,year,pages)VALUES(?,?,?,?,?)";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, bookRefID);
                ps.setInt(4, year);
                ps.setInt(5, pages);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book Issue failed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book Issue failed");
        }
        showBooks();
    }
    private void removeBook(String bookRefID) throws SQLException{
        String query="DELETE FROM Books WHERE bookRefID=?";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, bookRefID);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book not removed for issuing");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book not removed for issuing");
        }
        showBooks();
    }
    public void handleMouseAction(MouseEvent mouseEvent) {
        Books books= tvBooks2.getSelectionModel().getSelectedItem();
        if(books!=null){
            tfTitle2.setText(""+books.getTitle());
            tfAuthor2.setText(""+books.getAuthor());
            tfYear2.setText(""+books.getYear());
            tfPages2.setText(""+books.getPages());
            tfBookRefId2.setText(""+books.getBookRefId());
        }
    }

    private void searchBook() throws SQLException {
        ObservableList<Books>list2=persistence.BookListDAO.getInstance().getBooksList();
        colTitle.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));
        tvBooks2.setItems(list2);
        FilteredList<Books> filteredList=new FilteredList<>(list2,b->true);
        tfFilterField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(book->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=newValue.toLowerCase();
                if(book.getBookRefId().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                }else if(book.getTitle().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<Books> sortedData=new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tvBooks2.comparatorProperty());
        tvBooks2.setItems(sortedData);
    }
    private void notEditableDetails(){
        tfTitle2.setEditable(false);
        tfAuthor2.setEditable(false);
        tfBookRefId2.setEditable(false);
        tfPages2.setEditable(false);
        tfYear2.setEditable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notEditableDetails();
        try {
            showBooks();
            searchBook(); // used to search through the book list using Book reference id or book title
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
