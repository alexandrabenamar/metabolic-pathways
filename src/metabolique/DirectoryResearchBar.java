package metabolique;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DirectoryResearchBar {

	/**
	 * JPanel contenant le chemin d'acces au fichier
	 */
	protected JPanel directory = new JPanel();

	/**
	 * description du label : repertoire
	 */
	protected final JLabel directoryLabel = new JLabel("Répertoire");

	/**
	 * creation d'un champ de texte
	 */
	protected JTextField textField = new JTextField(30);
	/**
	 * creation du bouton parcourir
	 */
	protected JButton Browse = new JButton ("..."); //bouton parcourir
	
	/**
	 * affichage du nom de la voie de signalisation
	 */
	protected JLabel pathName;
	
	/**
	 * Liste des especes contenue dans le menu des especes
	 */
	protected DefaultListModel<String> speciesList = new DefaultListModel<String>();

	protected File directoryName;


	public File getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(File directoryName) {
		this.directoryName = directoryName;
	}

	DirectoryResearchBar() {
		directory.add(directoryLabel);
		directory.add(textField);
		directory.add(Browse);
		Browse.addActionListener(new PathwaySelectionListener(this));
	}

	void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JPanel getDirectory() {
		return directory;
	}

	public void setDirectory(JPanel directory) {
		this.directory = directory;
	}

	public JButton getBrowse() {
		return Browse;
	}

	public void setBrowse(JButton browse) {
		Browse = browse;
	}

	public JLabel getpathName() {
		return pathName;
	}

	public void setpathName(JLabel pathName) {
		this.pathName = pathName;
	}

	public JLabel getDirectoryLabel() {
		return directoryLabel;
	}

	public JTextField getTextField() {
		return textField;
	}
	
	public JLabel getPathName() {
		return pathName;
	}

	public void setPathName(JLabel pathName) {
		this.pathName = pathName;
	}

	public DefaultListModel<String> getSpeciesList() {
		return speciesList;
	}

	public void setSpeciesList(DefaultListModel<String> speciesList) {
		this.speciesList = speciesList;
	}


}
