package com.jessi.pms.models;

/**
 * Created by Jessi on 11/26/2016.
 */

public class Monitor {
    public String id;
    public String fullname;
    public String physician;
    public String room;
    public String medicine;
    public boolean monitoring;

    public Monitor() {
    }

    public Monitor(String id, String fullname, String physician, String room, String medicine) {
        this.id = id;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
        this.medicine = medicine;
    }

    public Monitor(String id, String fullname, String physician, String room, String medicine, boolean monitoring) {
        this.id = id;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
        this.medicine = medicine;
        this.monitoring = monitoring;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }
}
