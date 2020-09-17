package com.zongyuheng.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class ImageUpload {

@RequestMapping("/file/upload")
@ResponseBody
public JSONObject imageUpLoad(HttpServletResponse response, @RequestParam(value = "editormd-image-file",required = true)MultipartFile file, HttpServletRequest request) throws IOException {
    String path=System.getProperty("user.dir")+"\\upload\\";

    //按照月份进行分类
    Calendar instance=Calendar.getInstance();
    String month=(instance.get(Calendar.MONTH)+1)+"月";
    path=path+month;

    File realPath=new File(path);
    if (!realPath.exists()){
        realPath.mkdirs();

    }
    //上传文件地址


    //解决文件名字问题：我们使用uuid
    String filename="ks-"+ UUID.randomUUID().toString().replaceAll("-","");

    //进行写文件
    file.transferTo(new File(realPath+"/"+ filename+".jpg"));

    //给editormd 进行回调
    JSONObject res=new JSONObject();
    res.put("url","/upload/"+month+"/"+filename+".jpg");
    res.put("success",1);
    res.put("message","upload success!");
    response.addHeader("X-Frame-Options","SAMEORIGIN");

    return  res;
}



}
