package metabolique;
import java.util.List;

/**
 * Add KEGG pathway in KGML Model
 * 
 * @author Alexandra Benamar
 *
 */

public class Pathway {

	//---------------------------------------
	// variables d'instance
	//---------------------------------------

	/**
	 * unique pathway's id
	 */
	protected int number;
	
	/**
	 * principal pathway's name
	 */
	protected String name;
	
	/**
	 * pathway's title
	 */
	protected String title;
	
	/**
	 * second pathway's name
	 */
	protected String alt_name;

	/**
	 * list of the pathway's reactions
	 */
	protected List<Reaction> reactionList;
	
	protected List<String> espece;
	
	protected GenericGraph graph;


	//---------------------------------------
	// Constructors
	//---------------------------------------

	public Pathway() {}

	/**
	 * Creates a pathway with an unique number and a name
	 * @param number pathway's number (id)
	 * @param name pathway's name
	 */
	public Pathway(int number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	/**
	 * Creates a pathway with an unique number, a name, a title, and a reaction's list
	 * @param number pathway's number (id)
	 * @param name pathway's name
	 * @param title pathway's title
	 * @param reactionList pathway's reactions' list
	 */
	public Pathway(int number, String name, String title, List<Reaction> reactionList) {
		super();
		this.number = number;
		this.name = name;
		this.title = title;
		this.reactionList = reactionList;
	}
	
	public Pathway(int number, String name, String title, List<Reaction> reactionList, List<String> espece) {
		super();
		this.number = number;
		this.name = name;
		this.title = title;
		this.reactionList = reactionList;
		this.espece = espece;
	}


	//---------------------------------------
	// Methods
	//---------------------------------------

	/**
	 * Get the pathway's principal name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the pathway's principal name
	 * @param name pathway's principal name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the pathway's title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the pathway's title
	 * @param title pathway's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the pathway's second name
	 * @return alt_name
	 */
	public String getAltName() {
		return alt_name;
	}

	/**
	 * Set the pathway's second name
	 * @param alt_name pathway's second name
	 */
	public void setAltName(String alt_name) {
		this.alt_name = alt_name;
	}

	/**
	 * Get the pathway's number (id)
	 * @return number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Set the pathway's number (id)
	 * @param number pathway's number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Get the pathway's reactions' list
	 * @return reactionList
	 */
	public List<Reaction> getReactionList() {
		return reactionList;
	}

	/**
	 * Set the pathway's reactions' list
	 * @param reactionList pathway's reactions' list
	 */
	public void setReactionList(List<Reaction> reactionList) {
		this.reactionList = reactionList;
	}
	
	

	public String getAlt_name() {
		return alt_name;
	}

	public void setAlt_name(String alt_name) {
		this.alt_name = alt_name;
	}

	public List<String> getEspece() {
		return espece;
	}

	public void setEspece(List<String> espece) {
		this.espece = espece;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alt_name == null) ? 0 : alt_name.hashCode());
		result = prime * result + ((espece == null) ? 0 : espece.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		result = prime * result + ((reactionList == null) ? 0 : reactionList.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pathway other = (Pathway) obj;
		if (alt_name == null) {
			if (other.alt_name != null)
				return false;
		} else if (!alt_name.equals(other.alt_name))
			return false;
		if (espece == null) {
			if (other.espece != null)
				return false;
		} else if (!espece.equals(other.espece))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		if (reactionList == null) {
			if (other.reactionList != null)
				return false;
		} else if (!reactionList.equals(other.reactionList))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public GenericGraph getGraph() {
		return graph;
	}
	
	public void setGraph(GenericGraph g) {
		this.graph = g;
	}
	

}
