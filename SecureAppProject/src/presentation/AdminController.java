package presentation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import insertBookVerification.Book;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Books;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import insertBookVerification.BookFactory;

public class AdminController implements Initializable {

    @FXML
    private TextField tfBookRefId;
    @FXML
    private TextField tfConfirmBookRefId;
    @FXML
    BorderPane adminBorderPane;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfPages;
    @FXML
    private TableView<Books> tvBooks;
    @FXML
    private TableColumn<Books,String> colTitle;
    @FXML
    private TableColumn<Books,String> colAuth;
    @FXML
    private TableColumn<Books,Integer> colYear;
    @FXML
    private TableColumn<Books,Integer> colPages;
    @FXML
    private Button insertBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button delBtn;
    @FXML
    private javafx.scene.control.Label messageLabel;


    @FXML
    public void btnBackAction(){
        System.out.println("Admin Back pressed");
        goToLoginPage();
        closeWindow();

    }
    @FXML
    public void btnAction(ActionEvent actionEvent) throws SQLException {
        if(tfTitle.getText()==""||tfAuthor.getText()==""||tfBookRefId.getText()==""||tfConfirmBookRefId.getText()==""||tfYear.getText()==""||tfPages.getText()==""){
            showMessage("You haven't entered the details of the book to be inserted!");
        }
        else if(actionEvent.getSource()==insertBtn){
            insertBook();
        }
    }
    public void btnAction2(ActionEvent actionEvent) throws SQLException {
        if(actionEvent.getSource()==updateBtn){
            persistence.BookListDAO.updateBookItem(tfTitle.getText(),tfAuthor.getText(),tfBookRefId.getText(),Integer.parseInt(tfYear.getText()),Integer.parseInt(tfPages.getText()));
            showBooks();
        }
        else if(actionEvent.getSource()==delBtn){
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Just confirming...");
            alert.setContentText("Are you sure you don't want to keep this book item?");
            Optional<ButtonType>action=alert.showAndWait();
            if(action.get()==ButtonType.OK){
                deleteBookItem(tfBookRefId.getText());
            }
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
    public void closeWindow(){
        Stage stage = (Stage) adminBorderPane.getScene().getWindow();
        stage.close();
    }
    // Factory Design pattern used
    private void insertBook() throws SQLException {
        String bookrefId1=tfBookRefId.getText();
        String bookrefId2=tfConfirmBookRefId.getText();
        String msg=confirmBookRefId(bookrefId1,bookrefId2);

        showMessage(msg);
        if(msg.trim().equals("OK")){
            String query="INSERT INTO Books VALUES ('"+tfTitle.getText()+"','"+tfAuthor.getText()+
                    "','"+tfBookRefId.getText()+"',"+tfYear.getText()+","+tfPages.getText()+")";
            executeQuery(query);
            showBooks();
        }
    }

    //            Alternate compliant solution (for insecure coding practise):

//            private void insertBook(String title, String author, String bookRefID, int year, int pages) throws SQLException {
//                String bookrefId1=tfBookRefId.getText();
//                String bookrefId2=tfConfirmBookRefId.getText();
//                String msg=confirmBookRefId(bookrefId1,bookrefId2);
//
//                showMessage(msg);
//                if(msg.trim().equals("OK")){
//                    String query="INSERT INTO Books(title,author,bookRefID,year,pages)VALUES(?,?,?,?,?)";
//                    try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
//                        try(PreparedStatement ps=con.prepareStatement(query)){
//                            ps.setString(1, title);
//                            ps.setString(2, author);
//                            ps.setString(3, bookRefID);
//                            ps.setInt(4, year);
//                            ps.setInt(5, pages);
//                            ps.executeUpdate();
//                        }catch(Exception e){
//                            System.out.println(e.getMessage()+"Book Insert failed");
//                        }
//                    }catch(Exception e){
//                        System.out.println(e.getMessage()+"Book Insert failed");
//                    }
//            }
    private void executeQuery(String query) throws SQLException {
        Connection con = persistence.DBMySQL.getInstance().getConnection();
        Statement st;
        ResultSet rs;
        try{
            st= con.createStatement();
            st.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book Insert failed");
        }
    }
    private String confirmBookRefId(String refId1, String refId2){
        BookFactory bf=new BookFactory();
        Book bookref1=bf.getBookRefIdTest("STRTCHR");
        Book bookref2=bf.getBookRefIdTest("ALLCAPS");
        Book bookref3=bf.getBookRefIdTest("LENGTHCHK");
        Book bookref4=bf.getBookRefIdTest("ENDCHR");
        Book bookref5=bf.getBookRefIdTest("SMLR");
        List<Book> list=new ArrayList<Book>();
        list.add(bookref1);
        list.add(bookref2);
        list.add(bookref3);
        list.add(bookref4);
        list.add(bookref5);
        String message="";
        for(Book b:list){
            if(b.confirmBookRefId(refId1,refId2)){
                message=b.getMessage();
                showMessage("message"+message);
                break;
            }else{
                message="OK";
            }
        }
        return message;
    }
    private void deleteBookItem(String bookRefID) throws SQLException{
        String query="DELETE FROM Books WHERE bookRefID=?";
        try(Connection con = persistence.DBMySQL.getInstance().getConnection()){
            try(PreparedStatement ps=con.prepareStatement(query)){
                ps.setString(1, bookRefID);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Book delete failed");
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"Book delete failed");
        }
        showBooks();
    }

    @FXML
    public void handleMouseAction(MouseEvent mouseEvent) {
        Books books=tvBooks.getSelectionModel().getSelectedItem();
        if(books!=null){
            tfTitle.setText("" + books.getTitle());
            tfAuthor.setText("" + books.getAuthor());
            tfYear.setText("" + books.getYear());
            tfPages.setText("" + books.getPages());
            tfBookRefId.setText(""+books.getBookRefId());
        }
    }

    public void showBooks() throws SQLException {
        ObservableList<Books>list=persistence.BookListDAO.getInstance().getBooksList();
        colTitle.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));
        tvBooks.setItems(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showBooks();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
