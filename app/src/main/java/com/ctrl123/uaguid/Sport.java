package com.ctrl123.uaguid;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Sport extends AppCompatActivity {
    //!\ ---------Modifier l'adresse ip ici-------- /!\
    public static String ip = "192.168.0.15";
    //!\ ------------------------------------------ /!\
    //!\ Vérifier si possible de définir l'IP dès la connexion, puis transmettre via intent... /!\

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    PopupWindow popUp;

    // URL to get JSON
    private String urlperso = "http://" + ip + "/UAGuid/JSON.php?sujet=sport";

    ArrayList<HashMap<String, String>> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        questionList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetData().execute();

        //Permet de gérer l'evenement lors du clic sur un des items de la listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupère les infos à afficher ds l'arrayList
                String textQ = questionList.get(position).get("question");
                String textR = questionList.get(position).get("reponse");

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                TextView popupTextQ, popupTextR;
                Button closeBtn;

                //On charge le layout personnalisé dans une view
                View customView = inflater.inflate(R.layout.popup_layout, null);

                //Initialisation popup avec la view personnalisées
                popUp = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT

                );

                //Ajout des infos dans les éléments de la popup
                popupTextQ = (TextView) customView.findViewById(R.id.questionText);
                popupTextQ.setText(textQ);

                popupTextR = (TextView) customView.findViewById(R.id.reponseText);
                popupTextR.setText(textR);

                //Bouton pour fermer
                closeBtn = (Button) customView.findViewById(R.id.closeButton);
                closeBtn.setBackgroundColor(Color.parseColor("#BECC07"));
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });

                //Affichage de la popup
                popUp.showAtLocation(findViewById(R.id.sport_layout), Gravity.CENTER, 0, 0);
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     * Fonction qui recupère le JSON dans la BDD via l'URL fournie
     * Le code s'execute sur un autre Thread en arrière-plan
     * Et affiche un message pendant le chargement
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        //Fonction qui affiche le message de chargement du JSON
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Sport.this);
            pDialog.setMessage("Data is loading, please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //Fonction qui recupère le JSON sur un Thread en background
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlperso);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node : l'objet principal
                    JSONArray questions = jsonObj.getJSONArray("donnees");

                    // looping through All Questions
                    for (int i = 0; i < questions.length(); i++) {
                        JSONObject c = questions.getJSONObject(i);

                        String id = c.getString("id");
                        String question = c.getString("question");
                        String reponse = c.getString("reponse");

                        // tmp hash map for single question-reponse
                        HashMap<String, String> questMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        questMap.put("id", id);
                        questMap.put("question", question);
                        questMap.put("reponse", reponse);

                        // adding contact to question list
                        questionList.add(questMap);
                    }
                } catch (final JSONException e) {
                    //enfin la gestion des erreurs, affiche un message selon le problème rencontré
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Essayez de vérifier l'IP, ou si le serveur est bien en ligne.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        //Après la récupération du JSON, masque le message de chargement, et charge les infos dans la listView
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             **/
            ListAdapter adapter = new SimpleAdapter(
                    Sport.this, questionList,
                    R.layout.list_item, new String[]{"question"}, new int[]{R.id.textView});

            lv.setAdapter(adapter);
        }

    }

}
