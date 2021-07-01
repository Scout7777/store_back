package com.histsys.web.controller;

import com.histsys.data.repository.UserRepository;
import com.histsys.web.http.ResponseBody;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDeleteController {
    @Resource
    private UserRepository userRepository;

    @DeleteMapping("/batch")
    public ResponseEntity deleteBatch(@RequestBody Payload payload) {
        userRepository.deleteAllByIdIn(payload.getIds());
        return ResponseBody.status(200).message("批量删除成功").toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseBody.status(200).message("删除成功").toResponseEntity();
    }

    @Data
    static class Payload {
        private List<Long> ids;
    }
}
