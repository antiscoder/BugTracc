package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditProjectController {
	@FXML
	private TextField name;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	public TicketDAO ticketDAO;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	// updates date when editing a project (fix later)
	public void initialize() {
		name.setText(commonObjs.getProjectName());
		date.setValue(LocalDate.now());
		description.setText(commonObjs.getProjectDesc());
	}
		
		
	public void saveProject() {
		
	}
	
	public void cancel() {
		// URL for the "ProjectBox.fxml" file
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/AllTickets.fxml");
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		try {
					
			// Load AnchorPane for the ProjectBox view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
		    VBox box1 = (VBox) FXMLLoader.load(url);
					
			// Retrieve the mainBox from commonObjs
			HBox mainBox = commonObjs.getMainBox();
					
			// If there is a view page in mainBox, remove it
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
					
			// Adds pane1 to the mainBox
		    mainBox.getChildren().add(pane1);
		            
			VBox ticketList = commonObjs.getTicketList();
			ticketList.getChildren().clear();
					
			for (int ticketID : ticketDAO.getTicketIDs()) {
				int ticketProjectID = ticketDAO.getTicketProjectByID(ticketID);
				String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = ticketDAO.getTicketNameByID(ticketID);
				String ticketDesc = ticketDAO.getTicketDescByID(ticketID);
						
				System.out.println(commonObjs.getCurrentProject());
							
				// Create a button for the ticket and add it to box1
				Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
				ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
				box1.getChildren().add(ticketButton);
			}
					
			pane1.getChildren().add(box1);
				
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}