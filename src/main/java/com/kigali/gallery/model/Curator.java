package com.kigali.gallery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "curator")
public class Curator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "exhibition_hall_id")
    private ExhibitionHall exhibitionHall;

    @ManyToMany
    @JoinTable(
        name = "curator_art_style",
        joinColumns = @JoinColumn(name="curator_id"),
        inverseJoinColumns = @JoinColumn(name="art_style_id")
    )
    private List<ArtStyle> artStyles = new ArrayList<>();

    public Curator() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ExhibitionHall getExhibitionHall() {
        return exhibitionHall;
    }

    public void setExhibitionHall(ExhibitionHall exhibitionHall) {
        this.exhibitionHall = exhibitionHall;
    }

    public List<ArtStyle> getArtStyles() {
        return artStyles;
    }
    
    public void setArtStyles(List<ArtStyle> artStyles) {
        this.artStyles = artStyles;
    }

    
}
