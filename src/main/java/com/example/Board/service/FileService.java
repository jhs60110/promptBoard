package com.example.Board.service;

import com.example.Board.controller.UserController;
import com.example.Board.entity.Board;
import com.example.Board.entity.BoardFile;
import com.example.Board.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FileService {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${file.path}")
    private String filePath;
    @Autowired
    private FileRepository fileRepository;

    public void callFileRepository(List<BoardFile> fileList, Board board){

        if(fileList.isEmpty()){
            logger.info("파일리스트 비었다");
            return;
        }
        for(BoardFile boardFile : fileList){
            boardFile.setBoard(board);
            fileRepository.save(boardFile);
        }
    }
    }


//    public Long saveFile(MultipartFile files, Long boardId) throws IOException {
//        if (files.isEmpty()) {
//            return null;
//        }
//
//        // 원래 파일 이름 추출
//        String originalFileName = files.getOriginalFilename();
//
//        // 파일 이름으로 쓸 uuid 생성
//        String uuid = UUID.randomUUID().toString();
//
//        // 확장자 추출(ex : .png)
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//
//        // uuid와 확장자 결합
//        String savedFileName = uuid + extension;
//
//        // 파일을 불러올 때 사용할 파일 경로
//        String savedFilePath = fileDir + savedFileName;
//
//        Board fileBoard = boardRepository.getReferenceById(boardId);
//
//// 파일 엔티티 생성
//        Files file = Files.builder()
//                .originalFileName(originalFileName)
//                .savedFileName(savedFileName)
//                .savedFilePath(savedFilePath)
//                .board(fileBoard)
//                .build();
//
//        // 실제로 로컬에 uuid를 파일명으로 저장
//        files.transferTo(new File(savedFilePath));
//
//        // 데이터베이스에 파일 정보 저장
//        Files savedFile = fileRepository.save(file);
//
//        return savedFile.getId();
//    }


