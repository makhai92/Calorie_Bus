package kr.co.coloriebus.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFile {
	private int fileNo;
	private String filename;
	private String filepath;
	private int productNo;
}
