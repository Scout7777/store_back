package com.histsys.data.converter;

import com.alibaba.fastjson.JSON;
import com.histsys.data.pojo.FileInfo;

import javax.persistence.AttributeConverter;
import java.util.List;

public class FileInfoListConverter implements AttributeConverter<List<FileInfo>, String> {

    @Override
    public String convertToDatabaseColumn(List<FileInfo> fileInfo) {
        if (fileInfo == null) return null;
        return JSON.toJSONString(fileInfo);
    }

    @Override
    public List<FileInfo> convertToEntityAttribute(String s) {
        if (s == null) return null;
        return JSON.parseObject(s, List.class);
    }
}
