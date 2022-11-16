package com.iaeep.controller;

import com.iaeep.service.PostsService;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author DELL
 * @version 1.0
 * @Classname ArticleImgUpload
 * @Description TODO
 * @CreateDate 2022/10/20 21:16
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/20 21:16
 */
@RestController
@RequestMapping("/article")
public class ArticleImgUpload {
    @Autowired
    private  PostsService postsService;

    @PostMapping("/uploadImg")
    public HashMap<String,Object> fileUpload(@RequestParam("editormd-image-file") MultipartFile file, String guid) throws IOException {
        HashMap<String, Object> hashMap = new LinkedHashMap<>();

        // 获得后缀类型
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
        // 获得文件上传路径
        String path = "iaeep/image/articleImg";
        // 将路径传给 File 对象
        File realPath = new File(path);
        // 判断路径上的文件夹是否存在，不存在就创建
        if (!realPath.exists()){
            realPath.mkdir();
        }
        // 设置上传的文件名字
        String filename = guid + type;
        //通过CommonsMultipartFile的方法直接写文件
        file.transferTo(new File(realPath +"/"+ filename));

        // 返回上传路径
        hashMap.put("url","../../image/articleImg" + filename);
        // 返回是否成功
        hashMap.put("success", 1);
        // 返回信息提示
        hashMap.put("message", "upload success!");
        return hashMap;
    }
}
