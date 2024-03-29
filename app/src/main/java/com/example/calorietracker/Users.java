package com.example.calorietracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
public class Users implements Parcelable {

    private Integer userId;
    private String firstname;
    private String surname;
    private String email;
    private Date dob;
    private int heightCms;
    private int weightKgs;
    private Character gender;
    private String address;
    private String postcode;
    private int levelsOfActivity;
    private int stepsPerMile;
    private Collection<Credential> credentialCollection;
    private Collection<ReportNetbeans> reportCollection;
    private Collection<Consumption> consumptionCollection;

    public Users() {
    }
    /*NETBEANS CLASS*/

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users( Integer userId, String firstname, String lastname, String email, Date dob, int heightCms, int weightKgs, Character gender, String address, String postcode, int levelsOfActivity, int stepsPerMile) {
        this.userId = userId;
        this.firstname = firstname;
        this.surname = lastname;
        this.email = email;
        this.dob = dob;
        this.heightCms = heightCms;
        this.weightKgs = weightKgs;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelsOfActivity = levelsOfActivity;
        this.stepsPerMile = stepsPerMile;
    }


    protected Users(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        firstname = in.readString();
        surname = in.readString();
        email = in.readString();
        heightCms = in.readInt();
        weightKgs = in.readInt();
        int tmpGender = in.readInt();
        gender = tmpGender != Integer.MAX_VALUE ? (char) tmpGender : null;
        address = in.readString();
        postcode = in.readString();
        levelsOfActivity = in.readInt();
        stepsPerMile = in.readInt();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public static String createID()
    {
        Integer idCounter = 6;
        return String.valueOf(idCounter++);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getHeightCms() {
        return heightCms;
    }

    public void setHeightCms(int heightCms) {
        this.heightCms = heightCms;
    }

    public int getWeightKgs() {
        return weightKgs;
    }

    public void setWeightKgs(int weightKgs) {
        this.weightKgs = weightKgs;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getLevelsOfActivity() {
        return levelsOfActivity;
    }

    public void setLevelsOfActivity(int levelsOfActivity) {
        this.levelsOfActivity = levelsOfActivity;
    }

    public int getStepsPerMile() {
        return stepsPerMile;
    }

    public void setStepsPerMile(int stepsPerMile) {
        this.stepsPerMile = stepsPerMile;
    }
//    public int getAge() {
//        Date dob = this.getDob();
//        LocalDate dobLocal = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate now = LocalDate.now();
//        Period diff = Period.between(dobLocal,now);
//        return diff.getYears();
//    }
//
    public Collection<Credential> getCredentialCollection() {
        return credentialCollection;
    }

    public void setCredentialCollection(Collection<Credential> credentialCollection) {
        this.credentialCollection = credentialCollection;
    }

    public Collection<ReportNetbeans> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<ReportNetbeans> reportCollection) {
        this.reportCollection = reportCollection;
    }
    public Collection<Consumption> getConsumptionCollection() {
        return consumptionCollection;
    }

    public void setConsumptionCollection(Collection<Consumption> consumptionCollection) {
        this.consumptionCollection = consumptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Users[ userId=" + userId + " ]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(firstname);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeInt(heightCms);
        dest.writeInt(weightKgs);
        dest.writeInt(gender != null ? (int) gender : Integer.MAX_VALUE);
        dest.writeString(address);
        dest.writeString(postcode);
        dest.writeInt(levelsOfActivity);
        dest.writeInt(stepsPerMile);
    }
}

