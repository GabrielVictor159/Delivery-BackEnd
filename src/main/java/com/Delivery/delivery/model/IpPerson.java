package com.Delivery.delivery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ip_Person")
public class IpPerson implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column(name = "ipCode", length = 40, nullable = true, unique = true)
    private String ipCode;

    @Column(name = "country", length = 60, nullable = true)
    private String country;

    @Column(name = "region", length = 10, nullable = true)
    private String region;

    @Column(name = "city", length = 80, nullable = true)
    private String city;

    @Column(name = "zip", length = 20, nullable = true)
    private String zip;

    @Column(name = "lat", precision = 10, scale = 6, nullable = true)
    private BigDecimal lat;

    @Column(name = "lon", precision = 10, scale = 6, nullable = true)
    private BigDecimal lon;

    @Column(name = "timezone", length = 100, nullable = true)
    private String timezone;

    @Column(name = "isp", length = 140, nullable = true)
    private String isp;

    @Column(name = "org", length = 244, nullable = true)
    private String org;

    @Column(name = "assinature", length = 244, nullable = true)
    private String assinature;

    @Column(name = "banned")
    private Boolean banned;

    public IpPerson() {
        this.banned = false;
    }

    public IpPerson(String ipCode, String country, String region, String city, String zip, BigDecimal lat,
            BigDecimal lon, String timezone, String isp, String org, String assinature) {
        this.id = UUID.randomUUID();
        this.ipCode = ipCode;
        this.country = country;
        this.region = region;
        this.city = city;
        this.zip = zip;
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.isp = isp;
        this.org = org;
        this.assinature = assinature;
        this.banned = false;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIpCode() {
        return this.ipCode;
    }

    public void setIpCode(String ipCode) {
        this.ipCode = ipCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public BigDecimal getLat() {
        return this.lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return this.lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getIsp() {
        return this.isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getAssinature() {
        return this.assinature;
    }

    public void setAssinature(String assinature) {
        this.assinature = assinature;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "IpPerson [id=" + id + ", ipCode=" + ipCode + ", country=" + country + ", region=" + region + ", city="
                + city + ", zip=" + zip + ", lat=" + lat + ", lon=" + lon + ", timezone=" + timezone + ", isp=" + isp
                + ", org=" + org + ", assinature=" + assinature + ", banned=" + banned + "]";
    }

}
