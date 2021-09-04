package controller;

import bo.BoFactory;
import bo.custom.RoomBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.RoomDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.tm.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RoomFormController implements Initializable {

    public AnchorPane RoomForm;
    public JFXTextField txtRoomId;
    public JFXRadioButton rbtnSingleBed;
    public JFXRadioButton rbtnDoubleBed;
    public JFXTextField txtRoomStatus;
    public JFXRadioButton rbtnNonAC;
    public JFXRadioButton rbtnAC;
    public JFXTextField txtAmount;
    public JFXButton btnAdd;
    public TableView<RoomTM> tblRoom;
    public ComboBox<String> cmbStatus;
    public Label lblRoomType;
    public Label lblRoomCondition;
    private RoomBO roomBO;
    private String type;
    private String condition;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomBO= BoFactory.getInstance().getBo(BoFactory.BoType.ROOM);

        ObservableList<String> values = FXCollections.observableArrayList("Available", "Non Available");
        cmbStatus.setItems(values);

        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomStatus"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("roomCondition"));
        tblRoom.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("roomAmount"));

        loadAllRooms();
        try {
            loadRoomId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadRoomId() throws Exception {
        String id=roomBO.getRoomId();
        txtRoomId.setText(id);
    }
    public void setUI(String location) throws IOException {
        Stage stage= (Stage) RoomForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/"+location))));
    }

    public void CustomerFormClick(ActionEvent actionEvent) throws IOException {
        setUI("CustomerForm.fxml");
    }

    public void RoomFormClick(ActionEvent actionEvent) throws IOException {
        setUI("RoomForm.fxml");
    }

    public void ServiceFormClick(ActionEvent actionEvent) throws IOException {
        setUI("ServiceForm.fxml");
    }

    public void ReservationFormClick(ActionEvent actionEvent) throws IOException {
        setUI("ReservationForm.fxml");
    }

    public void SearchFormClick(ActionEvent actionEvent) throws IOException {
        setUI("BillingForm.fxml");
    }

    public void btnMainOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MainForm.fxml");
    }

    public void AllReservationClick(ActionEvent actionEvent) throws IOException {
        setUI("AllReservationsForm.fxml");
    }

    public void btnAddOnAction(MouseEvent mouseEvent) {
        if(lblRoomType.getText().isEmpty() | lblRoomCondition.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Fields Empty").show();
            return;
        }
        if(Pattern.compile("^(R)[0-9]{1,}").matcher(txtRoomId.getText()).matches()) {
            if(Pattern.compile("^(Available)|(Non Available)$").matcher(txtRoomStatus.getText()).matches()) {
                if(Pattern.compile("^[0-9].{1,}$").matcher(txtAmount.getText()).matches()) {
                    try {
                        boolean isAdded = roomBO.saveRoom(new RoomDTO(
                                txtRoomId.getText(),
                                txtRoomStatus.getText(),
                                lblRoomType.getText(),
                                lblRoomCondition.getText(),
                                Double.parseDouble(txtAmount.getText())
                        ));
                        if (isAdded) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room is Added");
                            alert.show();
                            clearFields();
                            loadAllRooms();
                            loadRoomId();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Added Fail");
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    txtAmount.setFocusColor(Paint.valueOf("red"));
                    txtAmount.requestFocus();
                }
            }else{
                txtRoomStatus.setFocusColor(Paint.valueOf("red"));
                txtRoomStatus.requestFocus();
            }
        }else{
            txtRoomId.setFocusColor(Paint.valueOf("red"));
            txtRoomId.requestFocus();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) throws Exception {
        if(Pattern.compile("^(R)[0-9]{1,}").matcher(txtRoomId.getText()).matches()) {
            RoomDTO roomDTO = roomBO.searchRoom(txtRoomId.getText());
            if (roomDTO != null) {
                txtRoomId.setText(roomDTO.getRoomId());
                txtRoomStatus.setText(roomDTO.getRoomStatus());
                lblRoomType.setText(roomDTO.getRoomType());
                lblRoomCondition.setText(roomDTO.getRoomCondition());
                txtAmount.setText(roomDTO.getRoomAmount() + "");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Room Not Found");
                alert.show();
            }
        }else{
            txtRoomId.setFocusColor(Paint.valueOf("red"));
            txtRoomId.requestFocus();
        }

    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
        if(lblRoomType.getText().isEmpty() | lblRoomCondition.getText().isEmpty() | txtAmount.getText().isEmpty() | txtRoomStatus.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Fields Empty").show();
            return;
        }
        try {
            boolean isDeleted = roomBO.deleteRoom(txtRoomId.getText());
            if(isDeleted){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Room is Deleted");
                alert.show();
                clearFields();
                loadAllRooms();
                loadRoomId();
            }else{
                new Alert(Alert.AlertType.WARNING,"Delete Fail").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Delete Fail").show();
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        if(lblRoomType.getText().isEmpty() | lblRoomCondition.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Fields Empty").show();
            return;
        }
        if(Pattern.compile("^(R)[0-9]{1,}").matcher(txtRoomId.getText()).matches()) {
            if(Pattern.compile("^(Available)|(Non Available)$").matcher(txtRoomStatus.getText()).matches()) {
                if(Pattern.compile("^[0-9].{1,}$").matcher(txtAmount.getText()).matches()) {
                    try {
                        boolean isUpdated = roomBO.updateRoom(new RoomDTO(
                                txtRoomId.getText(),
                                txtRoomStatus.getText(),
                                lblRoomType.getText(),
                                lblRoomCondition.getText(),
                                Double.parseDouble(txtAmount.getText())
                        ));
                        if(isUpdated){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Room is Updated");
                            alert.show();
                            clearFields();
                            loadAllRooms();
                            loadRoomId();
                        }else{
                            Alert alert=new Alert(Alert.AlertType.WARNING,"Update Fail");
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    txtAmount.setFocusColor(Paint.valueOf("red"));
                    txtAmount.requestFocus();
                }
            }else{
                txtRoomStatus.setFocusColor(Paint.valueOf("red"));
                txtRoomStatus.requestFocus();
            }
        }else{
            txtRoomId.setFocusColor(Paint.valueOf("red"));
            txtRoomId.requestFocus();
        }
    }


    public void roomConditonOnAction(ActionEvent actionEvent) {
        if(rbtnAC.isSelected()){
            rbtnNonAC.setSelected(false);
            lblRoomCondition.setText("A/C");
        }else{
            rbtnAC.setSelected(false);
            rbtnNonAC.setSelected(true);
            lblRoomCondition.setText("NonA/C");
        }
    }

    public void roomTypeOnAction(ActionEvent actionEvent) {
        if(rbtnDoubleBed.isSelected()){
            rbtnSingleBed.setSelected(false);
            lblRoomType.setText("DoubleBed");
        }else{
            rbtnDoubleBed.setSelected(false);
            rbtnSingleBed.setSelected(true);
            lblRoomType.setText("SingleBed");
        }
    }

    public void cmbStatusOnAction(ActionEvent actionEvent) {
        try {
            txtRoomStatus.setText(cmbStatus.getValue().toString());
        }catch (NullPointerException nullPointerException){

        }

    }

    public void loadAllRooms(){
        try {
            ObservableList<RoomTM> rooms = FXCollections.observableArrayList();
            ArrayList<RoomDTO> allRooms = roomBO.getAllRooms();
            for (RoomDTO d: allRooms) {
                rooms.add(new RoomTM(
                        d.getRoomId(),
                        d.getRoomStatus(),
                        d.getRoomType(),
                        d.getRoomCondition(),
                        d.getRoomAmount()
                ));
            }
            tblRoom.setItems(rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFields(){
        txtRoomStatus.clear();
        txtRoomStatus.clear();
        lblRoomType.setText("");
        lblRoomCondition.setText("");
        txtAmount.clear();
        rbtnNonAC.setSelected(false);
        rbtnAC.setSelected(false);
        cmbStatus.getSelectionModel().clearSelection();
        rbtnSingleBed.setSelected(false);
        rbtnDoubleBed.setSelected(false);
    }
}
