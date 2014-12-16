package be.condorcet.projetgroupe5;

import java.sql.Connection;


import be.condorcet.projetgroupe5.modele.*;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class InscriptionActivity extends ActionBarActivity {
	private EditText nomJoueur,prenomJoueur,pseudoJoueur,mdpJoueur,mdpconfJoueur;
	private Button btInscrip,btEffacer;
	private String nomJ,prenomJ,pseudoJ,mdpJ,mdpConfJ = "";
	private Connection con=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		//ed1=(EditText) findViewById(R.id.texte1);
		nomJoueur = (EditText) findViewById(R.id.editNom);
		prenomJoueur = (EditText) findViewById(R.id.editPrenom);
		pseudoJoueur = (EditText) findViewById(R.id.editPseudo);
		mdpJoueur = (EditText) findViewById(R.id.editPassword);
		mdpconfJoueur = (EditText) findViewById(R.id.editPassConfirm);
		btInscrip = (Button) findViewById(R.id.buttonInscrip);
		btEffacer = (Button) findViewById(R.id.buttonClear);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		 try {
	          con.close();
	          con=null;
	          Log.d("connexion","deconnexion OK");
	          }
	          catch (Exception e) { 
	          }
		 Log.d("connexion","deconnexion OK");
	}
	
	public void gestionSinscrire(View view){
			String verif= "";
			boolean flag = false;
			nomJ = nomJoueur.getText().toString().trim();
			prenomJ = prenomJoueur.getText().toString().trim();
			pseudoJ = pseudoJoueur.getText().toString().trim();
			mdpJ = mdpJoueur.getText().toString().trim(); 
			mdpConfJ = mdpconfJoueur.getText().toString().trim();
			if(nomJ.equals("") || prenomJ.equals("") || pseudoJ.equals("") || mdpJ.equals("") || mdpConfJ.equals("")){
				verif = getString(R.string.errchampvide);
			}
			
			else if(!mdpJ.equals(mdpConfJ)){
				flag = true;
				verif = getString (R.string.errmdp);
			}
			
			if(!verif.equals("")){
				if(flag){
					mdpJoueur.getText().clear();
					mdpconfJoueur.getText().clear();
				}
				Toast.makeText(InscriptionActivity.this,verif, Toast.LENGTH_LONG).show();
			}
			else{
				MyAccesDBInscripJoueur insjr = new MyAccesDBInscripJoueur(InscriptionActivity.this); 
				insjr.execute();
			}
			
		}
	
	public void gestionClear(View view){
		raz();
	}
	
	public void raz(){
		nomJoueur.getText().clear();
		prenomJoueur.getText().clear();
		pseudoJoueur.getText().clear();
		mdpJoueur.getText().clear();
		mdpconfJoueur.getText().clear();
	}
	
	/* classe interne qui permet d'inscrire un joueur, avant d'effectuer l'inscription
	 * vérifie si le pseudo se trouve déjà dans la base de données
	 */
	class MyAccesDBInscripJoueur extends AsyncTask<String,Integer,Boolean> {
		private String resultat;
		private ProgressDialog pgd=null;
		boolean ok= false;
		
							
				public MyAccesDBInscripJoueur(InscriptionActivity pActivity) {
				
					link(pActivity);
					// TODO Auto-generated constructor stub
				}

				private void link(InscriptionActivity pActivity) {
					// TODO Auto-generated method stub
								
				}
				
				protected void onPreExecute(){
					super.onPreExecute();
					pgd=new ProgressDialog(InscriptionActivity.this);
				    pgd.setMessage("Traitement de données en cours");
				    pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				    pgd.show();
				}
				
				@Override
				protected Boolean doInBackground(String... arg0) {  
					if(con==null){
						con = new DBConnection().getConnection(); 
						if(con==null) {
							resultat="echec de la connexion";
							return false;
						}
						JoueurDB.setConnection(con);
					}
					try{
						JoueurDB j=JoueurDB.rechPseudo(pseudoJ);
						if(j!=null){ 
							resultat = getString(R.string.errpseudoexist);
							ok = true;
							return false;
						}
					    JoueurDB j1= new JoueurDB(nomJ,prenomJ,pseudoJ,mdpJ);
						j1.create();
						resultat = getString(R.string.inscrip_ok);
						}
					catch(Exception e){	
						resultat="erreur"+e.getMessage(); 
						return false;
					}
					return true;
				}
			
				
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					pgd.dismiss();
					
					if (result) {
						Toast.makeText(InscriptionActivity.this,resultat, Toast.LENGTH_LONG).show();
						raz();
						Intent i = new Intent(InscriptionActivity.this,ConnexionActivity.class);
						startActivity(i);
					}
					else{
						Toast.makeText(InscriptionActivity.this,resultat, Toast.LENGTH_LONG).show();
						if(ok){
							pseudoJoueur.getText().clear();
						}
					}

			}
	}
}
