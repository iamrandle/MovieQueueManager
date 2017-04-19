package com.tylerrb.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Button btn;
    Button delbtn;
    EditText textfield;
    TextView view;
    RequestQueue rq;
    MovieDBHandler dbHandler;
     View mainact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        delbtn = (Button)findViewById(R.id.delbtn);
        textfield = (EditText)findViewById(R.id.editText);
        view = (TextView)findViewById(R.id.textView);
        rq = Volley.newRequestQueue(this);
        dbHandler = new MovieDBHandler(this, null, null, 1);
        view.setText(dbHandler.databaseToString());
        mainact = findViewById(R.id.activity_main);

        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View V)
            {
                Log.v("onResponse", "click");

                String url = "http://www.omdbapi.com/?t=" + textfield.getText().toString()+"&r=json" ;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response){
                                try {
                                    Log.v("onResponse", "try");

                                    if(response.getString("Response").equals("True")) {
                                        //view.setText(response.getString("Title") + " was added successfully");
                                        dbHandler.addMovie(response);
                                        view.setText(dbHandler.databaseToString());
                                        mainact.setBackgroundColor(Color.CYAN);
                                        Log.v("onResponse", "success");
                                    }
                                    else
                                    {
                                        view.setText("Error, movie not found");
                                        Log.v("onResponse", "fail");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        null
                );
                rq.add(jsonObjectRequest);
                textfield.setText("");
            }
        });
        delbtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View V){
                dbHandler.deleteMovie(textfield.getText().toString());
                textfield.setText("");
                view.setText(dbHandler.databaseToString());
            }
        });

    }
}
