package com.example.hospitalapp3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

public class DoctorDetailsFragment extends Fragment {
    private Doctor selectedDoctor;
    private EditText nameEditText;
    private EditText specializationEditText;
    private EditText experienceEditText;
    private EditText qualificationsEditText;
    private Button saveButton;
    private Button deleteButton;
    private DatabaseHelper dbHelper;
    // Define an interface to communicate with the parent fragment (DoctorListFragment)
    public interface DoctorDetailsListener {
        void onDoctorDetailsUpdated();
    }

    private DoctorDetailsListener listener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the DatabaseHelper instance
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_details, container, false);

        // Retrieve the selected doctor from the arguments
        Bundle args = getArguments();
        if (args != null) {
            selectedDoctor = args.getParcelable("selected_doctor");
        }

        if (selectedDoctor != null) {
            // Initialize views
            nameEditText = view.findViewById(R.id.doctorNameEditText);
            specializationEditText = view.findViewById(R.id.doctorSpecializationEditText);
            experienceEditText = view.findViewById(R.id.doctorExperienceEditText);
            qualificationsEditText = view.findViewById(R.id.doctorQualificationsEditText);
            saveButton = view.findViewById(R.id.saveButton);
            deleteButton = view.findViewById(R.id.deleteDoctorButton);

            // Populate EditText fields with doctor's current details
            nameEditText.setText(selectedDoctor.getName());
            specializationEditText.setText(selectedDoctor.getSpecialization());
            experienceEditText.setText(String.valueOf(selectedDoctor.getExperience()));
            qualificationsEditText.setText(selectedDoctor.getQualifications());

            // Add click listener to the Save button
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the doctor's details with the new values entered in EditText fields
                    selectedDoctor.setName(nameEditText.getText().toString());
                    selectedDoctor.setSpecialization(specializationEditText.getText().toString());
                    selectedDoctor.setExperience(Integer.parseInt(experienceEditText.getText().toString()));
                    selectedDoctor.setQualifications(qualificationsEditText.getText().toString());

                    boolean isUpdated = dbHelper.updateDoctorDetails(selectedDoctor);

                    if (isUpdated) {
                        // The doctor details were successfully updated
                        if (listener != null) {
                            listener.onDoctorDetailsUpdated();
                        }
                        // Optionally, navigate back to the doctor list fragment
                        requireActivity().getSupportFragmentManager().popBackStack();

                        Toast.makeText(requireContext(), "Doctor details updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Failed to update doctor details", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Add click listener to the Delete button
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isDeleted = dbHelper.deleteDoctor(selectedDoctor.getId());

                    if (isDeleted) {
                        // The doctor was successfully deleted
                        if (listener != null) {
                            listener.onDoctorDetailsUpdated();
                        }
                        // navigate back to the doctor list fragment
                        requireActivity().getSupportFragmentManager().popBackStack();

                        Toast.makeText(requireContext(), "Doctor deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Failed to delete doctor", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Check if the parent fragment (DoctorListFragment) implements the listener interface
        if (context instanceof DoctorDetailsListener) {
            // Set the listener
            listener = (DoctorDetailsListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement DoctorDetailsListener");
        }
    }
}
