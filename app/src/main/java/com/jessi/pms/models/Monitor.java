package com.jessi.pms.models;

import java.util.Map;

/**
 * Created by Jessi on 11/21/2016.
 */

public class Monitor {
    public String id;
    public String fullname;
    public String nurseAssigned;
    public String room;
    //public Map<String, String> medicines;
    public String medicine;

    public Monitor() {}

    public Monitor(String id, String fullname, String nurseAssigned, String room, String medicine) {
        super();

        this.id = id;
        this.fullname = fullname;
        this.nurseAssigned = nurseAssigned;
        this.room = room;
        this.medicine = medicine;
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

//    public Map<String, String> getMedicines() {
//        return medicines;
//    }
//
//    public void setMedicines(Map<String, String> medicines) {
//        this.medicines = medicines;
//    }
}
