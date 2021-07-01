package com.histsys.web._view;

import com.histsys.data.model.User;
import com.histsys.data.model.UserInfo;
import com.histsys.data.pojo.FileInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class UserView {
//    private String hospitalId;
    private Long id;
    private User.Role role;
    private String staffNo;
    private String avatar;
    private String name;
    private String email;
    private String telephone;

    // user info
    private String idNo; // 身份证
    private String idType;
    private String technicalPosition; //
    private String staffLevel;

    private Boolean isAdvanced; // 是否进修
    private UserInfo.StaffStatus staffStatus; // 在职离岗信息
    private UserInfo.PostType postType; // 编制类型
    private String hospitalPosition;

    // 证书信息
    private String practiceCertificate;
    private String practiceCertificateNo;
    private String qualificationCertificate;
    private String qualificationType;

    private FileInfo diplomaFile;
    private FileInfo degreeCertificateFile;
    private FileInfo trainingCertificateFile;
    private FileInfo practiceCertificateFile;
    private FileInfo qualificationCertificateFile;

    // 血透信息
    private String trainingName;
    private Date trainingDate;
    private Date inDate;
    private Date outDate;

    private Date createdAt;
    private Date updatedAt;

    private User.Status status;


    public static UserView toView(User user) {
        UserView view = new UserView();
        if (user.getUserInfo() != null)
            BeanUtils.copyProperties(user.getUserInfo(), view);
//        if (user.getHospital() != null) {
//            BeanUtils.copyProperties(user.getHospital(), view);
//            view.setHospitalId(user.getHospital().getId());
//        }
        BeanUtils.copyProperties(user, view);
        view.setCreatedAt(user.getCreatedAt());
        view.setUpdatedAt(user.getUpdatedAt());
        return view;
    }
}
