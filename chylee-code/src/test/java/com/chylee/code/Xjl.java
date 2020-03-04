package com.chylee.code;

public class Xjl {
    private String COMPANY;
    private String CREATOR;
    private String USR_GROUP;
    private String CREATE_DATE;
    private String MODIFIER;
    private String MODI_DATE;
    private int FLAG;

    public Xjl() {
        this.COMPANY = "XJL";
        this.CREATOR = "fxiaok";
        this.USR_GROUP = "XJL02";
        this.FLAG = 1;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR;
    }

    public String getUSR_GROUP() {
        return USR_GROUP;
    }

    public void setUSR_GROUP(String USR_GROUP) {
        this.USR_GROUP = USR_GROUP;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getMODIFIER() {
        return MODIFIER;
    }

    public void setMODIFIER(String MODIFIER) {
        this.MODIFIER = MODIFIER;
    }

    public String getMODI_DATE() {
        return MODI_DATE;
    }

    public void setMODI_DATE(String MODI_DATE) {
        this.MODI_DATE = MODI_DATE;
    }

    public int getFLAG() {
        return FLAG;
    }

    public void setFLAG(int FLAG) {
        this.FLAG = FLAG;
    }
}
