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
}
