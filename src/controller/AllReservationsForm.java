package controller;

import bo.BoFactory;
import bo.custom.ReservationBO;
import dto.ReservationDTO;
import entity.AllReservationDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.ReservationTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllReservationsForm implements Initializable {
    public AnchorPane AllReservationForm;
    public TableView<ReservationTM> tblReservation;
    private ReservationBO reservationBO= BoFactory.getInstance().getBo(BoFactory.BoType.RESERVATION);
    public void setUI(String location) throws IOException {
        Stage stage= (Stage) AllReservationForm.getScene().getWindow();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblReservation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
        tblReservation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        tblReservation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ServiceId"));
        tblReservation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        tblReservation.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("ReservationDate"));
        tblReservation.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CheckInDate"));
        tblReservation.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CheckOutDate"));
        tblReservation.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Terms"));
        tblReservation.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("ServiceAmount"));
        tblReservation.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("FullPayment"));

        ObservableList<ReservationTM> list = FXCollections.observableArrayList();
        try {
            ArrayList<ReservationDTO> allReservationDetail = reservationBO.getAllReservationDetail();
            for (ReservationDTO d: allReservationDetail) {
                list.add(new ReservationTM(
                        d.getReservationId(),
                        d.getCustomerId(),
                        d.getServiceId(),
                        d.getRoomId(),
                        d.getReservationDate(),
                        d.getCheckInDate(),
                        d.getCheckOutDate(),
                        d.getTerms(),
                        d.getServiceAmount(),
                        d.getFullPayment()
                ));
               tblReservation.setItems(list);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
