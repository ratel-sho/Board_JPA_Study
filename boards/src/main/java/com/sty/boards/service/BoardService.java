package com.sty.boards.service;

import com.sty.boards.entity.Board;
import com.sty.boards.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        //저장할 경로 지정
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        //파일이름에 붙일 랜덤 이름 생성
        UUID uuid = UUID.randomUUID();
        //파일 이름 생성
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 파일을 저장할 껍데기를 지정
        File saveFile = new File(projectPath,"name");

        file.transferTo(saveFile);
        //저장된 파일의 이름
        board.setFilename(fileName);
        //저장된 파일의 경로와 이름
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }


    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
