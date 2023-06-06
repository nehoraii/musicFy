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
@Table(name = "password")
@NamedQueries({
    @NamedQuery(name = "PasswordEntity.findAll", query = "SELECT p FROM PasswordEntity p"),
    @NamedQuery(name = "PasswordEntity.findById", query = "SELECT p FROM PasswordEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PasswordEntity.findByUserId", query = "SELECT p FROM PasswordEntity p WHERE p.userId = :userId"),
    @NamedQuery(name = "PasswordEntity.findByPass", query = "SELECT p FROM PasswordEntity p WHERE p.pass = :pass"),
    @NamedQuery(name = "PasswordEntity.findByDate", query = "SELECT p FROM PasswordEntity p WHERE p.date = :date")})
public class PasswordEntity implements Serializable {

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
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public PasswordEntity() {
    }

    public PasswordEntity(Long id) {
        this.id = id;
    }

    public PasswordEntity(Long id, long userId, String pass, Date date) {
        this.id = id;
        this.userId = userId;
        this.pass = pass;
        this.date = date;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof PasswordEntity)) {
            return false;
        }
        PasswordEntity other = (PasswordEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.PasswordEntity[ id=" + id + " ]";
    }
    
}
