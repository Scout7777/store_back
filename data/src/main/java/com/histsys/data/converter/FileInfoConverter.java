package com.histsys.data.converter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.histsys.data.pojo.FileInfo;

import javax.persistence.AttributeConverter;

public class FileInfoConverter implements AttributeConverter<FileInfo, String> {

    @Override
    public String convertToDatabaseColumn(FileInfo fileInfo) {
        if (fileInfo == null) return null;
        return JSON.toJSONString(fileInfo);
    }

    @Override
    public FileInfo convertToEntityAttribute(String s) {
        if (s == null) return null;
        return JSON.parseObject(s, FileInfo.class);
    }
}
