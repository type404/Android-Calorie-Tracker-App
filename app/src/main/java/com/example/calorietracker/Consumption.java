package com.example.calorietracker;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
/*NETBEANS CLASS*/
public class Consumption {

    private static final long serialVersionUID = 1L;
    private Integer consId;
    private Date consDate;
    private int quantity;
    private Food foodId;
    private Users userId;

    public Consumption() {
    }

    public Consumption(Integer consId) {
        this.consId = consId;
    }

    public Consumption(Integer consId, Users user, Food food, Date consDate, int quantity) {
        this.consId = consId;
        this.userId = user;
        this.foodId = food;
        this.consDate = consDate;
        this.quantity = quantity;
    }

    public Integer getConsId() {
        return consId;
    }

    public void setConsId(Integer consId) {
        this.consId = consId;
    }

    public Date getConsDate() {
        return consDate;
    }

    public void setConsDate(Date consDate) {
        this.consDate = consDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
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
        hash += (consId != null ? consId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.consId == null && other.consId != null) || (this.consId != null && !this.consId.equals(other.consId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Consumption[ consId=" + consId + " ]";
    }

    public static Integer createID()
    {
        Integer idCounter = 120;
        return idCounter++;
    }
}
