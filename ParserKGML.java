package metabolique;
/**
 * KEGG parser in KGML model
 *
 * @author Alexandra Benamar
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserKGML {

	/**
	 * Liste de reactions de la voie de signalisation
	 */
	static List<Reaction> reactionList;

	/**
	 * Parse un fichier .kgml de KEGG
	 * @param file fichier KGML
	 */
	ParserKGML(File file) {
		
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

			// Get the pathway element
			final Element root = document.getDocumentElement();
			// Get the compounds elements
			final NodeList rootNodes = root.getElementsByTagName("entry");
			final int nbRootNodes = rootNodes.getLength();

			List<String> id_list = new ArrayList<>();
			// Go through the entry elements to save the elements
			for (int i = 0; i<nbRootNodes; i++) {
				if(rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					// Save a new element
					final Element element = (Element)rootNodes.item(i);
					List<String> graph_list = new ArrayList<>();
					id_list.add(element.getAttribute("id"));
					final NodeList graphNodes = element.getElementsByTagName("graphics");
					for (int k = 0; k<graphNodes.getLength(); k++) {
						if(graphNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
							final Element graph_element = (Element)graphNodes.item(k);
							graph_list.add(graph_element.getAttribute("x"));
							graph_list.add(graph_element.getAttribute("y"));
						}
					} id_list.addAll(graph_list);
				}
			}

			// Get the reaction elements
			final NodeList reactionNodes = root.getElementsByTagName("reaction");
			List<Reaction> reactions = new ArrayList<Reaction>();
			// Go through the entry elements to save the reactions
			for (int j = 0; j<reactionNodes.getLength(); j++) {

				if(reactionNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					// Save a new element
					final Element element = (Element)reactionNodes.item(j);
					// Go through the substrates of the reaction
					final NodeList substrateNodes = element.getElementsByTagName("substrate");
					// Creation of a substrates list for each reaction
					List<Compound> substrates = new ArrayList<Compound>();
					for (int k = 0; k<substrateNodes.getLength(); k++) {
						if(substrateNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
							final Element substrate_element = (Element)substrateNodes.item(k);
							Compound substrate = new Compound (substrate_element.getAttribute("id"), substrate_element.getAttribute("name").substring(3));

							boolean flag = false;
							while (!flag) {
								for (int g=0; g<(id_list.size()-2); g++) {
									if (id_list.get(g).matches(substrate.getId())) {
										substrate.setGraphCoords(Integer.parseInt(id_list.get(g+1)), Integer.parseInt(id_list.get(g+2)));
										flag = true;
									}
								}
							}
							substrates.add(substrate);
						}
					} // end substrates

					// Go through the products of the reaction
					final NodeList productNodes = element.getElementsByTagName("product");
					final int nbProductNodes = productNodes.getLength();

					// Creation of a product list for each reaction
					List<Compound> products = new ArrayList<Compound>();
					for (int l = 0; l<nbProductNodes; l++) {

						if(productNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {

							final Element product_element = (Element)productNodes.item(l);
							Compound product = new Compound (product_element.getAttribute("id"), product_element.getAttribute("name").substring(3));

							boolean flag = false;
							while (!flag) {
								for (int g=0; g<id_list.size(); g++) {
									if (id_list.get(g).matches(product.getId())) {
										product.setGraphCoords(Integer.parseInt(id_list.get(g+1)), Integer.parseInt(id_list.get(g+2)));
										flag = true;
									}
								}
							}
							products.add(product);
						}
					} // end products

					// Nouvelle reaction
					
				//	List<String> listName = new ArrayList<String>();
					if (element.getAttribute("name").length() == 9) {
						Reaction reaction = new Reaction(element.getAttribute("id"), element.getAttribute("name").substring(3), element.getAttribute("type") , substrates, products);
						reaction.setSubstrateList(substrates);
						reaction.setProductList(products);
						reactions.add(reaction);
					}
					else {
						
						Reaction reaction1 = new Reaction(element.getAttribute("id"), element.getAttribute("name").substring(3, 9), element.getAttribute("type") , substrates, products);
						reaction1.setProductList(products);
						reaction1.setSubstrateList(substrates);
						
						Reaction reaction2 = new Reaction(element.getAttribute("id"), element.getAttribute("name").substring(13, 19), element.getAttribute("type") , substrates, products);
						reaction2.setSubstrateList(substrates);
						reaction2.setProductList(products);
						
						reactions.add(reaction1);
						reactions.add(reaction2);
						
					//	listName.add(element.getAttribute("name").substring(3, 9));
					//	listName.add(element.getAttribute("name").substring(13, 19));
					}
				}
			} // end reactions

			// creation of the pathway 
			@SuppressWarnings("unused")
			Pathway pathway = new Pathway(Integer.parseInt(root.getAttribute("number")), root.getAttribute("name").substring(5), root.getAttribute("title"), reactions);
			this.setReactionList(reactions);

		} // end try

		catch (final ParserConfigurationException e) {  // Indicates a serious configuration error
			e.printStackTrace();
		} catch (final SAXException e) {  // Encapsulate a general SAX error or warning.
			e.printStackTrace();
		} catch (final IOException e) {  // Signals that an I/O exception of some sort has occurred.
			e.printStackTrace();		 // This class is the general class of exceptions produced by
		}								 // failed or interrupted I/O operations.
	}

	/**
	 * Recuperation de la liste des reactions de la voie
	 * @return
	 */
	public List<Reaction> getReactionList() {
		return reactionList;
	}

	/**
	 * Recuperation de la liste des reactions de la voie
	 * @param list
	 */
	public void setReactionList(List<Reaction> list) {
		reactionList = list;
	}

} // end ParserKGML
