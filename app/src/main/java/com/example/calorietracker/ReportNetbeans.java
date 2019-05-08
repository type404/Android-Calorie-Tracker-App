package com.example.calorietracker;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tisa9
 */

public class ReportNetbeans {

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

    public ReportNetbeans(Integer reportId, Date reportDate) {
        this.reportId = reportId;
        this.reportDate = reportDate;
    }

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

}

