package kr.co.caloriebus.exercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseFile {
	private int fileNo;
	private String filename;
	private String filepath;
	private int boardNo;
}
