/*
 * 
 */
package application.presenter.treeview;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

/**
 * A custom TreeItem which represents Files
 */
public class TreeItemFile extends TreeItem<String> {
	
	/** Stores the full path to the file or directory. */
	private String fullPath;
	
	/** Defines if this File is a Directory */
	private boolean isDirectory;
	
	/**
	 * Constructor.
	 *
	 * @param absolutePath
	 *            The absolute path of the file or folder
	 * 
	 */
	public TreeItemFile(String absolutePath) {
		super(absolutePath);
		this.fullPath = absolutePath;
		
		//Is this a directory?
		isDirectory = new File(fullPath).isDirectory();
		setGraphic(new ImageView(!isDirectory ? SystemRoot.fileImage : SystemRoot.closedFolderImage));
		
		// set the value
		if (!fullPath.endsWith(File.separator)) {
			// set the value (which is what is displayed in the tree)
			String value = absolutePath;
			int indexOf = value.lastIndexOf(File.separator);
			if (indexOf > 0)
				this.setValue(value.substring(indexOf + 1));
			else
				this.setValue(value);
			
		}
		
		//this.setValue(InfoTool.getFileName(absolutePath));
	}
	
	/**
	 * Gets the full path.
	 *
	 * @return the full path
	 */
	public String getFullPath() {
		return fullPath;
	}
	
	/**
	 * Checks if is directory.
	 *
	 * @return true, if is directory
	 */
	public boolean isDirectory() {
		return isDirectory;
	}
}
