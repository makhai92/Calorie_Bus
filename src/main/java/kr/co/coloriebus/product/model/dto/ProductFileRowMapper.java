package kr.co.coloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductFileRowMapper implements RowMapper<ProductFile>{

	@Override
	public ProductFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductFile productFile = new ProductFile();
		productFile.setFilename(rs.getString("filename"));
		productFile.setFileNo(rs.getInt("file_no"));
		productFile.setFilepath(rs.getString("filepath"));
		productFile.setProductNo(rs.getInt("product_no"));
		return productFile;
	}
	
}
