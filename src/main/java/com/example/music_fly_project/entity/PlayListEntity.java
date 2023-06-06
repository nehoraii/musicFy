/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_fly_project.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "play_list")
@NamedQueries({
    @NamedQuery(name = "PlayListEntity.findAll", query = "SELECT p FROM PlayListEntity p"),
    @NamedQuery(name = "PlayListEntity.findById", query = "SELECT p FROM PlayListEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PlayListEntity.findByUserId", query = "SELECT p FROM PlayListEntity p WHERE p.userId = :userId"),
    @NamedQuery(name = "PlayListEntity.findByPlayListName", query = "SELECT p FROM PlayListEntity p WHERE p.playListName = :playListName"),
    @NamedQuery(name = "PlayListEntity.findBySongId", query = "SELECT p FROM PlayListEntity p WHERE p.songId = :songId"),
    @NamedQuery(name = "PlayListEntity.findByDate", query = "SELECT p FROM PlayListEntity p WHERE p.date = :date"),
    @NamedQuery(name = "PlayListEntity.findByPublic1", query = "SELECT p FROM PlayListEntity p WHERE p.public1 = :public1")})
public class PlayListEntity implements Serializable {

    @Lob
    @Column(name = "image")
    private byte[] image;

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
    @Column(name = "play_list_name")
    private String playListName;
    @Basic(optional = false)
    @Column(name = "song_id")
    private long songId;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Lob
    @Column(name = "length_play_list")
    private Object lengthPlayList;
    @Basic(optional = false)
    @Column(name = "public")
    private boolean public1;

    public PlayListEntity() {
    }

    public PlayListEntity(Long id) {
        this.id = id;
    }

    public PlayListEntity(Long id, long userId, String playListName, long songId, Date date, Object lengthPlayList, boolean public1) {
        this.id = id;
        this.userId = userId;
        this.playListName = playListName;
        this.songId = songId;
        this.date = date;
        this.lengthPlayList = lengthPlayList;
        this.public1 = public1;
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

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getLengthPlayList() {
        return lengthPlayList;
    }

    public void setLengthPlayList(Object lengthPlayList) {
        this.lengthPlayList = lengthPlayList;
    }

    public boolean getPublic1() {
        return public1;
    }

    public void setPublic1(boolean public1) {
        this.public1 = public1;
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
        if (!(object instanceof PlayListEntity)) {
            return false;
        }
        PlayListEntity other = (PlayListEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.PlayListEntity[ id=" + id + " ]";
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
}
