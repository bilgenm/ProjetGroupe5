package be.condorcet.projetgroupe5;

import java.sql.Connection;
import java.util.ArrayList;
import be.condorcet.projetgroupe5.modele.*;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RechEnigmeActivity extends ActionBarActivity {
	private TextView numLieuRech,txtEnigme,msgDist,lblLieu;
	private Button btContinue,btAbandonner;
	private Connection con=null;
	private ArrayList<LieuDB> listeLieux = new ArrayList<LieuDB>();
	private ArrayList<EnigmeDB> listeEnigmes = new ArrayList<EnigmeDB>();
	private JeuDB jeuChoisi=null;
	private long dist = 1000;
	private int cptLieu = 0;
	private int idLieuRech = 0;
	private int idJ = 1;
	private LocationManager locationManager;
	private Point lieuRechPt=new Point(0,0),pt=new Point(0,0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rech_enigme);
		Intent i = getIntent();
		jeuChoisi = (JeuDB) i.getParcelableExtra(RechVilleActivity.IDJEU);
		numLieuRech = (TextView) findViewById(R.id.numlieu);
		txtEnigme = (TextView) findViewById(R.id.enigrech);
		msgDist = (TextView) findViewById(R.id.affdist);
		lblLieu = (TextView) findViewById(R.id.lablieu);
		btAbandonner = (Button) findViewById(R.id.abandonne);
		btContinue = (Button) findViewById(R.id.go);
		btContinue.setVisibility(View.INVISIBLE);
		if (savedInstanceState != null) {
			 listeLieux = savedInstanceState.getParcelableArrayList("myListeLieux");
			 listeEnigmes = savedInstanceState.getParcelableArrayList("myListeEnigmes");
			 cptLieu =  savedInstanceState.getInt("myCpt");
			 dist = savedInstanceState.getLong("myDist");
		        if (listeLieux != null && listeEnigmes != null) {
		        	if(dist<25) {
		        		txtEnigme.setText("Vous avez trouvé!!!");
						msgDist.setText("Description: "+listeEnigmes.get(cptLieu).getDescLieu());
						if(cptLieu==listeLieux.size()-1) {
							btAbandonner.setVisibility(View.INVISIBLE);
						}
						btContinue.setVisibility(View.VISIBLE);	
		        	} 
		        	else {
		        		btContinue.setVisibility(View.INVISIBLE);
		        		numLieuRech.setText(""+(cptLieu+1));
						txtEnigme.setText(listeEnigmes.get(cptLieu).getTexte());
						lieuRechPt=new Point(listeLieux.get(cptLieu).getLatLieu(),listeLieux.get(cptLieu).getLongLieu());
						msgDist.setText("");
		        	}
		        		
		        }
		}
		else {
			btContinue.setVisibility(View.INVISIBLE);
			MyAccesDBListeLieux adb=new MyAccesDBListeLieux(RechEnigmeActivity.this);
	        adb.execute();
	    }
		/* récupération des services de localisation disponibles (GPS,signal
		 * GSM,...)
		 * permet d'enregistrer les systèmes qui sont à votre disposition
		 */
		ArrayList<LocationProvider> providers = new ArrayList<LocationProvider>();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ArrayList<String> names = (ArrayList<String>) locationManager.getProviders(true);
		for (String name : names)
			providers.add(locationManager.getProvider(name));
		/* gestionnaire de localisation activer la méthode requestLocUp le
		 * provider qu'il doit utiliser pour calculer la long et lat va
		 * interroger le système tous les 1000 milliseconde ici on a mis 1
		 * seconde mais normalement 30 secondes c'est bon car ça consome de la
		 * batterie le 0 pour dire évaluer ma position tous les 0 metre
		 */
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0, new LocationListener() {
			/*Cette méthode s'active si vous avez changé de la place la
			 * méthode reçoit objet de la classe location
			 */
			@Override
			public void onLocationChanged(android.location.Location location) {
				// TODO Auto-generated method stub
				if (location != null) {
					double longitude = location.getLongitude();
					double latitude = location.getLatitude();
					pt = new Point(latitude, longitude);
					dist = pt.dist(lieuRechPt);
					if (dist < 25) {
						txtEnigme.setText("Vous avez trouvé!!!");
						msgDist.setText("Description: "+ listeEnigmes.get(cptLieu).getDescLieu());
						if (cptLieu == listeLieux.size()-1) {
							btAbandonner.setVisibility(View.INVISIBLE);
						}
						btContinue.setVisibility(View.VISIBLE);
					} 
					else if (dist < 30) {
						msgDist.setText("Vous êtes tout près regarder bien au tour de vous! Vous êtes à "+ dist + " m");

					} 
					else if (dist < 1000) {
						msgDist.setText("Vous êtes à " + dist + " m");
					} 
					else {
						msgDist.setText("Vous êtes à " + (dist / 1000)+ " km");

					}
				}
			}
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
            }
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
			}
		});
	}
	public void gestionAbandon(View view){
		Toast.makeText(RechEnigmeActivity.this,"Ne fonctionne pas!", Toast.LENGTH_SHORT).show();	
	}
	public void gestionContinue(View view){
		cptLieu++;
		if(cptLieu>listeLieux.size()-1){
			btContinue.setVisibility(View.INVISIBLE);
			//btAbandonner.setVisibility(View.INVISIBLE);
			lblLieu.setText("");
			numLieuRech.setText("");
			txtEnigme.setText("Fin de jeu de piste pour cette ville");
			msgDist.setText("");
			idLieuRech = 0;
			MyAccesDBLieuRech adb2 = new MyAccesDBLieuRech(RechEnigmeActivity.this); 
			adb2.execute();	
		}
		else{
			btContinue.setVisibility(View.INVISIBLE);
			idLieuRech = listeLieux.get(cptLieu).getIdLieu();
			MyAccesDBLieuRech adb2 = new MyAccesDBLieuRech(RechEnigmeActivity.this); 
			adb2.execute();
			numLieuRech.setText(""+(cptLieu+1));
			txtEnigme.setText(listeEnigmes.get(cptLieu).getTexte());
			lieuRechPt=new Point(listeLieux.get(cptLieu).getLatLieu(),listeLieux.get(cptLieu).getLongLieu());
			msgDist.setText("Calcul de distance en cours");	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rech_enigme, menu);
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
	
	public void onSaveInstanceState(Bundle savedState) {
	    super.onSaveInstanceState(savedState);
	    ArrayList<LieuDB> listeL = new ArrayList<LieuDB>();
	    ArrayList<EnigmeDB> listeEnig = new ArrayList<EnigmeDB>();
	    int cpt= cptLieu;
	    long distS = dist;
	    // Note: getValues() is a method in your ArrayAdaptor subclass
	    listeL = listeLieux;
	    listeEnig = listeEnigmes;
	    //values = listeJeux.getValues(); 
	    savedState.putParcelableArrayList("myListeLieux", listeL);
	    savedState.putParcelableArrayList("myListeEnigmes", listeEnig);
	    savedState.putLong("myDist", distS);
	    savedState.putInt("myCpt",cpt);    
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		 try {
	          con.close();
	          con=null;
	          /*le message qu'on voit dans LogCat*/
	          Log.d("connexion","deconnexion OK");
	          }
	          catch (Exception e) { 
	          }
		 Log.d("connexion","deconnexion OK");
	}
	/* classe interne qui permet de récuperer une liste avec tous les lieu 
	 * et une liste avec tous les énigmes pour le jeu choisi par le joueur
	 */
	class MyAccesDBListeLieux extends AsyncTask<String,Integer,Boolean> {
		private String resultat;
		private ProgressDialog pgd=null;
		public MyAccesDBListeLieux(RechEnigmeActivity pActivity) {
			link(pActivity);
			// TODO Auto-generated constructor stub
		}
		private void link(RechEnigmeActivity pActivity) {
			// TODO Auto-generated method stub			
		}
		protected void onPreExecute(){
			super.onPreExecute();
			pgd=new ProgressDialog(RechEnigmeActivity.this);
		    pgd.setMessage("Chargement d'enigmes en cours");
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
				LieuDB.setConnection(con);
				EnigmeDB.setConnection(con);
			}
			try{
				listeLieux = LieuDB.rechLieu(jeuChoisi.getDebut());
				for(LieuDB l1 : listeLieux)
				{
					EnigmeDB enig = EnigmeDB.rechEnigmeLieu(l1.getIdLieu(),"fr");
					listeEnigmes.add(enig);
				}
			}
			catch(Exception e){	
				resultat="erreur" +e.getMessage(); 
				return false;
			}
			return true;
		}
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (result) {
				idLieuRech = listeLieux.get(cptLieu).getIdLieu();
				MyAccesDBLieuRech adb2 = new MyAccesDBLieuRech(RechEnigmeActivity.this); 
				adb2.execute();
				numLieuRech.setText(""+(cptLieu+1));
				txtEnigme.setText(listeEnigmes.get(cptLieu).getTexte());
				lieuRechPt=new Point(listeLieux.get(cptLieu).getLatLieu(),listeLieux.get(cptLieu).getLongLieu());
				msgDist.setText("Calcul de distance en cours");
			}
			else {
				txtEnigme.setText(resultat);
			}
		}
	}
	/*classe interne laquelle va permettre de mettre à jour 
	 * le lieu recherché par le joueur*/
	class MyAccesDBLieuRech extends AsyncTask<String, Integer, Boolean> {
		private String resultat;
		private ProgressDialog pgd = null;
		public MyAccesDBLieuRech(RechEnigmeActivity pActivity) {
			link(pActivity);
			// TODO Auto-generated constructor stub
		}
		private void link(RechEnigmeActivity pActivity) {
			// TODO Auto-generated method stub
		}
		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(RechEnigmeActivity.this);
			pgd.setMessage("Mise à jour de votre lieu recherché");
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();

		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {
				con = new DBConnection().getConnection();
				if (con == null) {
					resultat = "echec de la connexion";
					return false;
				}
				JoueurDB.setConnection(con);
			} 
			else {
				JoueurDB.setConnection(con);
			}
			try {
				JoueurDB j = new JoueurDB(idJ);
				j.read();
				j.setLieuRech(idLieuRech);
				j.updateLieuRech();
			}
			catch (Exception e) {
				resultat = "Erreur de mis à jour de lieu recherché "+ e.getMessage();
				return false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (result) {
				Toast.makeText(RechEnigmeActivity.this,"Votre lieu a été mis à jour", Toast.LENGTH_SHORT).show();

			} 
			else {
				txtEnigme.setText(resultat);
			}
		}
	}
	
	/*Classe interne qui va nous permettre de créer des points et calculer 
	 * la distance entre le lieu recherché et la position de joueur */
	class Point {
		int deglat, minlat, seclat, centiseclat;
		int deglong, minlong, seclong, centiseclong;
		double latitude, longitude;

		/* Pour pouvoir transformer */
		public Point(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;

			double valeur = latitude;
			int valConv = (int) (valeur * 3600);
			centiseclat = (int) ((valeur * 3600 - valConv) * 100);
			deglat = valConv / 3600;
			valConv = valConv % 3600;
			minlat = valConv / 60;
			seclat = valConv % 60;

			valeur = longitude;
			valConv = (int) (valeur * 3600);
			centiseclong = (int) ((valeur * 3600 - valConv) * 100);
			deglong = valConv / 3600;
			valConv = valConv % 3600;
			minlong = valConv / 60;
			seclong = valConv % 60;

		}
		public Point(int deglat, int minlat, int seclat, int centiseclat,
				int deglong, int minlong, int seclong, int centiseclong) {
			super();
			this.deglat = deglat;
			this.minlat = minlat;
			this.seclat = seclat;
			this.centiseclat = centiseclat;
			this.deglong = deglong;
			this.minlong = minlong;
			this.seclong = seclong;
			this.centiseclong = centiseclong;
			latitude = (deglat * 3600 + minlat * 60 + seclat + centiseclat / 100.0) / 3600;
			longitude = (deglong * 3600 + minlong * 60 + seclong + centiseclong / 100.0) / 3600;
		}
		/* Pour calculer la distance entre deux point la méthode retourne 
		 * nombre de metres entre ses deux points
		 */
		public long dist(Point pt) {// formules approximatives
			double dlat = Math.abs(latitude - pt.latitude) / 90 * 10000000;
			double latInvRadians = Math.abs(90 - latitude) / 180 * Math.PI;
			double circLat = 10000000 * Math.sin(latInvRadians);
			double dlong = Math.abs(longitude - pt.longitude) / 90 * circLat;
			double distance = Math.sqrt(dlat * dlat + dlong * dlong);
			return (long) distance;
		}
	}
}
