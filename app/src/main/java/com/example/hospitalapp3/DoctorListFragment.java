package com.example.hospitalapp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DoctorListFragment extends Fragment implements DoctorDetailsFragment.DoctorDetailsListener {
    private RecyclerView recyclerView;
    private ArrayList<Doctor> doctorList;
    private ArrayList<Doctor> filteredDoctorList; // List to hold filtered doctors
    private DoctorAdapter adapter;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        // Call the getAllDoctors method to fetch the list of doctors from the database
        doctorList = dbHelper.getAllDoctors();

        // Initialize the filteredDoctorList with all doctors initially
        filteredDoctorList = new ArrayList<>(doctorList);

        // Set the adapter for the RecyclerView with the retrieved doctor list
        adapter = new DoctorAdapter(filteredDoctorList);
        recyclerView.setAdapter(adapter);

        // Initialize and set up the SearchView
        searchView = view.findViewById(R.id.searchView);
        //searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search when the user submits the query
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search as the user types
                filterDoctorList(newText);
                return true;
            }
        });

        return view;
    }

    // Implement the callback method from the interface
    @Override
    public void onDoctorDetailsUpdated() {
        // This method will be called when doctor details are updated in DoctorDetailsFragment
        // You can update your doctor list here
        updateDoctorList();
    }

    public void updateDoctorList() {
        // Initialize the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        // Call the getAllDoctors method to fetch the updated list of doctors from the database
        doctorList = dbHelper.getAllDoctors();

        // Refresh the filteredDoctorList with the updated list
        filterDoctorList(searchView.getQuery().toString());
    }

    // Add a method to filter the doctor list based on the search query
    private void filterDoctorList(String query) {
        // Clear the current filtered list
        filteredDoctorList.clear();

        // If the query is empty, show all doctors
        if (query.isEmpty()) {
            filteredDoctorList.addAll(doctorList);
        } else {
            // Otherwise, filter doctors whose names contain the query text (case-insensitive)
            for (Doctor doctor : doctorList) {
                if (doctor.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredDoctorList.add(doctor);
                }
            }
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    // Inner DoctorAdapter class
    private class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

        private ArrayList<Doctor> doctorList;

        // Constructor to initialize the list
        private DoctorAdapter(ArrayList<Doctor> doctorList) {
            this.doctorList = doctorList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Doctor doctor = doctorList.get(position);

            // Bind data to views in the ViewHolder
            holder.idTextView.setText(String.valueOf("id: "+doctor.getId()));
            holder.nameTextView.setText("name: "+doctor.getName());
            holder.specializationTextView.setText("specialization: "+doctor.getSpecialization());
            holder.experienceTextView.setText("years of exp: "+String.valueOf(doctor.getExperience()));
            holder.qualificationsTextView.setText("qualification: "+doctor.getQualifications());

            // Add click listener to the item view
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the click event here
                    openDoctorDetailsFragment(doctor); // You can create this method
                }
            });
        }
        private void openDoctorDetailsFragment(Doctor doctor) {
            // Create a new instance of the DoctorDetailsFragment and pass the selected doctor
            DoctorDetailsFragment doctorDetailsFragment = new DoctorDetailsFragment();

            Bundle args = new Bundle();
            args.putParcelable("selected_doctor", doctor); // Parcelable for passing data
            doctorDetailsFragment.setArguments(args);

            // Replace the current fragment with the DoctorDetailsFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, doctorDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
        @Override
        public int getItemCount() {
            return doctorList.size();
        }

        // Inner ViewHolder class
        private class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView;
            TextView nameTextView;
            TextView specializationTextView;
            TextView experienceTextView;
            TextView qualificationsTextView;

            private ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.doctorID_textView);
                nameTextView = itemView.findViewById(R.id.doctorName_textView);
                specializationTextView = itemView.findViewById(R.id.doctorSpecialization_textView);
                experienceTextView = itemView.findViewById(R.id.doctorExperience_textView);
                qualificationsTextView = itemView.findViewById(R.id.doctorQualifications_textView);
                // Initialize other views here
            }
        }
    }
}
