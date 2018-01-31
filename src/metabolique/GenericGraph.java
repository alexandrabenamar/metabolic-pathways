package metabolique;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.graphstream.graph.*;

public class GenericGraph {

	protected SpecificGraph g;
	protected Graph graphic;
	protected Pathway pathway;

	/**
	 * Creation de la carte generique de la voie
	 * @param nom de la voie de signalisation
	 **/
	public GenericGraph(String pathName) {
		this.pathway = createPathway(pathName);
		this.g = new SpecificGraph(pathway);
//		graphic = g.getGraphic();
		this.graphic = g;
	}
	
	public Pathway createPathway(String pathName) {
				
		List <Reaction> list = new ArrayList<>();
		HashSet <Reaction> listReaction = new HashSet<Reaction>();
		HashSet <String> listEspeces = new HashSet<String>();
		
		ParserKGML pars = null;

		File repertoire = new File ("/Users/alexandrabenamar/Bacteria"); //parcourir le répertoire
		for (File f : repertoire.listFiles()) {
			if (f.isDirectory()) {	
				for (File file : f.listFiles()) {		//parcourir les fichiers du répertoire
					if (file.getName().contains(pathName)) {	//recherche des fichiers de la voie 
						pars = new ParserKGML(file);	//parse le fichier
						List <Reaction> l = pars.getReactionList();	// obtient la liste des réactions du fichier
						listReaction.addAll(l);
						listEspeces.add(file.getName().substring(5, 8));	// ajoute l'espèce associée au fichier
					}
				}
			}
		}

		list.addAll(listReaction);
		
		List <String> especes = new ArrayList<>();
		especes.addAll(listEspeces);
		Pathway pathway = new Pathway(pars.getPathway().getNumber(), pathName, pars.getPathway().getTitle(), list, especes);
		
		return pathway;
	}

	/**
	 * Recuperation du graphique
	 * @return
	 */
	public SpecificGraph getG() {
		return g;
	}

	/**
	 * Fixation du graphique
	 * @param g graphique
	 */
	public void setG(SpecificGraph g) {
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
