package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_baiduid")
public class SystemBaiduid extends BaseEntity {

    @Id
    @Column(name = "id")
    private long id;

    private String hostName;

    private String port;

    private Integer type;

    private Date launchDate;

    private Timestamp  modified;

    private Timestamp created;
}