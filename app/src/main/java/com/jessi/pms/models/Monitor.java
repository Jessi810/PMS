package com.jessi.pms.models;

/**
 * Created by Jessi on 11/26/2016.
 */

public class Monitor {
    public String id;
    public String fullname;
    public String physician;
    public String room;
    public boolean monitoring;

    public String drug1;
    public String drug2;
    public String time1a;
    public String time1b;
    public String time1c;
    public String time2a;
    public String time2b;
    public String time2c;

    public Monitor() {
    }

    public Monitor(String id, String fullname, String physician, String room) {
        this.id = id;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
    }

    public Monitor(String id, String fullname, String physician, String room, boolean monitoring) {
        this.id = id;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
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

    public boolean isMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }

    public String getDrug1() {
        return drug1;
    }

    public void setDrug1(String drug1) {
        this.drug1 = drug1;
    }

    public String getDrug2() {
        return drug2;
    }

    public void setDrug2(String drug2) {
        this.drug2 = drug2;
    }

    public String getTime1a() {
        return time1a;
    }

    public void setTime1a(String time1a) {
        this.time1a = time1a;
    }

    public String getTime1b() {
        return time1b;
    }

    public void setTime1b(String time1b) {
        this.time1b = time1b;
    }

    public String getTime1c() {
        return time1c;
    }

    public void setTime1c(String time1c) {
        this.time1c = time1c;
    }

    public String getTime2a() {
        return time2a;
    }

    public void setTime2a(String time2a) {
        this.time2a = time2a;
    }

    public String getTime2b() {
        return time2b;
    }

    public void setTime2b(String time2b) {
        this.time2b = time2b;
    }

    public String getTime2c() {
        return time2c;
    }

    public void setTime2c(String time2c) {
        this.time2c = time2c;
    }
}
