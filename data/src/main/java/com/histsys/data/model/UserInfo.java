package com.histsys.data.model;

import com.histsys.data.converter.FileInfoConverter;
import com.histsys.data.pojo.FileInfo;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * belongs to User
 */
@Data
@Entity
@Table(name = "user_infos")
public class UserInfo {

    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
//    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String idNo; // 身份证
    private String idType;


    // base
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;




}
