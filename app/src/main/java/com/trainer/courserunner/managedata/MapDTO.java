package com.trainer.courserunner.managedata;

public class MapDTO {
    private double longitude;//x
    private double latitude;//y

    public MapDTO() {
        this.longitude = -1;
        this.latitude = -1;
    }

    public MapDTO(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getX() {
        return longitude;
    }

    public double getY() {
        return latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
