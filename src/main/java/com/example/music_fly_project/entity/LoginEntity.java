/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_fly_project.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "login")
@NamedQueries({
    @NamedQuery(name = "LoginEntity.findAll", query = "SELECT l FROM LoginEntity l"),
    @NamedQuery(name = "LoginEntity.findById", query = "SELECT l FROM LoginEntity l WHERE l.id = :id"),
    @NamedQuery(name = "LoginEntity.findByUserId", query = "SELECT l FROM LoginEntity l WHERE l.userId = :userId"),
    @NamedQuery(name = "LoginEntity.findByPass", query = "SELECT l FROM LoginEntity l WHERE l.pass = :pass"),
    @NamedQuery(name = "LoginEntity.findByIp", query = "SELECT l FROM LoginEntity l WHERE l.ip = :ip"),
    @NamedQuery(name = "LoginEntity.findBySec", query = "SELECT l FROM LoginEntity l WHERE l.sec = :sec"),
    @NamedQuery(name = "LoginEntity.findByDate", query = "SELECT l FROM LoginEntity l WHERE l.date = :date")})
public class LoginEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "sec")
    private boolean sec;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public LoginEntity() {
    }

    public LoginEntity(Long id) {
        this.id = id;
    }

    public LoginEntity(Long id, String ip, boolean sec, Date date) {
        this.id = id;
        this.ip = ip;
        this.sec = sec;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean getSec() {
        return sec;
    }

    public void setSec(boolean sec) {
        this.sec = sec;
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
        if (!(object instanceof LoginEntity)) {
            return false;
        }
        LoginEntity other = (LoginEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.practidb.LoginEntity[ id=" + id + " ]";
    }
    
}
