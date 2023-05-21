package com.example.music_fly_project.server;

import com.example.music_fly_project.entity.AlbumsEntity;
import com.example.music_fly_project.entity.UserEntity;
import com.example.music_fly_project.repository.UserRepository;
import com.example.music_fly_project.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServer {
    @Autowired
    private UserRepository userRepository;
    public long save(UserVO userVO){
        UserEntity bean= new UserEntity();
        BeanUtils.copyProperties(userVO,bean);
        bean=userRepository.save(bean);
        long userId=bean.getId();
        return userId;

    }
    public long delete(long id){
        userRepository.deleteById(id);
        return id;
    }
    public long update(UserVO userVO){
        UserEntity bean;
        try{
            bean=getById(userVO.getId());
        }catch (Exception e){
            e.getMessage();
            return -1;
        }
        BeanUtils.copyProperties(userVO,bean);
        long userId=userRepository.save(bean).getId();
        return userId;
    }
    private UserEntity getById(long id){
        UserEntity user=userRepository.getById(id);
        return user;
    }
}
