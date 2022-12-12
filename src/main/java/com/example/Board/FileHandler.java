package com.example.Board;

import com.example.Board.controller.UserController;
import com.example.Board.entity.BoardFile;
import com.example.Board.entity.User;
import com.example.Board.repository.UserRepository;
import com.example.Board.service.FileService;
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
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${file.path}")
    private String uploadPath;
    private final UserRepository userRepository;

    public List<BoardFile> UserFileUpload(List<MultipartFile> boardFiles, String userId) throws IOException {

        List<BoardFile> fileList = new ArrayList<>();
        User user = userRepository.findByUserId(userId);
        if (boardFiles.isEmpty()) {
            return fileList;
        }

        if (!CollectionUtils.isEmpty(boardFiles)) {
            for (MultipartFile multipartFile : boardFiles) {
                String originalName = multipartFile.getOriginalFilename();
                if (originalName.equals("")) {
                    continue;
                }
                logger.info("originalName :", originalName);
                String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
                logger.info("fileName :", fileName);
                String uuid = UUID.randomUUID().toString();

                String savefileName = uploadPath + File.separator + uuid + "_" + fileName;
                logger.info("savefileName :", savefileName);

                Path savePath = Paths.get(savefileName);

                File file = new File(uploadPath); //파일 저장 경로 확인, 없으면 만든다.
                if (!file.exists()) {
                    file.mkdirs();
                }
                BoardFile boardFile = new BoardFile();
                boardFile.setUser(user);
                boardFile.setFilePath(uploadPath);
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setSavedFileName(savefileName);

                fileList.add(boardFile);
                logger.info(fileList.toString());

                multipartFile.transferTo(savePath);

                file.setWritable(true);
                file.setReadable(true);
            }
            return fileList;
        }

        return null;

    }


}

