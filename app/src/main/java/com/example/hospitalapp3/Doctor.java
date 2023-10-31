package com.example.hospitalapp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private int id;
    private String name;
    private String specialization;
    private int experience;
    private String qualifications;

    public Doctor() {
        // Default constructor
    }

    public Doctor(int id, String name, String specialization, int experience, String qualifications) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.qualifications = qualifications;
    }
    // Getter and Setter methods for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                ", qualifications='" + qualifications + '\'' +
                '}';
    }
    // Parcelable implementation
    protected Doctor(Parcel in) {
        id = in.readInt();
        name = in.readString();
        specialization = in.readString();
        experience = in.readInt();
        qualifications = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(specialization);
        dest.writeInt(experience);
        dest.writeString(qualifications);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
