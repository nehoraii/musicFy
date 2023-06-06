/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_fly_project.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "connection_song_album")
@NamedQueries({
    @NamedQuery(name = "ConnectionSongAlbum.findAll", query = "SELECT c FROM ConnectionSongAlbum c"),
    @NamedQuery(name = "ConnectionSongAlbum.findById", query = "SELECT c FROM ConnectionSongAlbum c WHERE c.id = :id"),
    @NamedQuery(name = "ConnectionSongAlbum.findByAlbumId", query = "SELECT c FROM ConnectionSongAlbum c WHERE c.albumId = :albumId"),
    @NamedQuery(name = "ConnectionSongAlbum.findBySongId", query = "SELECT c FROM ConnectionSongAlbum c WHERE c.songId = :songId")})
public class ConnectionSongAlbum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "album_id")
    private long albumId;
    @Basic(optional = false)
    @Column(name = "song_id")
    private long songId;

    public ConnectionSongAlbum() {
    }

    public ConnectionSongAlbum(Long id) {
        this.id = id;
    }

    public ConnectionSongAlbum(Long id, long albumId, long songId) {
        this.id = id;
        this.albumId = albumId;
        this.songId = songId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
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
        if (!(object instanceof ConnectionSongAlbum)) {
            return false;
        }
        ConnectionSongAlbum other = (ConnectionSongAlbum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.ConnectionSongAlbum[ id=" + id + " ]";
    }
    
}
