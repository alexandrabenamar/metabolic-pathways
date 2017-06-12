package metabolique;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;


public class Graphics {
	
	/**
	 * graphique de la voie de signalisation
	 */
	Graph g;
	
	/**
	 * Creation du graphique de la voie
	 * @param path voie de signalisation 
	 */
	Graphics(Pathway path) {

		g = new MultiGraph(path.getTitle());
		g.setNullAttributesAreErrors(true);
		
		setDefaultStylesheet();
		
		g.addAttribute("ui.quality");
		g.addAttribute("ui.antialias");

		List <Reaction> temp = new LinkedList<>();

		for(int i=0; i<path.getReactionList().size(); i++) {

			Reaction r = path.reactionList.get(i);

			if (!(temp.contains(r))) {

				for (Compound substrate_compound : r.substrateList) {

					if (g.getNode(substrate_compound.getAltName()) == null) { 
						Node substrat = g.addNode(substrate_compound.getAltName());
						substrat.setAttribute("ui.class", "compound");
						substrat.addAttribute("ui.label", substrate_compound.getAltName());
						substrat.addAttribute("xy", substrate_compound.getGraphX(), -substrate_compound.getGraphY());
						substrat.addAttribute("ui.style", "fill-color: red;");
					}

					for (Compound product_compound : r.productList) {

						if (g.getNode(product_compound.getAltName()) == null) {
							Node product = g.addNode(product_compound.getAltName());
							product.setAttribute("ui.class", "compound");
							product.setAttribute("ui.label", product_compound.getAltName());
							product.setAttribute("xy", product_compound.getGraphX(), -product_compound.getGraphY());
							product.setAttribute("ui.style", "fill-color: red;");
						}
						
						if (g.getNode(r.getEnzymeList().get(0)) == null) {
							Node enzyme = g.addNode(r.getEnzymeList().get(0));
							enzyme.setAttribute("ui.class", "enzyme");
							enzyme.setAttribute("ui.label", r.getEnzymeList().get(0));
							enzyme.setAttribute("xy", (substrate_compound.getGraphX() + product_compound.getGraphX())/2, -(substrate_compound.getGraphY() + product_compound.getGraphY())/2);
						//	enzyme.setAttribute("ui.style", "fill-color: blue;");
						}

						if (g.getNode(substrate_compound.getAltName()).hasEdgeBetween(r.getEnzymeList().get(0))) {
							if (g.getNode(r.getEnzymeList().get(0)).hasEdgeToward(substrate_compound.getAltName())) {
								g.getNode(r.getEnzymeList().get(0)).getEdgeToward(substrate_compound.getAltName());
							}
							if (g.getNode(r.getEnzymeList().get(0)).hasEdgeToward(product_compound.getAltName())) {
								g.getNode(r.getEnzymeList().get(0)).getEdgeToward(product_compound.getAltName());
							}
							g.getNode(substrate_compound.getAltName()).getEdgeBetween(r.getEnzymeList().get(0));
							g.getNode(product_compound.getAltName()).getEdgeBetween(r.getEnzymeList().get(0));

						}

						if (r.getType() == "reversible") {
							g.addEdge(UUID.randomUUID().toString(), r.getEnzymeList().get(0), substrate_compound.getAltName(), true);
							g.addEdge(UUID.randomUUID().toString(), r.getEnzymeList().get(0), product_compound.getAltName(), true);

						} else if (r.getType() == "irreversible") {
							g.addEdge(UUID.randomUUID().toString(), r.getEnzymeList().get(0), substrate_compound.getAltName());
							g.addEdge(UUID.randomUUID().toString(), r.getEnzymeList().get(0), product_compound.getAltName(), true);
						}
					}


				}
			} else {
				temp.add(r);
			}
		}
		
	}

	/**
	 * Recuperation du graphique
	 * @return graphique
	 */
	public Graph getGraphic() {
		return g;
	}

	/**
	 * Fixation du graphique
	 * @param graphic graphique 
	 */
	public void setGraphic(Graph graphic) {
		this.g = graphic;
	}
	
	/**
	 * Creation d'un stylesheet par defaut
	 */
	public void setDefaultStylesheet() {
		
		g.setAttribute("stylesheet", "node { "
				+ "size: 10px;"
				+ "fill-mode: dyn-plain; "
	    		+ "stroke-mode: plain;"
	    		+ "stroke-color: black;"
	    		+ "stroke-mode: plain;"
	    		+ "stroke-width: 1px;"
	    		+ "text-alignment: under;"
	    		+ "} "
	    		+ "node.enzyme {"
	    		+ "shape: box;"
	    		+ "size: 10px, 10px;"
	    		+ "text-alignment: under;"
	    		+ "} "
	    		+ "node.compound { "
	    		+ "shape: circle;"
	    		+ "text-alignment: under;"
	    		+ "}");
		
	}
	
	/**
	 * Mise a jour du stylesheet pour les cartes specifiques
	 * @param path voie de signalisation du graphique
	 * @param espece souhaitee pour la carte specifique
	 */
	public void updateStylesheet(Pathway path, String espece) {
		for (Reaction r : path.getReactionList()) {
			for (String org : r.getEspece()) {
				if (org.equals(espece)) {
					g.getNode(r.getEnzymeList().get(0)).setAttribute("ui.class", "enzyme");
					g.getNode(r.getEnzymeList().get(0)).setAttribute("ui.style", "fill-color: blue;");
				}
			}
		}
	}
	
}
