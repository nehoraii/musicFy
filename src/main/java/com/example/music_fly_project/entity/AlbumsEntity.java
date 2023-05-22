/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_fly_project.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "albums")
public class AlbumsEntity implements Serializable {

    @Basic(optional = false)
    @Lob
    @Column(name = "image_album")
    private byte[] imageAlbum;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "name_album")
    private String nameAlbum;
    @Basic(optional = false)
    @Column(name = "song_id")
    private long songId;

    public AlbumsEntity() {
    }

    public AlbumsEntity(Long id) {
        this.id = id;
    }

    public AlbumsEntity(Long id, long userId, String nameAlbum, long songId) {
        this.id = id;
        this.userId = userId;
        this.nameAlbum = nameAlbum;
        this.songId = songId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
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
        if (!(object instanceof AlbumsEntity)) {
            return false;
        }
        AlbumsEntity other = (AlbumsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.Albums[ id=" + id + " ]";
    }

    public byte[] getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(byte[] imageAlbum) {
        this.imageAlbum = imageAlbum;
    }
    
}
