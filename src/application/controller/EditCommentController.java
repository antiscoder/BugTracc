package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.CommonObjs;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditCommentController {
	
	@FXML private TextField timestamp;
	
	@FXML private TextArea description;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	private CommentDAO commentDAO;
	
	public void initialize() {
		LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    String formattedDateTime = currentDateTime.format(formatter);
	    
	    timestamp.setText(formattedDateTime);
	    
	    //description.setText(CommentDAO.getCommentDescByID(commonObjs.getCurrentComment()));
	}
	
	public void save() {
	    String newDesc = description.getText();
	    
	    int commentID = commonObjs.getCurrentComment();
	    
	    CommentDAO.updateComment(commentID, timestamp.getText(), newDesc);
	    
	    URL ticketBox = getClass().getClassLoader().getResource("view/TicketBox.fxml");
	    URL ticketListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
	    URL displayComment = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");
	    
	    try {
	        // Load AnchorPane for the AllTickets view
	        AnchorPane pane = (AnchorPane) FXMLLoader.load(ticketBox);
	        VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);

	        // Retrieve the mainBox from commonObjs
	        HBox mainBox = commonObjs.getMainBox();

	        // If there is a view page in mainBox, remove it
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        // Adds the AllTickets view to the mainBox
	        mainBox.getChildren().add(pane);

	        // Add the ticketList to the AllTickets view
	        pane.getChildren().add(ticketList);

	        // Clear the ticketList
	        ticketList.getChildren().clear();

	        for (int commentID1 : CommentDAO.getCommentIDs()) {
	            int commentTicketID = CommentDAO.getCommentTicketByID(commentID1);
//	            String projectName = ProjectDAO.getProjectNameByID(ticketProjectID);
//	            String ticketName = TicketDAO.getTicketNameByID(ticketID1);
//	            String ticketDesc = TicketDAO.getTicketDescByID(ticketID1);

	            // Check if the ticket belongs to the current project
	            if (commentTicketID == commonObjs.getCurrentTicket()) {
	                
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void cancel() {
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
		URL commentUrl = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");
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
		            
			VBox commentList = commonObjs.getCommentList();
			commentList.getChildren().clear();
					
			for (int commentID : commentDAO.getCommentIDs()) {
				int commentTicketID = commentDAO.getCommentTicketByID(commentID);
				int commentProjectID = commentDAO.getCommentProjectByID(commentID);
				String commentText = commentDAO.getCommentByID(commentID);
				
				// Check if the comment belongs to the current ticket (maybe current project)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					AnchorPane commentDisplay = (AnchorPane) FXMLLoader.load(commentUrl);
					commentList.getChildren().add(commentDisplay);
				}
			}
					
			pane1.getChildren().add(commentList);
				
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}