package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.exception.FileFormatException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Tag(name = "文件上传接口")
public class FilesUploadController {

    @Value("${application.upload-path}")
    private String fileDirectory;

    @Operation(summary = "头像上传")
    @PostMapping("/upload/{type}")
    public Result<String> upload(@PathVariable("type") String type,
                                 @RequestParam("file") MultipartFile file) throws Exception {
        System.out.println("请求图片");
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (!tail.equals(".jpg") && !tail.equals(".gif") && !tail.equals(".png") && !tail.equals(".eg") &&
                !tail.equals(".jpeg") && !tail.equals(".bmp") && !tail.equals(".mp4")) {
            throw new FileFormatException("请传入正确格式文件");
        }

        String header = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = header + tail;
        String targetFileLocation = fileDirectory + File.separator + type;
        File filel = new File(targetFileLocation);
        if (!filel.exists()) {
            filel.mkdirs();
        }

        String targetFileName = targetFileLocation + File.separator + newFileName;
        File targetFile = new File(targetFileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }

        file.transferTo(targetFile);
        return new Result<>(true, StatusCode.OK, "上传成功", type + "/" + newFileName);
    }

    @Operation(summary = "资料上传")
    @PostMapping("/uploadInfo/{type}")
    public Result<String> uploadInfo(@PathVariable("type") String type,
                                     @RequestParam("file") MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (!tail.equals(".jpg") && !tail.equals(".gif") && !tail.equals(".png") && !tail.equals(".eg") &&
                !tail.equals(".jpeg") && !tail.equals(".bmp") && !tail.equals(".mp4")) {
            throw new FileFormatException("请传入正确格式文件");
        }

        Calendar calendar = Calendar.getInstance();
        String header = calendar.getTime().toString();
        String newFileName = header + tail;
        String targetFileLocation = fileDirectory + File.separator + type;
        File filel = new File(targetFileLocation);
        if (!filel.exists()) {
            filel.mkdirs();
        }

        String targetFileName = targetFileLocation + File.separator + newFileName;
        File targetFile = new File(targetFileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }

        file.transferTo(targetFile);
        return new Result<>(true, StatusCode.OK, "上传成功", type + "/" + newFileName);
    }
}
