package com.example.hospitalapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContentActivity extends AppCompatActivity {
    private TextView courseContentTextView;
    private Button callServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // Get the views
        courseContentTextView = findViewById(R.id.jsonTextView);
        callServiceButton = findViewById(R.id.getDataButton);

        callServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Consume the cat fact web service
                String uri = "https://catfact.ninja/fact";
                new AsyncHttpClient().get(uri, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String res = new String(responseBody);
                            // Parse the JSON response
                            JSONObject jsonResponse = new JSONObject(res);

                            // Extract the "fact" field from the JSON response
                            String fact = jsonResponse.getString("fact");

                            // Display the cat fact in the TextView
                            courseContentTextView.setText(fact);
                        } catch (JSONException e) {
                            courseContentTextView.setText("Error parsing response");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        courseContentTextView.setText("Error calling web service");
                    }
                });
            }
        });
    }
}
