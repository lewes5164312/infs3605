package com.example.infs3605;

public class Industry {
    private Integer id;
    private String name;
    private String open;
    private String limits;
    private String distancing;
    private String entitlements;
    private String hygiene;
    private String records;
    private String notificationTitle;
    private String notificationText;

    public Industry(Integer id, String name, String open, String limits, String distancing, String entitlements, String hygiene, String records, String notificationTitle, String notificationText) {
        this.id = id;
        this.name = name;
        this.open = open;
        this.limits = limits;
        this.distancing = distancing;
        this.entitlements = entitlements;
        this.hygiene = hygiene;
        this.records = records;
        this.notificationTitle = notificationTitle;
        this.notificationText = notificationText;
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

    public String getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(String entitlements) {
        this.entitlements = entitlements;
    }

    public String getHygiene() {
        return hygiene;
    }

    public void setHygiene(String hygiene) {
        this.hygiene = hygiene;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

}