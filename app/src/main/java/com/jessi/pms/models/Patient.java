package com.jessi.pms.models;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Patient {
    public String caseNumber;
    public String fullName;
    public String sex;
    public String physician;
    public String room;
    public String dateAdmitted;
    public String timeAdmitted;

    public Patient() {

    }

    public Patient(String caseNumber, String fullName, String sex, String physician, String room,
                   String dateAdmitted, String timeAdmitted) {
        super();

        this.caseNumber = caseNumber;
        this.fullName= fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getDateTimeAdmitted() {
        return dateAdmitted + " " + timeAdmitted;
    }

    public void setDateTimeAdmitted(String dateAdmitted, String timeAdmitted) {
        this.dateAdmitted = dateAdmitted;
        this.timeAdmitted = timeAdmitted;
    }
}
