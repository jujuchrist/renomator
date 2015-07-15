import java.util.Vector;
//test git

public class TraiteurDeNoms {
	private Vector<String> remplacements = new Vector<String>();
	
	private boolean doAddMajuscule = true;
	private boolean doDeleteSpaces = true;
	private boolean doRemoveNonChar = true;
	private boolean doRemoveAccolades = true;
	private boolean doRemoveParentheses = true;
	
	public String traiter(String nom) {
		String oldName = nom;
		
		//Retirer l'extension
		String extension = nom.substring(nom.lastIndexOf("."));
		nom = nom.substring(0, nom.lastIndexOf(".")).toLowerCase();
		
		for(int i = 0; i<this.remplacements.size()-1; i+=2){
			nom = nom.replaceAll(this.remplacements.get(i).toLowerCase(), this.remplacements.get(i+1).toLowerCase());
		}
		
		if(this.doRemoveParentheses)
			nom = nomSansParentheses(nom);
		
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
	
	public void setParametres(boolean pDoAddMajuscule, boolean pDoDeleteSpaces, boolean pDoRemoveNonChar, boolean pDoRemoveAccolades, boolean pDoRemoveParentheses){
		doAddMajuscule = pDoAddMajuscule;
		doDeleteSpaces = pDoDeleteSpaces;
		doRemoveNonChar = pDoRemoveNonChar;
		doRemoveAccolades = pDoRemoveAccolades;
		doRemoveParentheses = pDoRemoveParentheses;
	}

	private String nomSansAccolades(String nom) {
		return nom.replaceAll("\\[[^\\]\\[]*\\]"," ");
	}

	private String nomSansParentheses(String nom) {
		return nom.replaceAll("\\([^\\(\\)]*\\)"," ");
	}

	private String nomSansNnChar(String nom) {
		return nom.replaceAll("\\W"," ");
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
