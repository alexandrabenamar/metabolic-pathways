package metabolique;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.graphstream.graph.Graph;


public class GenericGraph {

	protected Graphics g;
	protected Graph graphic;
	protected Pathway pathway;

	/**
	 * Creation de la carte generique de la voie
	 * @param nom de la voie de signalisation
	 **/
	public GenericGraph(String pathName) {

		List <Reaction> list = new ArrayList<>();
		HashSet <Reaction> listReaction = new HashSet<Reaction>();
		HashSet <String> listEspeces = new HashSet<String>();

		File repertoire = new File ("/Users/utilisateur/Downloads/Bacteria");
		for (File f : repertoire.listFiles()) {
			if (f.isDirectory()) {
				for (File file : f.listFiles()) {
					if (file.getName().contains(pathName)) {
						ParserKGML pars = new ParserKGML(file);
						List <Reaction> l = pars.getReactionList();
						listEspeces.add(file.getName().substring(5, 8));
						for (Reaction r: l) {
							listReaction.add(r);
							r.addEspece(file.getName().substring(5, 8));
						}
					}
					
				}

			}
		}
		
		list.clear();
		list.addAll(listReaction);
		
		List <String> especes = new ArrayList<>();
		especes.addAll(listEspeces);
		Pathway path = new Pathway(3, pathName, "glycolyse", list, especes);
		this.pathway = path;
		g = new Graphics(path);		
		graphic = g.getGraphic();

	}

	/**
	 * Recuperation du graphique
	 * @return
	 */
	public Graphics getG() {
		return g;
	}

	/**
	 * Fixation du graphique
	 * @param g graphique
	 */
	public void setG(Graphics g) {
		this.g = g;
	}

	/**
	 * Recuperation du graphique
	 * @return graphique
	 */
	public Graph getGraphic() {
		return graphic;
	}

	/**
	 * fixation du graphique
	 * @param graphic
	 */
	public void setGraphic(Graph graphic) {
		this.graphic = graphic;
	}
	
	/**
	 * fixation d'un stylesheet par defaut
	 */
	public void setDefaultStylesheet() {
		g.setDefaultStylesheet();
	}
	
	/**
	 * mise a jour du stylesheet pour graphique specialise
	 * @param espece souhaitee
	 */
	public void updateStylesheet(String espece) {
		g.updateStylesheet(pathway, espece);
	}

}
