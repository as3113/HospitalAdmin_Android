package com.example.hospitalapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements DoctorDetailsFragment.DoctorDetailsListener{
    private FragmentManager fragmentManager;
    // Implement the listener method, but leave it blank if you don't need to perform any actions.
    @Override
    public void onDoctorDetailsUpdated() {
        // You can leave this method blank if you don't need to do anything in the MainActivity.
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // Load the LoginFragment initially
        loadFragment(new LoginFragment());
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d("TAG", "method onSaveInstance() called");

    }

}