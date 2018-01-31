package metabolique;

import org.graphstream.graph.*;

import org.graphstream.graph.implementations.MultiGraph;

public class SpecificGraph extends MultiGraph {

	private Pathway pathway;

	SpecificGraph(Pathway pathway) {

		super(pathway.getTitle());
		super.setNullAttributesAreErrors(true);

		this.pathway = pathway;

		addAttribute("ui.quality");
		addAttribute("ui.antialias");
		
		setDefaultStylesheet();

		createGraph();
	}
	
	public void createGraph() {
		for (Reaction reaction : pathway.getReactionList())
			addReaction(reaction);
	}

	public void addReaction(Reaction reaction) {

		addEnzymeNode(reaction);

		for (Compound substrate : reaction.getSubstrateList()) {
			addSubstrateNode(reaction, substrate);

			for (Compound product : reaction.getProductList()) {
				addProductNode(reaction, product);
				setPositionAttribute(reaction, substrate, product);
			}
		}
	}

	public void addEnzymeNode(Reaction reaction) {
		if (getNode(reaction.getName()+"_"+reaction.enzymeList.get(0)) == null)
			addNode(reaction.getName()+"_"+reaction.enzymeList.get(0));
	}

	public void setPositionAttribute(Reaction reaction, Compound substrate, Compound product) {
		Node enzyme = getNode(reaction.getName()+"_"+reaction.enzymeList.get(0));
		enzyme.setAttribute("ui.class", "enzyme");
		enzyme.setAttribute("ui.label", reaction.getName()+"_"+reaction.enzymeList.get(0));
		enzyme.setAttribute("xy", (substrate.getGraphX() + product.getGraphX())/2, -(substrate.getGraphY() + product.getGraphY())/2);
	}

	public void addSubstrateNode(Reaction reaction, Compound substrate) {

		if(getNode(substrate.getAltName()) == null) {
			addNode(substrate.getAltName());
			addCompoundsAttributes(substrate);
		}

		if (reaction.type == "reversible") {
			try {
				addEdge("From+"+reaction.getName()+"_"+reaction.enzymeList.get(0)+"+To+"+substrate.getAltName(),
						reaction.getName()+"_"+reaction.enzymeList.get(0), substrate.getAltName(), true);
			} catch(IdAlreadyInUseException e) {}
		}

		else {
			try {
				addEdge("From+"+reaction.getName()+"_"+reaction.enzymeList.get(0)+"+To+"+substrate.getAltName(),
						reaction.getName()+"_"+reaction.enzymeList.get(0), substrate.getAltName());
			} catch(IdAlreadyInUseException e) {}
		}
	}

	public void addProductNode(Reaction reaction, Compound product) {

		if(getNode(product.getAltName()) == null) {
			addNode(product.getAltName());
			addCompoundsAttributes(product);
		}

		try {
			addEdge("From+"+reaction.getName()+"_"+reaction.enzymeList.get(0)+"+To+"+product.getAltName(),
					reaction.getName()+"_"+reaction.enzymeList.get(0), product.getAltName(), true);
		} catch(IdAlreadyInUseException e) {}
	}


	public void addCompoundsAttributes(Compound compound) {
		getNode(compound.getAltName()).setAttribute("ui.class", "compound");
		getNode(compound.getAltName()).addAttribute("ui.label", compound.getAltName());
		getNode(compound.getAltName()).addAttribute("xy", compound.getGraphX(), -compound.getGraphY());
	}

	/**
	 * Creation d'un stylesheet par defaut
	 */
	public void setDefaultStylesheet() {

		setAttribute("stylesheet", "node { "
			//	+ "size: 10px;"
				+ "size: 10px, 10px;"
				+ "fill-mode: dyn-plain; "
			//	+ "stroke-mode: plain;"
			//	+ "stroke-color: black;"
				+ "stroke-mode: plain;"
				+ "stroke-width: 1px;"
				+ "text-alignment: under;"
				+ "} "
				+ "node.enzyme {"
				+ "shape: box;"
			//	+ "size: 10px, 10px;"
				+ "text-alignment: under;"
				+ "fill-color: #DEE;"
				+ "} "
				+ "node.compound { "
			//	+ "size: 10px, 10px;"
				+ "shape: circle;"
				+ "text-alignment: under;"
				+ "}");
	}

	/**
	 * Mise a jour du stylesheet pour les cartes specifiques
	 * @param path voie de signalisation du graphique
	 * @param espece souhaitee pour la carte specifique
	 */
	public void updateStylesheet(Pathway pathway, String espece) {
		for (Reaction r : pathway.getReactionList()) {
			for (String org : r.getEspece()) {
				if (org.equals(espece)) {
					getNode(r.getEnzymeList().get(0)).setAttribute("ui.class", "enzyme");
				}
			}
		}
	}

	public Pathway getPathway() {
		return pathway;
	}

	public void setPathway(Pathway pathway) {
		this.pathway = pathway;
	}

}