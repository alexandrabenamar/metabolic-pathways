package metabolique;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;


/**
 * Construction du menu deroulant contenant les voies et les especes
 */
public class ScrollMenu {
		
	/**
	 * voie de signalisation en cours de traitement
	 */
	protected Pathway pathway;

	/**
	 * menu deroulant de la voie de signalisation
	 */
	protected JScrollPane jspPath;
	/**
	 * menu deroulant de l'organisme
	 */
	protected JScrollPane jspOrg;
	/**
	 * menu deroulant contenant les voies de signalisation
	 */
	protected JList<String> pathMenu;
	/**
	 * menu deroulant contenant les especes
	 */
	protected JList<String> speciesMenu;

	/**
	 * Liste des voies contenue dans le menu des voies
	 */
	protected DefaultListModel<String> pathMenuList = new DefaultListModel<String>();
	/**
	 * Liste des especes contenue dans le menu des especes
	 */
	protected DefaultListModel<String> espMenuList = new DefaultListModel<String>();

	// MENUS DEROULANTS
	/**
	 * menu deroulant contenant les menus de voie et d'espece
	 */
	protected JPanel panelMenuDeroulant = new JPanel(new GridBagLayout());
	/**
	 * ecriture de la voie au dessus de son menu
	 */
	protected final JLabel pathMenuLabel= new JLabel("Voies"); 
	/**
	 * ecriture d'especes au dessus de son menu
	 */
	protected final JLabel speciesMenuLabel= new JLabel("Especes");

	// GRAPHIQUE
	/**
	 * Jpanel contenant le graphique
	 */
	protected JPanel panelGraphe = new JPanel(new GridBagLayout());
	/**
	 * menu deroulant du graphique
	 */
	protected JScrollPane jspPanelGraph;

	/**
	 * creation de la carte generique
	 */
	protected GenericGraph graphic;
	/**
	 * creation du graphique
	 */
	protected Graph graph; 
	/**
	 * creation du viewer pour le graphique
	 */
	protected Viewer viewer;
	
	protected File directory;
	
	protected GridBagConstraints contrainte;

	
	ScrollMenu(File directoryName){
		
		this.directory = directoryName;
		
		contrainte = new GridBagConstraints();
		contrainte.insets = new Insets(5,5,5,5);
		contrainte.gridx = 0; contrainte.gridy = 1;
	}
	
