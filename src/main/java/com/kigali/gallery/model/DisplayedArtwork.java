package com.kigali.gallery.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "displayed_artwork")
public class DisplayedArtwork {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private int priceRwf;

    private LocalDate cataloguedDate;

    @ManyToOne
    @JoinColumn(name = "exhibition_booking_id")
    private ExhibitionBooking exhibitionBooking;

    public DisplayedArtwork() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceRwf() {
        return priceRwf;
    }

    public void setPriceRwf(int priceRwf) {
        this.priceRwf = priceRwf;
    }

    public LocalDate getCataloguedDate() {
        return cataloguedDate;
    }

    public void setCataloguedDate(LocalDate cataloguedDate) {
        this.cataloguedDate = cataloguedDate;
    }

    public ExhibitionBooking getExhibitionBooking() {
        return exhibitionBooking;
    }

    public void setExhibitionBooking(ExhibitionBooking exhibitionBooking) {
        this.exhibitionBooking = exhibitionBooking;
    }

}
