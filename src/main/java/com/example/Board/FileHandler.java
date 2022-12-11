package com.example.Board;


import com.example.Board.entity.BoardFile;
import com.example.Board.entity.User;
import com.example.Board.repository.UserRepository;
import com.example.Board.service.FileService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileHandler {
    @Value("${file.path}")
    private String uploadPath;
    private final FileService fileService;
    private final UserRepository userRepository;

    public List<BoardFile> UserFileUpload(List<MultipartFile> boardFile, String userId) throws IOException {

        // 반환할 파일 리스트
        List<BoardFile> fileList = new ArrayList<>();
        User user = userRepository.findByUserId(userId);

        // 전달되어 온 파일이 존재할 경우
        if (!CollectionUtils.isEmpty(boardFile)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            for (MultipartFile multipartFile : boardFile) {

                String originalName = multipartFile.getOriginalFilename();
                String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

                String uuid = UUID.randomUUID().toString();

                String savefileName = uploadPath + File.separator + uuid + "_" + fileName;

                Path savePath = Paths.get(savefileName);


//                try {
//                    multipartFile.transferTo(savePath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


//                String path = "/Users/seohaesu/Downloads" + "/" + userId + "/";
//
//                String extension;
//                String contentType = multipartFile.getContentType();
//
//                if (ObjectUtils.isEmpty(contentType)) {
//                    break;
//                }
//                // 원래 파일 이름 추출
//                String originalFileName = multipartFile.getOriginalFilename();
//
//                // 확장자 추출(ex : .png)
//                extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
//
//                String new_file_name = current_date + "_" + System.nanoTime() + "." + extension;

                File file = new File(uploadPath + savefileName); //파일 저장 경로 확인, 없으면 만든다.
                if (!file.exists()) {
                    file.mkdirs();
                }
                BoardFile userFile = new BoardFile();


                userFile.setUser(user);
                userFile.setFilePath(uploadPath + savefileName);
                userFile.setFileSize(multipartFile.getSize());
                userFile.setOriginalFileName(multipartFile.getOriginalFilename());
                userFile.setSavedFileName(savefileName);

                fileList.add(userFile);

                multipartFile.transferTo(savePath);

                file.setWritable(true);
                file.setReadable(true);
            }
            return fileList;
        }

        return null;

    }


};

