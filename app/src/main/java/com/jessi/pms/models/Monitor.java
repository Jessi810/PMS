package com.jessi.pms.models;

/**
 * Created by Jessi on 11/26/2016.
 */

public class Monitor {
    public String id;
    public String fullname;
    public String nurseAssigned;
    public String room;
    public String medicine;
    public String[] medicines;
    public boolean monitoring;

    public Monitor() {}

    public Monitor(String id, String fullname, String nurseAssigned, String room, String medicine) {
        super();

        this.id = id;
        this.fullname = fullname;
        this.nurseAssigned = nurseAssigned;
        this.room = room;
        this.medicine = medicine;
    }

    public Monitor(String id, String fullname, String nurseAssigned, String room, String[] medicines) {
        super();

        this.id = id;
        this.fullname = fullname;
        this.nurseAssigned = nurseAssigned;
        this.room = room;
        this.medicines = medicines;
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

    public String getNurseAssigned() {
        return nurseAssigned;
    }

    public void setNurseAssigned(String nurseAssigned) {
        this.nurseAssigned = nurseAssigned;
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

    public boolean getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }
}
