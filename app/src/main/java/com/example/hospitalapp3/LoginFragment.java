package com.example.hospitalapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the login fragment layout
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        // Check if savedInstanceState contains saved values
        if (savedInstanceState != null) {
            String savedUsername = savedInstanceState.getString("username");
            String savedPassword = savedInstanceState.getString("password");

            // Set the saved values to the EditText fields
            usernameEditText.setText(savedUsername);
            passwordEditText.setText(savedPassword);
        }
        // Set the saved values to the EditText fields

// Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input from EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Use the username and password for login validation
                performLogin(username, password);
            }
        });
        return view;
    }

    // Method to perform login validation
    private void performLogin(String username, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(requireActivity());

        boolean isValidLogin = dbHelper.checkUser(username, password);

        if (isValidLogin) {
            Intent serviceIntent = new Intent(requireContext(), NotificationService.class);
            requireContext().startService(serviceIntent);

            // Successful login
            // Redirect to the next screen or perform the desired action
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, new ActionsFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            // Failed login
            // Display an error message to the user
            Toast.makeText(requireContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current values of EditText fields
        //String enteredUsername = usernameEditText.getText().toString();
        //String enteredPassword = passwordEditText.getText().toString();

        outState.putString("username", "");
        outState.putString("password", "");
    }

}
