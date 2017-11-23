package com.polytechcloudapi.userapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.polytechcloudapi.userapi.deserializer.DateDeserializer;
import com.polytechcloudapi.userapi.deserializer.PositionDeserializer;
import com.polytechcloudapi.userapi.serializer.PositionSeializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 08-Nov-17.
 */
@Document(collection = "User")
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    @JsonDeserialize(using=DateDeserializer.class)
    private Date birthDay;

    @GeoSpatialIndexed
    @JsonSerialize(using= PositionSeializer.class)
    @JsonDeserialize(using=PositionDeserializer.class)
    private double[] position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }
}
