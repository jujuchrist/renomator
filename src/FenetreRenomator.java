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
	private JCheckBox chkDooDeleteSpaces;
	private JCheckBox chkDooRemoveNonChar;
	private JCheckBox chkDooRemoveAccolades;
	private JCheckBox chkDooRemoveParentheses;
	private JTextArea motsSuppField;

	public FenetreRenomator(){    
		this.addWindowListener(this);
		this.setTitle("");
	    this.setMinimumSize(new Dimension(1000, 400));
	    this.setLocationRelativeTo(null);            
	 
	    this.layoutMain = new GridBagLayout();
	    this.setLayout(this.layoutMain);
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    //gbc.fill = GridBagConstraints.BOTH;
	    
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
	    this.chkDooDeleteSpaces = new JCheckBox("Supprimer espaces");
	    this.chkDooRemoveNonChar = new JCheckBox("Supprimer caract�res speciaux");
	    this.chkDooRemoveAccolades = new JCheckBox("Supprimer accolades");
	    this.chkDooRemoveParentheses = new JCheckBox("Supprimer parenth�ses");
	    
	    this.chkDoAddMajuscule.setSelected(true);
	    this.chkDooDeleteSpaces.setSelected(true);
	    this.chkDooRemoveNonChar.setSelected(true);
	    this.chkDooRemoveAccolades.setSelected(true);
	    this.chkDooRemoveParentheses.setSelected(true);
	    
	    this.confPanel.setLayout(new GridLayout(5,1));
	    
	    this.confPanel.add(chkDoAddMajuscule);
	    this.confPanel.add(chkDooDeleteSpaces);
	    this.confPanel.add(chkDooRemoveNonChar);
	    this.confPanel.add(chkDooRemoveAccolades);
	    this.confPanel.add(chkDooRemoveParentheses);		
	    
	    //--liste des mots à supprimer
	    gbc.gridwidth = 1;
	    gbc.gridheight= 1;
	    gbc.gridx = 0;
	    gbc.gridy = yGrid++;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	   /* this.getContentPane().add(this.motsSuppPanel = new JPanel(),gbc);
	    this.confPanel.setLayout(new GridLayout(5,1));*/
	    this.getContentPane().add(this.motsSuppField = new JTextArea(),gbc);
	    this.motsSuppField.setPreferredSize(new Dimension(250, 30));
		this.motsSuppField.setMinimumSize(new Dimension(250, 30));
		this.motsSuppField.setMaximumSize(new Dimension(250, 30));

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

            bufferedWriter.write(this.motsSuppField.getText());
            		
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
			int option = jop.showConfirmDialog(null, "Les fichiers n'ont pas �t� trait�s ! Voulez-vous tout de m�me les ranger ?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			            
			if(option != JOptionPane.OK_OPTION){
				return; 
			}
		}
		
		System.out.println("D�but du rangement.");
		
		File[] tousLesFichiers = this.getListeFichiers();
		
		System.out.println(tousLesFichiers.length + " fichier(s) � ranger.");
		
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
		File dest = new File(this.selectedDir.getAbsolutePath() + File.separator + nomDossier);
		if(dest.exists()){
			if(dest.isDirectory()){
				return nomDossier;
			}
		}
		else{
			//créer le dossier
			if(dest.mkdir()){
				System.out.println("Dossier " + nomDossier + " cr��.");
				return nomDossier;
			}
		}
		return "";
	}

	private void traiterTousFichiers() {
		System.out.println("D�but du traitement.");
		
		this.traiteur.setParametres(this.chkDoAddMajuscule.isSelected(), 
				this.chkDooDeleteSpaces.isSelected(), 
				this.chkDooRemoveNonChar.isSelected(), 
				this.chkDooRemoveAccolades.isSelected(), 
				this.chkDooRemoveParentheses.isSelected(),
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
					    System.out.println(nom + " renomm� en " + nouvNom);
					    i++;
					}
				}
			}
		}
		
		this.traitementAJour = true;

		System.out.println("Fin du traitement. ( " + i + "/" + tousLesFichiers.length + " fichier(s) trait�(s). )");
		
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
			
			System.out.println("Nouveau dossier s�lectionn� :");
			System.out.println(this.selectedDir.getAbsolutePath());
			
			this.refreshList();
		}
	}

	private void refreshList() {
		System.out.println("Mise � jour de la liste des fichiers.\n...");
		this.textePathDossier.setText(this.selectedDir.getAbsolutePath());
		Vector<String> lstNoms = this.getListeNomsFichiers();
		this.listFichier.setListData(lstNoms);
		System.out.println("Liste des fichiers mise � jour. (" + lstNoms.size() + " fichier(s) )");
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
