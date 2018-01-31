package metabolique;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class PathwaySelectionListener implements ActionListener {

	DirectoryResearchBar researchBar;

	PathwaySelectionListener(DirectoryResearchBar researchBar) {
		this.researchBar = researchBar;
	}

	public void actionPerformed(ActionEvent e) {

		JFileChooser Directory = new JFileChooser();
		Directory.setCurrentDirectory(new File(System.getProperty("user.home")));
		Directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = Directory.showSaveDialog(null);

		if(result == JFileChooser.APPROVE_OPTION) {
			File selectedDirectory = Directory.getSelectedFile();
			String path = selectedDirectory.getAbsolutePath();
			researchBar.getTextField().setText(path);
		}
	}
}
