package com.codegraphy;

import static com.codegraphy.StrokeDataHandler.getStrokeData;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.net.NetworkInfo;

import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
//import com.codegraphy.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Tasks;


public class MainActivity extends AppCompatActivity {

    @VisibleForTesting
    final StrokeHandler strokeHandler = new StrokeHandler();

    private final String TAG = "parameters";
    private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextView editTextView = findViewById(R.id.edit_text_view);
        strokeHandler.setEditTextView(editTextView);

        //set model parameters and recognizer
        strokeHandler.modelManager.setModelParametersAndRecognizer();


        //download model
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTimeRunFlag", false)) {

            //download the model , once, if has internet
            if (!isNetwork(MainActivity.this)) {
                alert("Warning!", "An Internet connection is needed for a one time setup, please check you connection and restart the application");
            }
            strokeHandler.modelManager.downloadModel();
            Log.d(TAG, "------------------ ran one time-------------------");
            SharedPreferences.Editor editor = prefs.edit();
            if (isNetwork(MainActivity.this)) {
                editor.putBoolean("firstTimeRunFlag", true);
                Log.d(TAG, "------------------ has internet , flag set-------------------");
            }
            editor.commit();
        }

        //check if downloaded
        strokeHandler.modelManager.getRemoteModelManager()
                .isModelDownloaded(strokeHandler.modelManager.getModel())
                .onSuccessTask(result -> {
                    if (!result) {
                        Log.i(TAG, " en-us Model not downloaded yet");
                        return Tasks.forResult(null);
                    }
                    Log.i(TAG, "en-us Model downloaded successfully!!");
                    return Tasks.forResult(null);
                });

    }

    //read json file
    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    //check for network
    public static boolean isNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //alert box function - MainActivity
    private void alert(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", null);
        alert.show();

    }

    public void recognizeInk(View view) {
        strokeHandler.recognize();
    }

    public void setSample1JSON(View view) {
        //set sample 1 json file to process
        //load stroke data
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("1.json"));
            JSONArray jsonArray = jsonObject.getJSONArray("values");
            StrokeDataHandler.allStrokesForEachAuthor = getStrokeData(jsonArray); //holds one stroke set for each writer
        } catch (JSONException e) {
            e.printStackTrace();
        }

        alert("Info", "Sample 1 set");
    }

    public void setSample2JSON(View view) {
        //set sample 2 json file to process
        //load stroke data
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("2.json"));
            JSONArray jsonArray = jsonObject.getJSONArray("values");
            StrokeDataHandler.allStrokesForEachAuthor = getStrokeData(jsonArray); //holds one stroke set for each writer
        } catch (JSONException e) {
            e.printStackTrace();
        }

        alert("Info", "Sample 2 set");
    }

    public void setSample3JSON(View view) {
        //set sample 3 json file to process
        //load stroke data
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("3.json"));
            JSONArray jsonArray = jsonObject.getJSONArray("values");
            StrokeDataHandler.allStrokesForEachAuthor = getStrokeData(jsonArray); //holds one stroke set for each writer
        } catch (JSONException e) {
            e.printStackTrace();
        }

        alert("Info", "Sample 3 set");
    }

    public void setAman(View view) {
        //set Amans code
        strokeHandler.writer = "Aman";
        alert("Info", "Set Writer : Aman");
    }

    public void setCarolyn(View view) {
        strokeHandler.writer = "Carolyn";
        alert("Info", "Set Writer : Carolyn");
    }

    public void setChristine(View view) {
        strokeHandler.writer = "Christine";
        alert("Info", "Set Writer : Christine");
    }

    public void setClaire(View view) {
        strokeHandler.writer = "Claire";
        alert("Info", "Set Writer : Claire");
    }

    public void setJason(View view) {
        strokeHandler.writer = "Jason";
        alert("Info", "Set Writer : Jason");
    }

    public void setJohn(View view) {
        strokeHandler.writer = "John";
        alert("Info", "Set Writer : John");
    }

    public void setJoseph(View view) {
        strokeHandler.writer = "Joseph";
        alert("Info", "Set Writer : Joseph");
    }

    public void setJosh(View view) {
        strokeHandler.writer = "Josh";
        alert("Info", "Set Writer : Josh");
    }

    public void setLauren(View view) {
        strokeHandler.writer = "Lauren";
        alert("Info", "Set Writer : Lauren");
    }

    public void setMartin(View view) {
        strokeHandler.writer = "Martin";
        alert("Info", "Set Writer : Martin");
    }

    public void setMicayla(View view) {
        strokeHandler.writer = "Micayla";
        alert("Info", "Set Writer : Micayla");
    }

    public void setMikel (View view) {
        strokeHandler.writer = "Mikel";
        alert("Info", "Set Writer : Mikel");
    }

    public void setPoorna(View view) {
        strokeHandler.writer = "Poorna";
        alert("Info", "Set Writer : Poorna");
    }

    public void setRosalyn(View view) {
        strokeHandler.writer = "Rosalyn";
        alert("Info", "Set Writer : Rosalyn");
    }

    public void setSuwen(View view) {
        strokeHandler.writer = "Suwen";
        alert("Info", "Set Writer : Suwen");
    }

    public void setWhitney(View view) {
        strokeHandler.writer = "Whitney";
        alert("Info", "Set Writer : Whitney");
    }

    public void setXue(View view) {
        strokeHandler.writer = "Xue";
        alert("Info", "Set Writer : Xue");
    }

    public void clearStrokesAndText(View view) {
        EditTextView editTextView = findViewById(R.id.edit_text_view);
        strokeHandler.reset();
        editTextView.getText().clear();

    }
}