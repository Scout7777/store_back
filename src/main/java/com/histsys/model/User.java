package com.histsys.model;

import io.github.biezhi.anima.Model;
import lombok.Data;

@Data
public class User extends Model {
    private Integer id;
    private String name;
    private String uid;
}
