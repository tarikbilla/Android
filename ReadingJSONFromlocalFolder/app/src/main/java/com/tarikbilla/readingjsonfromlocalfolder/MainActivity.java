package com.tarikbilla.readingjsonfromlocalfolder;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*this code for load json file from raw folder*/
        try {
            //Load File
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.name)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }

            //Parse Json
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            ArrayList<String> fields = new ArrayList<String>();
            for (int index = 0; index < jsonArray.length(); index++) {
                //Set both values into the listview
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                    fields.add(jsonObject.getString("id")+"."+jsonObject.getString("name"));
            }

            /*if you get error please extends ListActivity with MainActivity ex:" MainActivity extends ListActivity"*/
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fields));
        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        } catch (JSONException e) {
            Log.e("jsonFile", "error while parsing json");
        }


    }
}
