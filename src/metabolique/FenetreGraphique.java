package metabolique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.Viewer;

public class FenetreGraphique extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

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
	 * creation du viewer pour le graphique
	 */
	protected Viewer viewer;

	protected DirectoryResearchBar directoryResearchBar;

	protected ScrollMenu scrollMenu;

	protected File directory;

	/**
	 * Initialisation de la fenetre
	 */
	public FenetreGraphique(){
		super();
		setVisible(true);
		build();
	}

	protected ToolbarBrowser toolbar;

	/**
	 * Fonction principale permettant d'afficher la fenetre
	 * @param args main
	 */
	public static void main(String[] args) {
		FenetreGraphique fenetre = new FenetreGraphique();
		fenetre.setVisible(true);
	}

	/**
	 * Preparation de la fenetre
	 */
	public void build(){
		setTitle("Réseaux métaboliques");
		setSize(600,400);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildToolbar();
		buildDirectoryResearchBar();
		buildScrollMenu();
		buildGraphFrame();
	}

	/**
	 * Construction de la barre d'outils de la fenetre
	 */
	public void buildToolbar(){
		toolbar = new ToolbarBrowser();
		setJMenuBar(toolbar.getBar());		
	}

	/**
	 * Construction de la barre de recherche du chemin
	 * contenant les fichiers de voies
	 */
	public void buildDirectoryResearchBar(){
		directoryResearchBar = new DirectoryResearchBar();
		add(directoryResearchBar.getDirectory(),BorderLayout.NORTH);
		this.directory = directoryResearchBar.getDirectoryName();
	}


	/**
	 * Construction du menu deroulant contenant les voies et les especes
	 */
	public void buildScrollMenu(){

		// PATHMENU ET SPECIES MENU SE CHARGENT
		this.pathMenu = new JList<String>(pathMenuList);
		this.speciesMenu = new JList<String>(espMenuList);
	//	pathMenu.setFixedCellHeight(20);
	//	pathMenu.setFixedCellWidth(100);
		pathMenu = new JList<String>(parsPath());
		speciesMenu = new JList<String>(parsSpecies());
	//	speciesMenu.setFixedCellHeight(20);
	//	speciesMenu.setFixedCellWidth(100);
		jspPath = new JScrollPane(pathMenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		jspOrg = new JScrollPane(speciesMenu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pathMenu.setVisibleRowCount(4);
 		speciesMenu.setVisibleRowCount(4);

 		// ON GERE LES CONTRAINTES SUR LES MENUS
 		GridBagConstraints contrainte = new GridBagConstraints();
		contrainte.insets = new Insets(5,5,5,5);
		contrainte.gridx = 0; contrainte.gridy = 1;
		panelMenuDeroulant.add(pathMenuLabel,contrainte);
		contrainte.gridy = 2;
		panelMenuDeroulant.add(jspPath,contrainte);
		contrainte.gridy = 3;
		panelMenuDeroulant.add(speciesMenuLabel,contrainte);
		contrainte.gridy = 4;
		panelMenuDeroulant.add(jspOrg,contrainte);
		// AJOUT DES MENUS A LA FENETRE
		add(panelMenuDeroulant,BorderLayout.WEST);
		// CREATION DES LISTENERS SUR LES MENUS
		pathMenu.addListSelectionListener(this);
 		speciesMenu.addListSelectionListener(this);
	}

	/**
	 * Construction de la fenetre d'affichage du graphique
	 */
	public void buildGraphFrame() {
		panelGraphe.setBackground(Color.white);
		panelGraphe.setPreferredSize(new Dimension(200,200));
		jspPanelGraph= new JScrollPane(panelGraphe, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
		add(jspPanelGraph,FlowLayout.RIGHT);
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
				listEspeces.add(d.getName().substring(4));

		String[] array = listEspeces.toArray(new String[0]);
		return array;
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

	/**
	 * listener sur les menus : menu de la voie de signalisation et menu de l'espece
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == pathMenu && !e.getValueIsAdjusting())
		{			
			String selectedValue = (String) this.getPathList().getSelectedValue().toString().substring(4);

			graphic = new GenericGraph(selectedValue);
			viewer = new Viewer(graphic.getGraphic(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			viewer.disableAutoLayout();
			
	        ViewPanel view = viewer.addDefaultView(false);

			// Add the view to self and make it visible
		    setVisible(true);
	        view.setSize(new Dimension(200, 200));
	        view.setBackground(Color.LIGHT_GRAY);
	        panelGraphe.setLayout(new BorderLayout());
	        panelGraphe.add(view, BorderLayout.CENTER);
	        panelGraphe.setVisible(true);
	        			
	         // Refresh the window
	         repaint();
	         revalidate();
	    }

		if (e.getSource() == speciesMenu && !e.getValueIsAdjusting())
		{
			graphic.setDefaultStylesheet();
			String selectedValue = (String) this.getSpeciesList().getSelectedValue().toString();
			graphic.updateStylesheet(selectedValue);
		}
	}

}
