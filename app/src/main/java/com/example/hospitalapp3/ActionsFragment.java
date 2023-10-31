package com.example.hospitalapp3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActionsFragment extends Fragment {
    private Button viewDoctorsButton;
    private Toolbar toolBar;
    public ActionsFragment() {
        // Required empty public constructor
        setRetainInstance(true); // Retain the fragment instance
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        getActivity().startService(new Intent(getActivity(), NotificationService.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);

        toolBar = (Toolbar) view.findViewById(R.id.toolbar3);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolBar);
        activity.getSupportActionBar().setTitle("Admin Menu");

        viewDoctorsButton = view.findViewById(R.id.readButton);
        viewDoctorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click action here
                // You can navigate to a new fragment or activity to display the list of doctors
                // For example, you can start a new fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, new DoctorListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflate the menu defined in menuCourse
        inflater.inflate(R.menu.menu_hospital, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent;
        if (itemId == R.id.optionItem1) {
            intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemId == R.id.optionItem2) {
            intent = new Intent(getActivity(), ContentActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
