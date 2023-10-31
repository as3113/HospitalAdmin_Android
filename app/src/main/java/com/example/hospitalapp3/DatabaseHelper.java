package com.example.hospitalapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "doctorAdmin.db";
    private static final int DATABASE_VERSION = 1;
    //User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    // Doctor table
    private static final String TABLE_DOCTORS = "doctors";
    private static final String COLUMN_DOCTOR_ID = "id"; // Updated to match the Doctor class
    private static final String COLUMN_DOCTOR_NAME = "name"; // Updated to match the Doctor class
    private static final String COLUMN_SPECIALIZATION = "specialization"; // Updated to match the Doctor class
    private static final String COLUMN_EXPERIENCE = "experience"; // Updated to match the Doctor class
    private static final String COLUMN_QUALIFICATIONS = "qualifications"; // Updated to match the Doctor class
    private static boolean isInitialUserDataInserted = false;
    private static boolean isInitialDoctorDataInserted = false;
    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_DOCTORS + "("
                + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DOCTOR_NAME + " TEXT,"
                + COLUMN_SPECIALIZATION + " TEXT,"
                + COLUMN_EXPERIENCE + " INTEGER,"
                + COLUMN_QUALIFICATIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_DOCTORS_TABLE);

        // Insert some initial user data
        if (!isInitialUserDataInserted) {
            insertInitialUserData(db);
            isInitialUserDataInserted = true;
        }
        if (!isInitialDoctorDataInserted) {
            insertInitialDoctorData(db);
            isInitialDoctorDataInserted = true;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        onCreate(db);
    }
    // method to retrieve a list of doctors from the database
    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_DOCTOR_ID,
                COLUMN_DOCTOR_NAME,
                COLUMN_SPECIALIZATION,
                COLUMN_EXPERIENCE,
                COLUMN_QUALIFICATIONS
        };

        Cursor cursor = db.query(
                TABLE_DOCTORS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String specialization = cursor.getString(2);
                int experience = cursor.getInt(3);
                String qualifications = cursor.getString(4);

                Doctor doctor = new Doctor(id, name, specialization, experience, qualifications);
                doctorList.add(doctor);
            }

            cursor.close();
        }

        return doctorList;
    }
    // Delete a doctor by ID
    public boolean deleteDoctor(int doctorId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to delete the specific doctor by ID
        String whereClause = COLUMN_DOCTOR_ID + "=?";
        String[] whereArgs = { String.valueOf(doctorId) };

        // Perform the delete operation
        int rowsAffected = db.delete(TABLE_DOCTORS, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Check if the delete was successful
        return rowsAffected > 0;
    }
    // Update doctor details in the database
    public boolean updateDoctorDetails(Doctor doctor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DOCTOR_NAME, doctor.getName());
        values.put(COLUMN_SPECIALIZATION, doctor.getSpecialization());
        values.put(COLUMN_EXPERIENCE, doctor.getExperience());
        values.put(COLUMN_QUALIFICATIONS, doctor.getQualifications());

        // Define the WHERE clause to update the specific doctor by ID
        String whereClause = COLUMN_DOCTOR_ID + "=?";
        String[] whereArgs = {String.valueOf(doctor.getId())};

        // Perform the update operation
        int rowsAffected = db.update(TABLE_DOCTORS, values, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Check if the update was successful
        return rowsAffected > 0;
    }
    // Validate login
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }
    // Method to insert initial user data if the table doesn't exist
    private void insertInitialUserData(SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, "adrian");
        values.put(COLUMN_PASSWORD, "1234");
        db.insert(TABLE_USERS, null, values);

        values.clear();

        values.put(COLUMN_USERNAME, "user2");
        values.put(COLUMN_PASSWORD, "password2");
        db.insert(TABLE_USERS, null, values);

    }
    private void insertInitialDoctorData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Smith");
        values.put(COLUMN_SPECIALIZATION, "Cardiologist");
        values.put(COLUMN_EXPERIENCE, 10);
        values.put(COLUMN_QUALIFICATIONS, "MD, FACC");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Johnson");
        values.put(COLUMN_SPECIALIZATION, "Pediatrician");
        values.put(COLUMN_EXPERIENCE, 8);
        values.put(COLUMN_QUALIFICATIONS, "MD, FAAP");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Anderson");
        values.put(COLUMN_SPECIALIZATION, "Dermatologist");
        values.put(COLUMN_EXPERIENCE, 15);
        values.put(COLUMN_QUALIFICATIONS, "MD, AAD");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Patel");
        values.put(COLUMN_SPECIALIZATION, "Orthopedic Surgeon");
        values.put(COLUMN_EXPERIENCE, 12);
        values.put(COLUMN_QUALIFICATIONS, "MD, FAAOS");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Wilson");
        values.put(COLUMN_SPECIALIZATION, "Obstetrician-Gynecologist");
        values.put(COLUMN_EXPERIENCE, 18);
        values.put(COLUMN_QUALIFICATIONS, "MD, FACOG");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Brown");
        values.put(COLUMN_SPECIALIZATION, "Neurologist");
        values.put(COLUMN_EXPERIENCE, 14);
        values.put(COLUMN_QUALIFICATIONS, "MD, FAAN");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Miller");
        values.put(COLUMN_SPECIALIZATION, "Ophthalmologist");
        values.put(COLUMN_EXPERIENCE, 11);
        values.put(COLUMN_QUALIFICATIONS, "MD, FACS");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Davis");
        values.put(COLUMN_SPECIALIZATION, "Psychiatrist");
        values.put(COLUMN_EXPERIENCE, 9);
        values.put(COLUMN_QUALIFICATIONS, "MD, DABPN");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Martinez");
        values.put(COLUMN_SPECIALIZATION, "Dentist");
        values.put(COLUMN_EXPERIENCE, 7);
        values.put(COLUMN_QUALIFICATIONS, "DDS");
        db.insert(TABLE_DOCTORS, null, values);

        values.clear();

        values.put(COLUMN_DOCTOR_NAME, "Dr. Garcia");
        values.put(COLUMN_SPECIALIZATION, "Urologist");
        values.put(COLUMN_EXPERIENCE, 13);
        values.put(COLUMN_QUALIFICATIONS, "MD, FACS");
        db.insert(TABLE_DOCTORS, null, values);
    }
}
