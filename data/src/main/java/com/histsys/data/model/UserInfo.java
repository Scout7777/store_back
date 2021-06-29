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
    private String technicalPosition; //
    private String staffLevel;

    private Boolean isAdvanced; // 是否进修
    @Enumerated
    private StaffStatus staffStatus; // 在职离岗信息
    @Enumerated
    private PostType postType; // 编制类型

    private String hospitalPosition;

    // 证书信息
    @Column(columnDefinition="TEXT")
    private String practiceCertificate;
    private String practiceCertificateNo;
    @Column(columnDefinition="TEXT")
    private String qualificationCertificate;
    private String qualificationType;

    @Column(columnDefinition="TEXT")
    @Convert(converter = FileInfoConverter.class)
    private FileInfo diplomaFile; // json
    @Column(columnDefinition="TEXT")
    @Convert(converter = FileInfoConverter.class)
    private FileInfo degreeCertificateFile;
    @Column(columnDefinition="TEXT")
    @Convert(converter = FileInfoConverter.class)
    private FileInfo trainingCertificateFile;
    @Column(columnDefinition="TEXT")
    @Convert(converter = FileInfoConverter.class)
    private FileInfo practiceCertificateFile;
    @Column(columnDefinition="TEXT")
    @Convert(converter = FileInfoConverter.class)
    private FileInfo qualificationCertificateFile;

    // 血透信息
    private String trainingName; // 培训名称
    private Date trainingDate;
    private Date inDate;
    private Date outDate;

    // base
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public enum StaffStatus {
        online, offline, // 在岗、离岗
    }
    public enum PostType {
        fullTime, partTime, // 全职、兼职
    }


}
