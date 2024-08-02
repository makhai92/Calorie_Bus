package kr.co.coloriebus.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.coloriebus.board.model.dao.BoardDao;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
}
