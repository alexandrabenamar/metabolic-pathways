package metabolique;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Enzyme extends Reaction {
	
	/**
	 * fichier de la reaction associee a l'enzyme
	 */
	protected File output;
	
	/**
	 * liste d'enzymes dans le fichier de la reaction
	 */
	protected List <String> enzymeList = new ArrayList<>();
	
	/**
	 * Creation d'une enzyme a partir du fichier de la reaction
	 * a laquelle elle est associee
	 * @param fileName nom du fichier de la reaction associee
	 */
	Enzyme(String fileName) {
		
		
		
		File repertoire = new File("/Users/utilisateur/Downloads/reactions");
		
		File[] fichiers = repertoire.listFiles();
		
		for (File fichier : fichiers) { 
			// Appel récursif sur les sous-répertoires 
			if (fichier.getName().contains(fileName)) {
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
	        	if ((last1.length() - last1.replace(".", "").length()==3)&&(!(last1.contains("[")))&&(!(last1.contains("]")))&&(!(last1.contains("EC"))&&(!(last1.contains(","))))) {
		        	temp.add(last1);
		        }   
	        }
	    }
		enzymeList = temp;	
		
	}

	/**
	 * Recuperation du fichier dans lequel se trouve l'enzyme
	 * @return fichier de la reaction
	 */
	public File getOutput() {
		return output;
	}

	/**
	 * Fixation du fichier dans lequel se trouve l'enzyme
	 * @param output
	 */
	public void setOutput(File output) {
		this.output = output;
	}

	/**
	 * Recuperation de la liste d'enzymes associee au fichier
	 * d'entree de la reaction associee
	 * @return liste d'enzyme
	 */
	public List<String> getEnzymeList() {
		return enzymeList;
	}

	/**
	 * Fixation de la liste d'enzymes associee au fichier
	 * d'entree de la reaction associee
	 * @param enzymeList liste d'enzyme
	 */
	public void setEnzymeList(List<String> enzymeList) {
		this.enzymeList = enzymeList;
	}

}
