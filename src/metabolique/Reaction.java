package metabolique;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Creation d'une reaction dans un model KGML
 * 
 * @author Alexandra Benamar
 */

public class Reaction {
	
	/**
	 *  two types of reactions : reversible and irreversible
	 * @author Alexandra Benamar
	 */
	public enum Type {
		Reversible,
		Irreversible;
	}

	//---------------------------------------
	// variables d'instance
	//---------------------------------------

	/**
	 * identifiant de la reaction
	 */
	protected String id;

	/**
	 * nom de la reaction
	 */
	protected String name;
	
	/**
	 * type de la reaction : reversible || irreversible
	 */
	protected Type reactionType;
	
	/**
	 * type de la reaction : reversible || irreversible
	 */
	protected String type;

	/**
	 * liste des substrats de la reaction
	 */
	protected List<Compound> substrateList;
	
	/**
	 * liste des produits de la reaction
	 */
	protected List<Compound> productList;
	
	/**
	 * liste des enzymes de la reaction
	 */
	protected List<String> enzymeList;
	
	/**
	 * liste des especes qui ont cette reaction
	 */
	protected List <String> especes = new ArrayList<>();

	/**
	 * fichier contenant les informations sur la reaction
	 */
	protected File output;

	//---------------------------------------
	// Constructors
	//---------------------------------------

	public Reaction() {}

	/**
	 * Creation d'une reaction a partir d'un identifiant, d'un nom,
	 * d'un type, d'une liste de substrats et d'une liste de produits
	 * @param id identifiant de la reaction
	 * @param name nom de la reaction
	 * @param reactionType type de la reaction : reversible || irreversible
	 * @param substrates liste des substrats de la reaction
	 * @param products liste des produits de la reaction
	 */
	public Reaction(String id, String name, String reactionType, List<Compound> substrates, List<Compound> products) {
		super();
		this.id = id;
		this.name = name;
		switch (reactionType) {
	      case "reversible": 
	    	  this.reactionType = Type.Reversible;
	    	  this.type = "reversible";
	    	  break;
	      case "irreversible":
	    	  this.reactionType = Type.Irreversible;
	    	  this.type = "irreversible";
	    	  break;
	    }
		this.substrateList = substrates;
		this.productList = products;
			
		getEnzyme(name);
	}


	//---------------------------------------
	// Methods
	//---------------------------------------

	/**
	 * Get the reaction's id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the reaction's id
	 * @param id reaction's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the reaction principal name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the reaction's principal name
	 * @param name reaction's principal name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Recuperation du type de la reaction
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Fixation du type de la reaction
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the reaction's type : reversible || irreversible
	 * @return reactionType
	 */
	public Type getReactionType() {
		return reactionType;
	}

	/**
	 * Set the reaction's type : reversible || irreversible
	 * @param reactionType reaction's type
	 */
	public void setReactionType(Type reactionType) {
		this.reactionType = reactionType;
	}

	/**
	 * Get the reaction's substrates' list
	 * @return substrateList
	 */
	public List<Compound> getSubstrateList() {
		return substrateList;
	}

	/**
	 * Set the reaction's substrates' list
	 * @param substrateList reaction's substrates' list
	 */
	public void setSubstrateList(List<Compound> substrateList) {
		this.substrateList = substrateList;
	}

	/**
	 * Get the reaction's products' list
	 * @return productList
	 */
	public List<Compound> getProductList() {
		return productList;
	}

	/**
	 * Set the reaction's products' list
	 * @param productList reaction's products' list
	 */
	public void setProductList(List<Compound> productList) {
		this.productList = productList;
	}

	/**
	 * Recupere la liste d'enzymes de la reaction
	 * @return productList liste d'enzymes de la reaction
	 */
	public List<String> getEnzymeList() {
		return enzymeList;
	}

	/**
	 * Fixe la liste des enzymes de la reaction
	 * @param enzymeList
	 */
	public void setEnzymeList(List<String> enzymeList) {
		this.enzymeList = enzymeList;
	}

	/**
	 * Recupere la liste des especes qui ont la reaction
	 * @return especes qui ont la reaction
	 */
	public List<String> getEspece() {
		return especes;
	}

	/**
	 * Fixe la liste des especes qui ont la reaction
	 * @param especes qui ont la reaction
	 */
	public void setEspece(List<String> especes) {
		this.especes = especes;
	}
	
	/**
	 * Ajout d'une espece sur la reaction
	 * @param espece qui possede la reaction
	 */
	public void addEspece(String espece) {
		this.especes.add(espece);
	}
	
	/**
	 * Fixe les enzymes de la reaction
	 * @param fileName 
	 */
	public void getEnzyme(String reactionName) {
		
		File repertoire = new File("/Users/alexandrabenamar/reactions");
		File[] fichiers = repertoire.listFiles();
		
		for (File fichier : fichiers) { 
			// Appel récursif sur les sous-répertoires 
			if (fichier.getName().contains(reactionName)) {
				this.output = fichier;
			}
		}
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(this.output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		List <String> temp = new ArrayList<String>();

		while(scanner.hasNext()){
	        String[] tokens = scanner.nextLine().split(" ");
	        for (String element : tokens) {
	        	String last1 = element;
	        	if ((last1.length() - last1.replace(".", "").length()==3)&&
	        			(!(last1.contains("[")))&&(!(last1.contains("]")))&&
	        			(!(last1.contains("EC"))&&(!(last1.contains(","))))) {
		        	temp.add(last1);
		        }   
	        }
	    }
		this.enzymeList = temp;	
	}
	
}
