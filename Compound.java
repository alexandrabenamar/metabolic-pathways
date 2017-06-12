package metabolique;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Ajout d'un metabolite avec KEGG
 *
 * @author Alexandra Benamar
 */

public class Compound {

	//---------------------------------------
	// variables d'instance
	//---------------------------------------

	/**
	 * (unique) compound's id
	 */
	protected final String id;

	/**
	 * compound's principal name
	 */
	protected String name;

	/**
	 * compound's second name
	 */
	protected String alt_name;
	
	/**
	 * abscisse du metabolite sur le graphique
	 */
	protected int graphX;


	/**
	 * ordonnee du metabolite sur le graphique
	 */
	protected int graphY;

	/**
	 * fichier du metabolite 
	 */
	protected File output;


	//---------------------------------------
	// Constructors
	//---------------------------------------

	/**
	 * Creation d'un metabolite avec un identifiant et un nom principal
	 * @param id identifiant du metabolite
	 * @param name nom principal du metabolite
	 */
	public Compound (String id, String name) {
		this.name = name.substring(1);
		this.id = id;
		parsAltName();
	}

	/**
	 * Creating a compound with an id, a principal compound and a secondary compound
	 * @param id identifiant du metabolite
	 * @param name nom principal du metabolite
	 * @param alt_name nom secondaire du metabolite
	 */
	public Compound (String id, String name, String alt_name) {
		this.name = name.substring(1);
		this.id = id;
		this.alt_name = alt_name;
	}


	//---------------------------------------
	// Methods
	//---------------------------------------

	/**
	 * Recupere l'identifiant du metabolite
	 * @return id du metabolite
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Recupere le nom du metabolite
	 * @return name nom du metabolite
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Fixe le nom principal du metabolite
	 * @param name nom principal du metabolite
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Recupere le nom secondaire du metabolites des fichiers compounds
	 * @return alt_name nom secondaire du metabolite
	 */
	public String getAltName() {
		return this.alt_name;
	}

	/**
	 * Fixe le nom secondaire du metabolite
	 * @param alt_name nom secondaire du metabolite
	 */
	public void setAltName(String alt_name) {
		this.alt_name = name;
	}

	/**
	 * Recupere l'abscisse du metabolite sur le graphique
	 * @return abscisse du metabolite sur le graphique
	 */
	public int getGraphY() {
		return graphY;
	}

	/**
	 * Recupere l'ordonnee du metabolite sur le graphique
	 * @return ordonnee du metabolite sur le graphique
	 */
	public int getGraphX() {
		return graphX;
	}


	/**
	 * Fixe la position du metabolite sur le graphique
	 * @param graphX abcisse compound
	 * @param graphY ordonnee compound
	 */
	public void setGraphCoords(int graphX, int graphY) {
		this.graphX = graphX;
		this.graphY = graphY;
	}

	/**
	 * Recuperation du nom du metabolite dans les fichiers compounds
	 */
	public void parsAltName() {

		File repertoire = new File("/Users/utilisateur/Downloads/compounds");

		File[] fichiers = repertoire.listFiles();

		for (File fichier : fichiers) { 
			// Appel récursif sur les sous-répertoires 
			if (fichier.getName().contains(this.name)) {
				this.output = fichier;
			}
		}

		Scanner scanner = null;
		try {
			scanner = new Scanner(this.output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		while(scanner.hasNext()){
			String str = scanner.findInLine("NAME");
			if (str != null){
				String[] tokens = scanner.nextLine().split(" ");
				for (String element : tokens) {
					if (element != "NAME") {
						if (element.endsWith(";")) {
							this.alt_name = element.substring(0, element.length()-1);
						}
						else this.alt_name = element;
					}
				}
			}
			scanner.nextLine();
		}
	}

}
