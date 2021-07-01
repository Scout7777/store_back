package com.histsys.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Data
@Table(name = "users")
@Entity
public class User {
    // 由 hospitalId 和 id 唯一确定一个 user，为方便操作，此处 id 也视为唯一
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 对应 staffId
//    @ManyToOne
//    @JoinColumn(name = "hospital_id")
//    private Hospital hospital;

    @Enumerated
    private Role role;

    private String staffNo; // 系统自动生成，一般6位，可用此登录?
    private String name;
    private String avatar;
    private String email;
    private String telephone;

    @Enumerated
    private Status status; // 账号状态

    @JsonIgnore
    private String salt;
    // @Getter(AccessLevel.PRIVATE)
    @JsonIgnore
    private String password; // hashed


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public static enum Role {
        admin,
        doctor,
        nurse,
        engineer
    }

    public static enum Status {
        active,
        disable
    }

    // 更新密码时会重置盐
    public void setPassword(String password) {
        if (password == null) {
            this.password = null;
            return;
        }
        this.resetSalt();
        this.password = DigestUtils.sha256Hex(this.salt + password);
    }

    public void resetSalt() {
        byte[] array = new byte[4]; // length is bounded by 4
        new Random().nextBytes(array);
        this.setSalt(new String(array, StandardCharsets.UTF_8));
    }

    public boolean isPasswordCorrect(String password) {
        String distPw = DigestUtils.sha256Hex(salt + password);
        return distPw.equals(this.password);
    }
}
