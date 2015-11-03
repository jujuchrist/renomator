import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class FenetreRenomator extends JFrame implements MouseListener, WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FILENAMECONF = "datas";
	private GridBagLayout layoutMain;
	
	private JButton btnChoixDossier;
	private JLabel textePathDossier;
	private File selectedDir = null;
	private JList<String> listFichier;
	private JButtonWithMLstnr btnLancerTraitement;
	private JButtonWithMLstnr btnRanger;
	private JPanel confPanel;
	private TraiteurDeNoms traiteur;
	
	private boolean traitementAJour = false;
	private JCheckBox chkDoAddMajuscule;
	private JCheckBox chkDoDeleteSpaces;
	private JCheckBox chkDoRemoveNonChar;
	private JCheckBox chkDoRemoveAccolades;
	private JCheckBox chkDoRemoveParentheses;
	private JCheckBox chkDoRemoveDate;
	private JCheckBox chkDoRemoveAfterAny;
	private JTextArea motsSuppField;

	public FenetreRenomator(){    
		ImageIcon img = new ImageIcon(getClass().getResource("/ressources/icon.png"));
		this.setIconImage(img.getImage());
		this.addWindowListener(this);
		this.setTitle("Rennomator");
	    this.setMinimumSize(new Dimension(1000, 600));
	    this.setLocationRelativeTo(null);            
	 
	    this.layoutMain = new GridBagLayout();
	    this.setLayout(this.layoutMain);
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    //gbc.fill = GridBagConstraints.BOTH;
	    
		Border bdr = BorderFactory.createLineBorder(Color.GRAY, 1);
	    
	    //On ajoute les boutons au panel droit
	    int yGrid = 1;

	    //On ajoute les boutons au panel gauche
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    this.getContentPane().add(this.btnChoixDossier = new JButtonWithMLstnr("Choix du dossier", this),gbc);
	    
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    this.getContentPane().add(this.textePathDossier = new JLabel("filePath"),gbc);
	    this.textePathDossier.setPreferredSize(new Dimension(250, 30));
		this.textePathDossier.setMinimumSize(new Dimension(250, 30));
		this.textePathDossier.setMaximumSize(new Dimension(250, 30));
	    
	    
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    this.getContentPane().add(this.confPanel = new JPanel(),gbc);
	    
	    this.chkDoAddMajuscule = new JCheckBox("Mettre majuscule");
	    this.chkDoDeleteSpaces = new JCheckBox("Supprimer espaces");
	    this.chkDoRemoveNonChar = new JCheckBox("Supprimer caractères speciaux");
	    this.chkDoRemoveAccolades = new JCheckBox("Supprimer accolades");
	    this.chkDoRemoveParentheses = new JCheckBox("Supprimer parenthèses");
	    this.chkDoRemoveDate = new JCheckBox("Supprimer dates");
	    this.chkDoRemoveAfterAny = new JCheckBox("Supprimer tout après l'un des précédants");
	    
	    this.chkDoAddMajuscule.setSelected(true);
	    this.chkDoDeleteSpaces.setSelected(true);
	    this.chkDoRemoveNonChar.setSelected(true);
	    this.chkDoRemoveAccolades.setSelected(true);
	    this.chkDoRemoveParentheses.setSelected(true);
	    this.chkDoRemoveDate.setSelected(true);
	    this.chkDoRemoveAfterAny.setSelected(false);
	    
	    this.confPanel.setLayout(new GridLayout(7,1));
	    
	    this.confPanel.add(chkDoAddMajuscule);
	    this.confPanel.add(chkDoDeleteSpaces);
	    this.confPanel.add(chkDoRemoveNonChar);
	    this.confPanel.add(chkDoRemoveAccolades);
	    this.confPanel.add(chkDoRemoveParentheses);
	    this.confPanel.add(chkDoRemoveDate);
	    this.confPanel.add(chkDoRemoveAfterAny);
	    
	    //--liste des mots Ã  supprimer
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 4.0;
	   /* this.getContentPane().add(this.motsSuppPanel = new JPanel(),gbc);
	    this.confPanel.setLayout(new GridLayout(5,1));*/
	    this.getContentPane().add(this.motsSuppField = new JTextArea(),gbc);
	    this.motsSuppField.setPreferredSize(new Dimension(250, 120));
		this.motsSuppField.setMinimumSize(new Dimension(250, 120));
		this.motsSuppField.setMaximumSize(new Dimension(250, 120));
		this.motsSuppField.setBorder(bdr);

	    //-- 
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    this.getContentPane().add(this.btnLancerTraitement = new JButtonWithMLstnr("Lancer le traitement", this),gbc);
	    
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    this.getContentPane().add(this.btnRanger = new JButtonWithMLstnr("Ranger", this),gbc);
	    
	    gbc.gridwidth = 1;
	    gbc.gridheight= GridBagConstraints.REMAINDER;
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.weightx = 50.0;
	    gbc.weighty = 1.0;
	    gbc.fill = GridBagConstraints.BOTH;
	    JScrollPane scrollPane = new JScrollPane();
	    this.getContentPane().add(scrollPane,gbc);
	    this.listFichier = new JList<String>();
	    scrollPane.setViewportView(this.listFichier);
	    //scrollPane.setMinimumSize(new Dimension(250, 250));
	    //scrollPane.setMaximumSize(new Dimension(250, 250));
	    //scrollPane.setPreferredSize(new Dimension(250, 250));
	    
	    
	    this.traiteur = new TraiteurDeNoms();
	    
	    this.setVisible(true);
	    readFilePersistant();
	}

	private void writeFilePersistant() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAMECONF);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if(this.selectedDir!=null){
            	bufferedWriter.write("**" + this.selectedDir.getAbsolutePath() + "**");
            }

            bufferedWriter.write(this.motsSuppField.getText().trim());
            		
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
	}

	private void readFilePersistant() {
		String listMots = "";
		String ligne = "";
		try {
            FileReader fileReader = new FileReader(FILENAMECONF);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((ligne = bufferedReader.readLine()) != null) {
            	listMots += ligne + "\n";
            }   
            listMots.trim();
            bufferedReader.close();        

        }
        catch(FileNotFoundException ex) {
        	//ex.printStackTrace();               
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
		if(listMots.matches("(?s)\\*\\*.+\\*\\*.*")){
			int st, end;
			st = listMots.indexOf("**")+2;
			end = listMots.indexOf("**", st);
			String filePath = listMots.substring(st, end);
			this.setDirectory(new File(filePath));
			listMots = listMots.substring(end+2);
		}
		this.motsSuppField.setText(listMots);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component cOrigin = e.getComponent();
		if(cOrigin == this.btnChoixDossier){
			this.choisirNouveauDossier();
		}
		else if(cOrigin == this.btnLancerTraitement){
			this.traiterTousFichiers();
		}
		else if(cOrigin == this.btnRanger){
			this.rangerFichiers();
		}
	}

	private void rangerFichiers() {
		
		if(!this.traitementAJour){
			JOptionPane jop = new JOptionPane();            
			@SuppressWarnings("static-access")
			int option = jop.showConfirmDialog(null, "Les fichiers n'ont pas été traités ! Voulez-vous tout de même les ranger ?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			            
			if(option != JOptionPane.OK_OPTION){
				return; 
			}
		}
		
		System.out.println("Début du rangement.");
		
		File[] tousLesFichiers = this.getListeFichiers();
		
		System.out.println(tousLesFichiers.length + " fichier(s) à  ranger.");
		
		int i = 0;
		for(File f:tousLesFichiers){
			if(f.isFile()){
				String nom = this.getFileName(f);
				String dossierRng = this.getDossierRangement(nom);
				if(!dossierRng.equals("")){
					File dest = new File(this.selectedDir.getAbsolutePath() + File.separator + dossierRng + File.separator + nom);
					if(!dest.exists()){
					    f.renameTo(dest);
					    System.out.println(nom + " rangé dans " + dossierRng);
					    i++;
					}
				}
			}
		}

		System.out.println("Fin du traitement. ( " + i + "/" + tousLesFichiers.length + " fichier(s) rangé(s). )");
		
		this.refreshList();
	}

	private String getDossierRangement(String nom) {
		String nomDossier = nom.substring(0, 1).toUpperCase();
		Pattern p = Pattern.compile("(?i)((le(s)?)*(la)*(un(e)?)*(des)*\\s).*");
		Matcher m = p.matcher(nom);
		if(m.matches() && m.groupCount()>=1 && m.group(1) != null){
			System.out.println("match");
			nomDossier = nom.substring(m.group(1).length(),m.group(1).length()+1).toUpperCase();
		}
		File dest = new File(this.selectedDir.getAbsolutePath() + File.separator + nomDossier);
		if(dest.exists()){
			if(dest.isDirectory()){
				return nomDossier;
			}
		}
		else{
			//créer le dossier
			if(dest.mkdir()){
				System.out.println("Dossier " + nomDossier + " créé.");
				return nomDossier;
			}
		}
		return "";
	}

	private void traiterTousFichiers() {
		System.out.println("Début du traitement.");
		
		this.traiteur.setParametres(this.chkDoAddMajuscule.isSelected(), 
				this.chkDoDeleteSpaces.isSelected(), 
				this.chkDoRemoveNonChar.isSelected(), 
				this.chkDoRemoveAccolades.isSelected(), 
				this.chkDoRemoveParentheses.isSelected(),
				this.chkDoRemoveDate.isSelected(),
				this.chkDoRemoveAfterAny.isSelected(),
				this.motsSuppField.getText());
		
		File[] tousLesFichiers = this.getListeFichiers();
		
		int i = 0;
		for(File f:tousLesFichiers){
			if(f.isFile()){
				String nom = this.getFileName(f);
				String nouvNom = this.traiteur.traiter(nom);
	
				if(!nouvNom.equals("") && !nom.equals(nouvNom)){
					File dest = new File(this.selectedDir.getAbsolutePath() + File.separator + nouvNom);
					if(!dest.exists()){
					    f.renameTo(dest);
					    System.out.println(nom + " renommé en " + nouvNom);
					    i++;
					}
				}
			}
		}
		
		this.traitementAJour = true;

		System.out.println("Fin du traitement. ( " + i + "/" + tousLesFichiers.length + " fichier(s) traité(s). )");
		
		this.refreshList();
	}


	private String getFileName(File f) {
		int index = f.getAbsolutePath().lastIndexOf(File.separator)+1;
		return f.getAbsolutePath().substring(index);
	}

	private void choisirNouveauDossier() {
		JFileChooser fileChooser = new JFileChooser(this.selectedDir);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showDialog(this, "Choisir");
		this.setDirectory(fileChooser.getSelectedFile());
	}

	private void setDirectory(File selectedFile) {
		if(selectedFile != null){
			this.selectedDir  = selectedFile;
			if ( !this.selectedDir.isDirectory() ) {
				this.selectedDir = this.selectedDir.getParentFile();
			}
			
			this.traitementAJour = false;
			
			System.out.println("Nouveau dossier sélectionné :");
			System.out.println(this.selectedDir.getAbsolutePath());
			
			this.refreshList();
		}
	}

	private void refreshList() {
		System.out.println("Mise à  jour de la liste des fichiers.\n...");
		this.textePathDossier.setText(this.selectedDir.getAbsolutePath());
		Vector<String> lstNoms = this.getListeNomsFichiers();
		this.listFichier.setListData(lstNoms);
		System.out.println("Liste des fichiers mise à  jour. (" + lstNoms.size() + " fichier(s) )");
	}

	private Vector<String> getListeNomsFichiers() {
		Vector<String> listeNomsFch = new Vector<String>();
		File[] lstFile = getListeFichiers();
		for(File f : lstFile){
			listeNomsFch.add(this.getFileName(f));
		}

		return listeNomsFch;
	}

	private File[] getListeFichiers() {
		return this.selectedDir.listFiles(new FileFilter(){

			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
			
		});
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	    writeFilePersistant();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}
}
