package com.example.proyecto_firebase;

import java.util.Date;

public class SeriesHelperClass {
    private String id;
    private String title;
    private String genre;
    private int seasonsWatched;
    private int totalSeasons;
    private boolean completed;
    private Date dateWatched;


    // Constructor completo
    public SeriesHelperClass(String id, String title, String genre, int seasonsWatched, int totalSeason, boolean completed, Date dateWatched) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.seasonsWatched = seasonsWatched;
        this.totalSeasons = totalSeason;
        this.completed = completed;
        this.dateWatched = dateWatched;
    }

    // Constructor vacío
    public SeriesHelperClass() {
    }

    public SeriesHelperClass(String title, String genre, int seasonsWatched, int totalSeasons, boolean completed, Date dateWatched) {
    }

    // Getters y setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSeasonsWatched() {
        return seasonsWatched;
    }

    public void setSeasonsWatched(int seasonsWatched) {
        this.seasonsWatched = seasonsWatched;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(int totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDateWatched() {
        return dateWatched;
    }

    public void setDateWatched(Date dateWatched) {
        this.dateWatched = dateWatched;
    }

    // Método toString opcional para facilitar la visualización de la serie
    @Override
    public String toString() {
        return "SeriesHelperClass{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", seasonsWatched=" + seasonsWatched +
                ", totalSeasons=" + totalSeasons +
                ", completed=" + completed +
                ", dateWatched=" + dateWatched +
                '}';
    }
}