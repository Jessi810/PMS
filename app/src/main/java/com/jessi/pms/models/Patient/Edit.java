package com.jessi.pms.models.Patient;

/**
 * Created by Jessi on 12/1/2016.
 */

public class Edit {
    public String key;
    public String fullname;
    public String physician;
    public String dateAdmitted;
    public String timeAdmitted;
    public String room;
    public String drug1;
    public String drug2;
    public String time1a;
    public String time1b;
    public String time1c;
    public String time2a;
    public String time2b;
    public String time2c;

    public Edit() {
    }

    public Edit(String key, String fullname, String physician, String room) {
        this.key = key;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
    }

    public Edit(String drug1, String drug2, String time1a, String time1b, String time1c, String time2a, String time2b, String time2c) {
        this.drug1 = drug1;
        this.drug2 = drug2;
        this.time1a = time1a;
        this.time1b = time1b;
        this.time1c = time1c;
        this.time2a = time2a;
        this.time2b = time2b;
        this.time2c = time2c;
    }

    public Edit(String key, String fullname, String physician, String room, String drug1, String drug2, String time1a, String time1b, String time1c, String time2a, String time2b, String time2c) {
        this.key = key;
        this.fullname = fullname;
        this.physician = physician;
        this.room = room;
        this.drug1 = drug1;
        this.drug2 = drug2;
        this.time1a = time1a;
        this.time1b = time1b;
        this.time1c = time1c;
        this.time2a = time2a;
        this.time2b = time2b;
        this.time2c = time2c;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDateAdmitted() {
        return dateAdmitted;
    }

    public void setDateAdmitted(String dateAdmitted) {
        this.dateAdmitted = dateAdmitted;
    }

    public String getTimeAdmitted() {
        return timeAdmitted;
    }

    public void setTimeAdmitted(String timeAdmitted) {
        this.timeAdmitted = timeAdmitted;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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
