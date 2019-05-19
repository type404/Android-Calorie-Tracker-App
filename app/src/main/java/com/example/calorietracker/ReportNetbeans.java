package com.example.calorietracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tisa9
 */
/*NETBEANS CLASS*/
public class ReportNetbeans implements Parcelable {

    private static final long serialVersionUID = 1L;

    private Integer reportId;

    private Date reportDate;

    private Integer totalCalsConsumed;

    private Integer totalStepsTaken;

    private Integer setCalsGoal;

    private Users userId;

    public ReportNetbeans() {
    }

    public ReportNetbeans(Integer reportId) {
        this.reportId = reportId;
    }

    public ReportNetbeans(Integer reportId, Date reportDate, Users user, Integer totalCalsConsumed, Integer totalStepsTaken, Integer setCalsGoals) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.userId = user;
        this.totalCalsConsumed = totalCalsConsumed;
        this.totalStepsTaken = totalStepsTaken;
        this.setCalsGoal = setCalsGoals;
    }

    protected ReportNetbeans(Parcel in) {
        if (in.readByte() == 0) {
            reportId = null;
        } else {
            reportId = in.readInt();
        }
        if (in.readByte() == 0) {
            totalCalsConsumed = null;
        } else {
            totalCalsConsumed = in.readInt();
        }
        if (in.readByte() == 0) {
            totalStepsTaken = null;
        } else {
            totalStepsTaken = in.readInt();
        }
        if (in.readByte() == 0) {
            setCalsGoal = null;
        } else {
            setCalsGoal = in.readInt();
        }
        userId = in.readParcelable(Users.class.getClassLoader());
    }

    public static final Creator<ReportNetbeans> CREATOR = new Creator<ReportNetbeans>() {
        @Override
        public ReportNetbeans createFromParcel(Parcel in) {
            return new ReportNetbeans(in);
        }

        @Override
        public ReportNetbeans[] newArray(int size) {
            return new ReportNetbeans[size];
        }
    };

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getTotalCalsConsumed() {
        return totalCalsConsumed;
    }

    public void setTotalCalsConsumed(Integer totalCalsConsumed) {
        this.totalCalsConsumed = totalCalsConsumed;
    }

    public Integer getTotalStepsTaken() {
        return totalStepsTaken;
    }

    public void setTotalStepsTaken(Integer totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }

    public Integer getSetCalsGoal() {
        return setCalsGoal;
    }

    public void setSetCalsGoal(Integer setCalsGoal) {
        this.setCalsGoal = setCalsGoal;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return "restws.Report[ reportId=" + reportId + " ]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (reportId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reportId);
        }
        if (totalCalsConsumed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalCalsConsumed);
        }
        if (totalStepsTaken == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalStepsTaken);
        }
        if (setCalsGoal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(setCalsGoal);
        }
        dest.writeParcelable(userId, flags);
    }
}

