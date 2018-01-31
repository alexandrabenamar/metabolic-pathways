package metabolique;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ToolbarBrowser {
	
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
	 * Construction de la barre d'outils de la fenetre
	 */
	ToolbarBrowser(){
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
	}
	
	public JMenuItem getItemGenerique() {
		return itemGenerique;
	}



	public void setItemGenerique(JMenuItem itemGenerique) {
		this.itemGenerique = itemGenerique;
	}



	public JMenuItem getItemSpecifique() {
		return itemSpecifique;
	}



	public void setItemSpecifique(JMenuItem itemSpecifique) {
		this.itemSpecifique = itemSpecifique;
	}



	public JMenuItem getItemCapture() {
		return itemCapture;
	}



	public void setItemCapture(JMenuItem itemCapture) {
		this.itemCapture = itemCapture;
	}



	public JMenuItem getItemHelp() {
		return itemHelp;
	}



	public void setItemHelp(JMenuItem itemHelp) {
		this.itemHelp = itemHelp;
	}



	public JMenu getMenuCartes() {
		return menuCartes;
	}



	public void setMenuCartes(JMenu menuCartes) {
		this.menuCartes = menuCartes;
	}



	public JMenu getMenuCapture() {
		return menuCapture;
	}



	public void setMenuCapture(JMenu menuCapture) {
		this.menuCapture = menuCapture;
	}



	public JMenu getMenuHelp() {
		return menuHelp;
	}



	public void setMenuHelp(JMenu menuHelp) {
		this.menuHelp = menuHelp;
	}



	public void setBar(JMenuBar bar) {
		this.bar = bar;
	}



	JMenuBar getBar() {
		return this.bar;
	}

}
