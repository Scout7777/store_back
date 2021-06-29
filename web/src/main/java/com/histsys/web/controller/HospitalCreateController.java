package com.histsys.web.controller;

import com.histsys.data.model.Hospital;
import com.histsys.data.repository.HospitalRepository;
import com.histsys.web.http.ResponseBody;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalCreateController {
    @Resource
    private HospitalRepository hospitalRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Payload payload) {
        Hospital hospital = toModel(payload);
        Hospital hospitalSaved = hospitalRepository.save(hospital);
        return ResponseBody.status(201).body(hospitalSaved).toResponseEntity();
    }

    private Hospital toModel(Payload payload) {
        Hospital hospital = new Hospital();
        hospital.setId(payload.getId());
        hospital.setHospitalType(payload.getHospitalType());
        hospital.setDepartmentName(payload.getDepartmentName());
        hospital.setAuthorizedBeds(payload.getAuthorizedBeds());
        hospital.setSubInstName(payload.getSubInstName());
        hospital.setCreatedAt(new Date());
        hospital.setUpdatedAt(new Date());
        return hospital;
    }

    @Data
    static class Payload {
        private String id;
        private Hospital.HospitalType hospitalType;
        private String departmentName;
        private Integer authorizedBeds;
        private String subInstName; // 分支机构名称，可选
    }
}
