package com.jessi.pms.models;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Patient {
    public String caseNumber;
    public String fullname;
    public String sex;
    public String physician;
    public String room;
    public String dateAdmitted;
    public String timeAdmitted;

    public Patient() { }

    public Patient(String caseNumber, String fullname, String sex, String physician, String room,
                   String dateAdmitted, String timeAdmitted) {
        super();

        this.caseNumber = caseNumber;
        this.fullname = fullname;
        this.sex = sex;
        this.physician= physician;
        this.room = room;
        this.dateAdmitted = dateAdmitted;
        this.timeAdmitted = timeAdmitted;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}