	void createPathMenu() {
		pathMenu = new JList<String>(pathMenuList);
		pathMenu.setFixedCellHeight(20);
		pathMenu.setFixedCellWidth(100);
		pathMenu = new JList<String>(parsPath());
		jspPath = new JScrollPane(pathMenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		pathMenu.setVisibleRowCount(4);
		panelMenuDeroulant.add(pathMenuLabel,contrainte);
		contrainte.gridy = 2;
		panelMenuDeroulant.add(jspPath,contrainte);
		contrainte.gridy = 3;
//		pathMenu.addListSelectionListener(new ScrollMenuListener(scrollMenu, this));
	}
	
	void createSpeciesMenu() {
		speciesMenu = new JList<String>(espMenuList);
		speciesMenu = new JList<String>(parsSpecies());
		speciesMenu.setFixedCellHeight(20);
		speciesMenu.setFixedCellWidth(100);
		jspOrg = new JScrollPane(speciesMenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		speciesMenu.setVisibleRowCount(4);
//		speciesMenu.addListSelectionListener(new ScrollMenuListener(this));
		
		panelMenuDeroulant.add(speciesMenuLabel,contrainte);
		contrainte.gridy = 4;
		panelMenuDeroulant.add(jspOrg,contrainte);
	}
	
	/**
	 * Parser de la liste des voies a partir du fichier source
	 * @return liste des voies de signalisation
	 */
	public String[] parsPath() {
		File repertoire = new File("/Users/alexandrabenamar/Bacteria");
		File[] dossiers = repertoire.listFiles();
		HashSet <String> hashPaths = new HashSet<String>();
		List <String> listPaths = new ArrayList<>();
		File[] fichiers = null;
		for (File d : dossiers)
			if (d.isDirectory())
				fichiers = d.listFiles();
		for (File f : fichiers)
			hashPaths.add(f.getName().substring(0, 4)+f.getName().substring(9, 13));

		listPaths.clear();
		listPaths.addAll(hashPaths);
		Collections.sort(listPaths, new Comparator<String>() {
			@Override
			public int compare(String path1, String path2) {
				return  path1.compareTo(path2);
			}
		});

		String[] array = listPaths.toArray(new String[0]);
		return array;
	}
	
	/**
	 * Parser de la liste des especes a partir du fichier source
	 * @return liste des especes
	 */
	public String[] parsSpecies() {
		File repertoire = new File("/Users/alexandrabenamar/Bacteria");
		File[] dossiers = repertoire.listFiles();
		List<String> listEspeces = new ArrayList<String>();
		for (File d : dossiers)
			if (!d.getName().contains(".DS"))
				listEspeces.add(d.getName().substring(0, 3));

		String[] array = listEspeces.toArray(new String[0]);
		return array;
	}
	
	/**
	 * Recuperation de la liste des voies de signalisation
	 * @return liste des voies de signalisation
	 */
	public JList<String> getPathList(){
		return pathMenu;
	}

	/**
	 * Recuperation de la liste des especes
	 * @return liste des especes
	 */
	public JList<String> getSpeciesList(){
		return speciesMenu;
	}
	

	public JScrollPane getJspPath() {
		return jspPath;
	}

	public void setJspPath(JScrollPane jspPath) {
		this.jspPath = jspPath;
	}

	public JScrollPane getJspOrg() {
		return jspOrg;
	}

	public void setJspOrg(JScrollPane jspOrg) {
		this.jspOrg = jspOrg;
	}

	public JList<String> getPathMenu() {
		return pathMenu;
	}

	public void setPathMenu(JList<String> pathMenu) {
		this.pathMenu = pathMenu;
	}

	public JList<String> getSpeciesMenu() {
		return speciesMenu;
	}

	public void setSpeciesMenu(JList<String> speciesMenu) {
		this.speciesMenu = speciesMenu;
	}

	public DefaultListModel<String> getPathMenuList() {
		return pathMenuList;
	}

	public void setPathMenuList(DefaultListModel<String> pathMenuList) {
		this.pathMenuList = pathMenuList;
	}

	public DefaultListModel<String> getEspMenuList() {
		return espMenuList;
	}

	public void setEspMenuList(DefaultListModel<String> espMenuList) {
		this.espMenuList = espMenuList;
	}

	public JPanel getPanelMenuDeroulant() {
		return panelMenuDeroulant;
	}

	public void setPanelMenuDeroulant(JPanel panelMenuDeroulant) {
		this.panelMenuDeroulant = panelMenuDeroulant;
	}

	public JLabel getPathMenuLabel() {
		return pathMenuLabel;
	}

	public JLabel getSpeciesMenuLabel() {
		return speciesMenuLabel;
	}

	public Pathway getPathway() {
		return pathway;
	}

	public void setPathway(Pathway pathway) {
		this.pathway = pathway;
	}

	public JPanel getPanelGraphe() {
		return panelGraphe;
	}

	public void setPanelGraphe(JPanel panelGraphe) {
		this.panelGraphe = panelGraphe;
	}

	public JScrollPane getJspPanelGraph() {
		return jspPanelGraph;
	}

	public void setJspPanelGraph(JScrollPane jspPanelGraph) {
		this.jspPanelGraph = jspPanelGraph;
	}

	public GenericGraph getGraphic() {
		return graphic;
	}

	public void setGraphic(GenericGraph graphic) {
		this.graphic = graphic;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

}
