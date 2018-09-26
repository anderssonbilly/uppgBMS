package se.bms.wearep;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Launcher extends Application
{
    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Pane(),400,400);
		stage.setTitle("Wearep");
		stage.setScene(scene);
		stage.show();
	}
}
