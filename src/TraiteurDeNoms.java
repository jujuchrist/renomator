import java.util.Vector;

public class TraiteurDeNoms {
	private Vector<String> remplacements = new Vector<String>();
	
	private boolean doAddMajuscule = true;
	private boolean doDeleteSpaces = true;
	private boolean doRemoveNonChar = true;
	private boolean doRemoveAccolades = true;
	private boolean doRemoveParentheses = true;
	private boolean doRemoveDates = true;
	private boolean doRemoveAfterAny = false;
	
	public String traiter(String nom) {
		String oldName = nom;
		
		//Retirer l'extension
		String extension = nom.substring(nom.lastIndexOf("."));
		nom = nom.substring(0, nom.lastIndexOf(".")).toLowerCase();
		
		for(int i = 0; i<this.remplacements.size()-1; i+=2){
			if(this.doRemoveAfterAny)
				nom = nom.replaceAll("(?i)" + this.remplacements.get(i).toLowerCase() + ".*", this.remplacements.get(i+1).toLowerCase());
			nom = nom.replaceAll("(?i)" + this.remplacements.get(i).toLowerCase(), this.remplacements.get(i+1).toLowerCase());
		}
		
		if(this.doRemoveParentheses)
			nom = nomSansParentheses(nom);
		
		if(this.doRemoveDates)
			nom = nomSansDates(nom);
		
		if(this.doRemoveAccolades)
			nom = nomSansAccolades(nom);
	    
	    if(this.doRemoveNonChar)
	    	nom = nomSansNnChar(nom);
	    
	    if(this.doDeleteSpaces){
	    	nom = nomSansEspaceSupp(nom);
	    	nom = nomSansEspacesExtrm(nom);
	    }
	    
	    if(this.doAddMajuscule )
	    	nom = nomAvecMajuscule(nom);
	    
	    //Remettre l'extension
	    nom = nom + extension;
	    
	    if(!oldName.equals(nom)){
	    	return traiter(nom);
	    }
	    
		return nom;
	} 
	
	private String nomSansDates(String nom) {
		if(this.doRemoveAfterAny)
			return nom.replaceAll("[\\d]{4}.*"," ");

		return nom.replaceAll("[\\d]{4}"," ");
	}

	public void setParametres(boolean pDoAddMajuscule, 
			boolean pDoDeleteSpaces, 
			boolean pDoRemoveNonChar, 
			boolean pDoRemoveAccolades, 
			boolean pDoRemoveParentheses, 
			boolean pDoRemoveDates, 
			boolean pDoRemoveAfterAny, 
			String pListMotsSuppr){
		doAddMajuscule = pDoAddMajuscule;
		doDeleteSpaces = pDoDeleteSpaces;
		doRemoveNonChar = pDoRemoveNonChar;
		doRemoveAccolades = pDoRemoveAccolades;
		doRemoveParentheses = pDoRemoveParentheses;
		doRemoveDates = pDoRemoveDates;
		doRemoveAfterAny = pDoRemoveAfterAny;
		this.remplacements.clear();
		
		String[] lstMots = pListMotsSuppr.split(";");
		for(String mot : lstMots){
			if(mot.matches(".*")){
				this.addRemplacement(mot.trim(), " ");
				System.out.println("supprimer \"" + mot.trim() + "\"");
			}
		}
	}

	private String nomSansAccolades(String nom) {
		if(this.doRemoveAfterAny)
			return nom.replaceAll("\\[[^\\]\\[]*\\].*"," ");
		return nom.replaceAll("\\[[^\\]\\[]*\\]"," ");
	}

	private String nomSansParentheses(String nom) {
		if(this.doRemoveAfterAny)
			return nom.replaceAll("\\([^\\(\\)]*\\).*"," ");
		return nom.replaceAll("\\([^\\(\\)]*\\)"," ");
	}

	private String nomSansNnChar(String nom) {
		return nom.replaceAll("[\\W&&[^\\u00C0-\\u017F]]"," ");
	}

	private String nomSansEspacesExtrm(String nom) {
		while(nom.startsWith(" "))
	    	nom = nom.substring(1);
	    
	    while(nom.endsWith(" "))
	    	nom = nom.substring(0, nom.length()-1);
		return nom;
	}

	private String nomSansEspaceSupp(String nom) {
		return nom.replaceAll("\\s\\s+"," ");
	}

	private String nomAvecMajuscule(String nom) {
		if(nom.length()>1)
	    	return nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase(); //Ajouter Majuscule
		return nom;
	}

	public void addSuppression(String pASupprimer){
		this.addRemplacement(pASupprimer, " ");
	}
	
	public void addRemplacement(String from, String to) {
		this.remplacements.add(from);
		this.remplacements.add(to);
	}

	/*public void removeRemplacement(String from) {
		this.remplacements.remove(from);
	}*/

}
