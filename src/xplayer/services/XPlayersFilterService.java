package xplayer.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import application.Main;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import xplayer.XPlayerController;
import xplayer.streamplayer.ThreadFactoryWithNamePrefix;

public class XPlayersFilterService {
	
	/**
	 * If is true then the Thread has stopped , so i restart it again...
	 */
	private final BooleanProperty threadStopped = new SimpleBooleanProperty(false);
	
	/**
	 * This executor service is used in order the playerState events to be executed in an order
	 */
	private final ExecutorService executors = Executors.newSingleThreadExecutor(new ThreadFactoryWithNamePrefix("Files Filter Service"));
	
	/**
	 * Start the Thread.
	 *
	 */
	public void start() {
		Runnable runnable = () -> {
			try {
				Platform.runLater(() -> threadStopped.set(false));
				
				//Run forever , except if i interrupt it ;)
				for (;; Thread.sleep(500))
					Platform.runLater(() -> Main.xPlayersList.getList().forEach(xPlayerController -> {
						
						//-------Set the appropriate image for the PlayPauseButton based on the status of the Player
						( (ImageView) xPlayerController.getPlayPauseButton().getGraphic() )
								.setImage(xPlayerController.getxPlayer().isPlaying() ? XPlayerController.pauseImage : XPlayerController.playImage);
						
						// ---------Liked or disliked--------?
						xPlayerController.changeEmotionImage(Main.emotionListsController.getEmotionForMedia(xPlayerController.getxPlayerModel().songPathProperty().get()));
						
					}));
			} catch (Exception ex) {
				Main.logger.log(Level.INFO, "", ex);
			} finally {
				System.out.println("XPlayersFilterService Thread exited!!!");
				Platform.runLater(() -> threadStopped.set(true));
			}
		};
		executors.execute(runnable);
		
		//---Add this listener in case something bad happens to the thread above
		threadStopped.addListener((observable , oldValue , newValue) -> {
			//Restart it if it has stopped
			if (newValue)
				executors.execute(runnable);
		});
		
	}
	
}
