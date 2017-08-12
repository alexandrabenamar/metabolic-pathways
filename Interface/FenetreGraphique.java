package metabolique;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.Viewer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class FenetreGraphique extends JFrame implements ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 1L;
	
	/**
	 * voie de signalisation en cours de traitement
	 */
	protected Pathway pathway;

	/**
	 * barre d'outil de la fenetre graphique
	 */
	protected JMenuBar bar = new JMenuBar();
	/**
	 * menu "Cartes" de la fenetre graphique
	 */
	protected JMenu menuCartes = new JMenu("Cartes");
	/**
	 * menu "Capture de la fenetre graphique permettant de faire un screenshot
	 */
	protected JMenu menuCapture = new JMenu("Capture");
	/**
	 * menu aide de la fenetre graphique
	 */
	protected JMenu menuHelp = new JMenu("Help");
	/**
	 * sous-menu permettant d'afficher les cartes generiques
	 */
	protected JMenuItem itemGenerique = new JMenu("Générique");
	/**
	 * sous-menu permettant d'afficher les cartes specifiques
	 */
	protected JMenuItem itemSpecifique = new JMenu("Spécifique");
	/**
	 * sous-menu permettant les captures d'ecran
	 */
	protected JMenuItem itemCapture = new JMenu("Capture");
	/**
	 * sous-menu de l'aide
	 */
	protected JMenuItem itemHelp = new JMenu("Contact us");
	
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
	 * affichage du nom de la voie de signalisation
	 */
	protected JLabel NomDuPathway;

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

	/**
	 * Initialisation de la fenetre
	 */
	public FenetreGraphique(){
		super();
		build();
	}
	
	/**
	 * Fonction principale permettant d'afficher la fenetre
	 * @param args main
	 */
	public static void main(String[] args) {
		// Creation d'une nouvelle instance de la fenetre
		FenetreGraphique fenetre = new FenetreGraphique();
		fenetre.setVisible(true);//On la rend visible
	}

	/**
	 * Preparation de la fenetre
	 */
	private void build(){
		setTitle("Réseaux métaboliques");
		setSize(600,400); 
		setLocationRelativeTo(null); // fenetre centrée sur l'écran
		setResizable(true); // redimensionnement possible de la fenetre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // se ferme lors du clic sur la croix
		buildToolbar();
		buildDirectoryResearchBar();
		buildScrollMenu();
		buildGraphFrame();
	}

	/**
	 * Construction de la bare d'outils de la fenetre
	 */
	private void buildToolbar(){
		itemGenerique.addMouseListener(new SimpleListener());
		itemSpecifique.addMouseListener(new SimpleListener());
		itemCapture.addMouseListener(new SimpleListener());
		itemHelp.addMouseListener(new SimpleListener());
		menuCartes.add(itemGenerique);
		menuCartes.add(itemSpecifique);
		menuCapture.add(itemCapture);
		menuHelp.add(itemHelp);
		bar.add(menuCartes);
		bar.add(menuCapture);
		bar.add(menuHelp);
		setJMenuBar(bar);		
	}
	
	/**
	 * Construction de la barre de recherche du chemin
	 * du dossier contenant les fichiers de voies
	 */
	private void buildDirectoryResearchBar(){
		// Barre de recherche du chemin
				directory.add(directoryLabel);
				directory.add(textField);
				directory.add(Browse);
				add(directory,BorderLayout.NORTH);
				Browse.addActionListener(this);
	}
	
	/**
	 * Construction du menu deroulant contenant les voies et les especes
	 */
	private void buildScrollMenu(){
		
		// PATHMENU ET SPECIES MENU SE CHARGENT
		this.pathMenu = new JList<String>(pathMenuList);
		this.speciesMenu = new JList<String>(espMenuList);
		pathMenu.setFixedCellHeight(20);
		pathMenu.setFixedCellWidth(100);
		pathMenu = new JList<String>(parsPath());
		speciesMenu = new JList<String>(parsSpecies());
		speciesMenu.setFixedCellHeight(20);
		speciesMenu.setFixedCellWidth(100);
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
	private void buildGraphFrame() {
		// ON GERE LE PANEL DU GRAPHIQUE
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
		File repertoire = new File("/Users/utilisateur/Downloads/Bacteria");
		File[] dossiers = repertoire.listFiles();
		List<String> listEspeces = new ArrayList<String>();
		for (File d : dossiers)
			if (!d.getName().contains(".DS"))
				listEspeces.add(d.getName().substring(0, 3));
 
		String[] array = listEspeces.toArray(new String[0]);
		return array;
		
	}

	/**
	 * Parser de la liste des voies a partir du fichier source
	 * @return liste des voies de signalisation
	 */
	public String[] parsPath() {
		File repertoire = new File("/Users/utilisateur/Downloads/Bacteria");
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
	 * action sur la barre de recherche parcourir
	 */
	public void actionPerformed(ActionEvent e) {

		 JFileChooser Directory = new JFileChooser();
		 Directory.setCurrentDirectory(new File(System.getProperty("user.home")));
		 
		 Directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         
         int result = Directory.showSaveDialog(null);
          //if the user click on save in Jfilechooser
         if(result == JFileChooser.APPROVE_OPTION)
         {
             File selectedDirectory = Directory.getSelectedFile();
             String path = selectedDirectory.getAbsolutePath();
             textField.setText(path);
             
             NomDuPathway = new JLabel(path);
             NomDuPathway.repaint();
             NomDuPathway.validate();
     		 add(NomDuPathway,BorderLayout.SOUTH);
     		 
     		 // REFRESH THE WINDOW
     		NomDuPathway.repaint();
     		NomDuPathway.revalidate();
             
         }

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
			graphic.setDefaultStylesheet();
			graph = graphic.getGraphic();
			viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			viewer.disableAutoLayout();
			
	        ViewPanel view = viewer.addDefaultView(false);
	        
			// Add the view to self and make it visible
		    setVisible(true);
	        view.setSize(new Dimension(200, 200));
	        view.setBackground(Color.LIGHT_GRAY);
	        panelGraphe.setLayout(new BorderLayout());
	        panelGraphe.add(view, BorderLayout.CENTER);
	        
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
