/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_fly_project.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "songs")
@NamedQueries({
    @NamedQuery(name = "SongsEntity.findAll", query = "SELECT s FROM SongsEntity s"),
    @NamedQuery(name = "SongsEntity.findById", query = "SELECT s FROM SongsEntity s WHERE s.id = :id"),
    @NamedQuery(name = "SongsEntity.findByNameSong", query = "SELECT s FROM SongsEntity s WHERE s.nameSong = :nameSong"),
    @NamedQuery(name = "SongsEntity.findByUserId", query = "SELECT s FROM SongsEntity s WHERE s.userId = :userId"),
    @NamedQuery(name = "SongsEntity.findByZaner", query = "SELECT s FROM SongsEntity s WHERE s.zaner = :zaner"),
    @NamedQuery(name = "SongsEntity.findByDate", query = "SELECT s FROM SongsEntity s WHERE s.date = :date"),
    @NamedQuery(name = "SongsEntity.findByAlbumId", query = "SELECT s FROM SongsEntity s WHERE s.albumId = :albumId")})
public class SongsEntity implements Serializable {

    @Basic(optional = false)
    @Lob
    @Column(name = "the_song")
    private byte[] theSong;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name_song")
    private String nameSong;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "zaner")
    private String zaner;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "album_id")
    private long albumId;

    public SongsEntity() {
    }

    public SongsEntity(Long id) {
        this.id = id;
    }

    public SongsEntity(Long id, String nameSong, long userId, byte[] theSong, String zaner, Date date, long albumId) {
        this.id = id;
        this.nameSong = nameSong;
        this.userId = userId;
        this.theSong = theSong;
        this.zaner = zaner;
        this.date = date;
        this.albumId = albumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getZaner() {
        return zaner;
    }

    public void setZaner(String zaner) {
        this.zaner = zaner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SongsEntity)) {
            return false;
        }
        SongsEntity other = (SongsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.SongsEntity[ id=" + id + " ]";
    }

    public byte[] getTheSong() {
        return theSong;
    }

    public void setTheSong(byte[] theSong) {
        this.theSong = theSong;
    }
    
}
