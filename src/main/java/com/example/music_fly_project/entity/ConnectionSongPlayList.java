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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "connection_song_play_list")
@NamedQueries({
    @NamedQuery(name = "ConnectionSongPlayList.findAll", query = "SELECT c FROM ConnectionSongPlayList c"),
    @NamedQuery(name = "ConnectionSongPlayList.findById", query = "SELECT c FROM ConnectionSongPlayList c WHERE c.id = :id"),
    @NamedQuery(name = "ConnectionSongPlayList.findByPlayListId", query = "SELECT c FROM ConnectionSongPlayList c WHERE c.playListId = :playListId"),
    @NamedQuery(name = "ConnectionSongPlayList.findBySongId", query = "SELECT c FROM ConnectionSongPlayList c WHERE c.songId = :songId")})
public class ConnectionSongPlayList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "play_list_id")
    private long playListId;
    @Basic(optional = false)
    @Column(name = "song_id")
    private long songId;

    public ConnectionSongPlayList() {
    }

    public ConnectionSongPlayList(Long id) {
        this.id = id;
    }

    public ConnectionSongPlayList(Long id, long playListId, long songId) {
        this.id = id;
        this.playListId = playListId;
        this.songId = songId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPlayListId() {
        return playListId;
    }

    public void setPlayListId(long playListId) {
        this.playListId = playListId;
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
        if (!(object instanceof ConnectionSongPlayList)) {
            return false;
        }
        ConnectionSongPlayList other = (ConnectionSongPlayList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.ConnectionSongPlayList[ id=" + id + " ]";
    }
    
}
