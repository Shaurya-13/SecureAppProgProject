package presentation;

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
import java.util.ResourceBundle;

public class SuperController implements Initializable {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField tfTitle4;
    @FXML
    private TextField tfAuthor4;
    @FXML
    private TextField tfYear4;
    @FXML
    private TextField tfPages4;
    @FXML
    private TextField tfBookRefId4;
    @FXML
    private TableColumn colTitle4;
    @FXML
    private TableColumn colAuthor4;
    @FXML
    private TableColumn colYear4;
    @FXML
    private TableColumn colPages4;
    @FXML
    private TableView tvBooks4;
    @FXML
    private Button updateBtn;
    @FXML
    BorderPane superBorderPane;

    public void btnBackAction(){

        goToLoginPage();
        closeWindow();

    }
    public void btnUpdateAction(ActionEvent actionEvent) throws SQLException {
        if(tfTitle4.getText()==""||tfAuthor4.getText()==""||tfBookRefId4.getText()==""||tfYear4.getText()==""||tfPages4.getText()==""){
            showMessage("Details not available, please choose the book to be updated!");
        }
        else if(actionEvent.getSource()==updateBtn){
            persistence.BookListDAO.updateBookItem(tfTitle4.getText(),tfAuthor4.getText(),tfBookRefId4.getText(),Integer.parseInt(tfYear4.getText()),Integer.parseInt(tfPages4.getText()));
            showBooks();
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
    public void showBooks() throws SQLException {
        ObservableList<Books> list=persistence.BookListDAO.getInstance().getBooksList();
        colTitle4.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        colAuthor4.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        colYear4.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));
        colPages4.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));
        tvBooks4.setItems(list);
    }
    public void closeWindow(){
        Stage stage = (Stage) superBorderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMouseAction(MouseEvent mouseEvent) {
        Books books= (Books) tvBooks4.getSelectionModel().getSelectedItem();
        if(books!=null){
            tfTitle4.setText("" + books.getTitle());
            tfAuthor4.setText("" + books.getAuthor());
            tfYear4.setText("" + books.getYear());
            tfPages4.setText("" + books.getPages());
            tfBookRefId4.setText(""+books.getBookRefId());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfBookRefId4.setEditable(false);
        try {
            showBooks();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
