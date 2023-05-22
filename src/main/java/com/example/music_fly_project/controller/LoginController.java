package com.example.music_fly_project.controller;

import com.example.music_fly_project.server.LoginServer;
import com.example.music_fly_project.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private LoginServer loginServer;
    @PostMapping("/save")
    public long save(@RequestBody LoginVo loginVo){
        return loginServer.save(loginVo);
    }
    @DeleteMapping("/delete")
    public long delete(@RequestBody LoginVo loginVo){
        return loginServer.delete(loginVo.getId());
    }
    @PutMapping("/update")
    public long update(@RequestBody LoginVo loginVo){
        long userId=loginServer.update(loginVo);
        return userId;
    }
}
