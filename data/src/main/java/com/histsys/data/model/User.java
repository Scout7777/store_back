package com.histsys.data.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uid;
    @Enumerated
    private Role role;
    private String avatar;

    private String name;
    private String email;
    private String telephone;
    @Enumerated
    private Status status;

    private String salt;
//    @Getter(AccessLevel.PRIVATE)
    private String password; // hashed

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
