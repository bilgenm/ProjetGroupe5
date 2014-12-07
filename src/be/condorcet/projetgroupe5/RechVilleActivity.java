package be.condorcet.projetgroupe5;

import java.sql.Connection;
import java.util.ArrayList;

import be.condorcet.projetgroupe5.modele.*;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RechVilleActivity extends ActionBarActivity {
	private Spinner list;
	private TextView tv;
	private ArrayAdapter<String> adapter;
	/*liste qui va être rempli avec tous les jeux existants dans la base
	 * de données*/
	private ArrayList<JeuDB> listeJeux = new ArrayList<JeuDB>();
	public static final String IDJEU="jeu";
	private Connection con=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rech_ville);
		// spin c'est l'id de la liste déroulante
		list = (Spinner) findViewById(R.id.spin);
		tv = (TextView) findViewById(R.id.aff);
		MyAccesDBListeJeux adb = new MyAccesDBListeJeux(RechVilleActivity.this);
		adb.execute();
	}
	public void gestionSearch(View view){
		int pos = list.getSelectedItemPosition();
		JeuDB j = listeJeux.get(pos);
		//tv.setText(j.toString());
		Intent i = new Intent(RechVilleActivity.this,RechEnigmeActivity.class);
		i.putExtra(IDJEU, j);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rech_ville, menu);
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
	public void onDestroy() {
		super.onDestroy();
		try {
			con.close();
			con = null;
			/* le message qu'on voit dans LogCat */
			Log.d("connexion", "deconnexion OK");
		} 
		catch (Exception e) {
		}
		Log.d("connexion", "deconnexion OK");
	}
	/*Classe interne qui permet d'injecter la connexion dans la classe JeuDB,
	 * d'accéder à la BD et de récuperer une liste avec tous les jeux dans la base 
	 * de données*/
	class MyAccesDBListeJeux extends AsyncTask<String, Integer, Boolean> {
		private String resultat;
		private ProgressDialog pgd = null;
		
		public MyAccesDBListeJeux(RechVilleActivity pActivity) {
			link(pActivity);
			// TODO Auto-generated constructor stub
		}

		private void link(RechVilleActivity pActivity) {
			// TODO Auto-generated method stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(RechVilleActivity.this);
			pgd.setMessage("chargement en cours");
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			/* une variable d'instance de connexion qui est globale elle vérifie
			 * si la connexion existe déjà le deuxieme fois il fait pas ça car
			 * la connexion est déjà établie 
			 * si c'est nulle il va essayer d'établir la connexion */
			if (con == null) {
				con = new DBConnection().getConnection();
				if (con == null) {
					resultat = "echec de la connexion";
					return false;
				}
				JeuDB.setConnection(con);
			}
			try {
				listeJeux = JeuDB.listeVilles();
			}
			catch (Exception e) {
				resultat = "erreur" + e.getMessage();
				return false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			/* pour faire disparaitre la progresse barre */
			pgd.dismiss();
			if (result) {
				int tailleListe = listeJeux.size();
				String[] tabVilles = new String[tailleListe];
				for (int i = 0; i < tailleListe; i++) {
					tabVilles[i] = listeJeux.get(i).getNomVille();
				}
				adapter = new ArrayAdapter<String>(RechVilleActivity.this,
						android.R.layout.simple_spinner_item, tabVilles);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				list.setAdapter(adapter);
			} else {
				tv.setText(resultat);
			}

		}
	}
}
