package com.example.infs3605;

public class Industry {
    private Integer id;
    private String name;
    private String open;
    private String limits;
    private String distancing;
    private String hygiene;
    private String wellbeing;
    private String general;
    private String notification1Title;
    private String notification1Text;
    private String notification2Title;
    private String notification2Text;

    public Industry(Integer id, String name, String open, String limits, String distancing, String hygiene, String wellbeing, String general, String notification1Title, String notification1Text, String notification2Title, String notification2Text) {
        this.id = id;
        this.name = name;
        this.open = open;
        this.limits = limits;
        this.distancing = distancing;
        this.hygiene = hygiene;
        this.wellbeing = wellbeing;
        this.general = general;
        this.notification1Title = notification1Title;
        this.notification1Text = notification1Text;
        this.notification2Title = notification2Title;
        this.notification2Text = notification2Text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    public String getDistancing() {
        return distancing;
    }

    public void setDistancing(String distancing) {
        this.distancing = distancing;
    }

    public String getHygiene() {
        return hygiene;
    }

    public void setHygiene(String hygiene) {
        this.hygiene = hygiene;
    }

    public String getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(String wellbeing) {
        this.wellbeing = wellbeing;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getNotification1Title() {
        return notification1Title;
    }

    public void setNotification1Title(String notification1Title) {
        this.notification1Title = notification1Title;
    }

    public String getNotification1Text() {
        return notification1Text;
    }

    public void setNotification1Text(String notification1Text) {
        this.notification1Text = notification1Text;
    }

    public String getNotification2Title() {
        return notification2Title;
    }

    public void setNotification2Title(String notification2Title) {
        this.notification2Title = notification2Title;
    }

    public String getNotification2Text() {
        return notification2Text;
    }

    public void setNotification2Text(String notification2Text) {
        this.notification2Text = notification2Text;
    }
}