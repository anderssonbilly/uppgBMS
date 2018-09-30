package se.bms.wearep;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
		
		Scene scene = new Scene(new Pane(),WIDTH,HEIGHT);
		stage.setScene(scene);
		
		stage.show();
	}
	
}
