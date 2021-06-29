package com.histsys.data.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileInfo implements Serializable {
    private String fileName;
    private String url;
    private Date createdAt;
    private String fileType;
}
