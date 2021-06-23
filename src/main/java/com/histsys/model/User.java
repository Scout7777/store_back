package com.histsys.model;

import com.blade.kit.json.JsonFormat;
import com.blade.kit.json.JsonIgnore;
import io.github.biezhi.anima.Model;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Data
@Table(name = "users")
public class User extends Model {
    private Integer id;
    private String uid;
    private Role role;
    private String avatar;

    private String name;
    private String email;
    private String telephone;
    private Status status;

    @JsonIgnore
    private String salt;
    @JsonIgnore
    private String password; // hashed

    @JsonFormat("yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat("yyyy-MM-dd HH:mm:ss")
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
