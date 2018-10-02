package se.bms.wearep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application
{
	
	private static final String TITLE = "Wearep - BMS";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Wearep");
		stage.setResizable(false);
		
		Parent fxml = FXMLLoader.load(getClass().getResource("view/container.fxml"));
		
		Scene scene = new Scene(fxml,WIDTH,HEIGHT);
		stage.setScene(scene);
		
		stage.show();
	}
}