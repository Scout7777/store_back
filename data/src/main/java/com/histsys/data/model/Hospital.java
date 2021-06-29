package com.histsys.data.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "hospitals")
public class Hospital {
    @Id
    private String id;
    @Enumerated
    private HospitalType hospitalType;
    private String departmentName;
    private Integer authorizedBeds;

    private String subInstName; // 分支机构名称，可选

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public enum HospitalType {
        hospital, self// 医疗机构透析中心,独立透析中心
    }
}
