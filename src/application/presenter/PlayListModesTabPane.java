package application.presenter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import application.tools.InfoTool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * 
 *
 * @author GOXR3PLUS
 */
public class PlayListModesTabPane extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private Tab openedLibrariesTab;
	
	@FXML
	private Tab emotionListsTab;
	
	@FXML
	private Tab searchEverythingTab;
	
	// -------------------------------------------------------------
	
	/** The logger. */
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Constructor.
	 */
	public PlayListModesTabPane() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(InfoTool.FXMLS + "PlayListModesTabPane.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
		
	}
	
	/**
	 * Called as soon as .FXML is loaded from FXML Loader
	 */
	@FXML
	private void initialize() {
		
		//
		openedLibrariesTab.setContent(Main.libraryMode.multipleLibs);
		
		//
		emotionListsTab.setContent(new BorderPane(Main.emotionsTabPane));
		//
		searchEverythingTab.setContent(new BorderPane(Main.searchWindowSmartController));
	}
	
	/**
	 * Select the tab with the given index in the TabPane
	 * 
	 * @param index
	 */
	public void selectTab(int index) {
		openedLibrariesTab.getTabPane().getSelectionModel().select(index);
		
		//In case of Search Window Tab  Request focus of Search Field
		//if (index == 2)
		//	Main.searchWindowSmartController.getSearchService().getSearchField().requestFocus();
	}
	
}
