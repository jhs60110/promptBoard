package com.example.Board.service;

import com.example.Board.controller.UserController;
import com.example.Board.entity.BoardFile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileHandler {
    @Value("${file.path}")
    private String uploadPath;

    public List<BoardFile> UserFileUpload(List<MultipartFile> boardFiles) throws IOException {

        List<BoardFile> fileList = new ArrayList<>();
        if (boardFiles.isEmpty()) {
            return fileList;
        }

        if (!CollectionUtils.isEmpty(boardFiles)) {
            for (MultipartFile multipartFile : boardFiles) {
                String originalName = multipartFile.getOriginalFilename();
                if (originalName.equals("")) {
                    continue;
                }
                String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
                String uuid = UUID.randomUUID().toString();
                String savefileName = uploadPath + File.separator + uuid + "_" + fileName;

                Path savePath = Paths.get(savefileName);

                File file = new File(uploadPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                BoardFile boardFile = new BoardFile();
                boardFile.setFilePath(uploadPath);
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setSavedFileName(savefileName);

                fileList.add(boardFile);

                multipartFile.transferTo(savePath);

                file.setWritable(true);
                file.setReadable(true);
            }
            return fileList;
        }
        return null;
    }
}

