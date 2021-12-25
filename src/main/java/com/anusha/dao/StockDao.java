package com.anusha.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anusha.core.Stock;

public interface StockDao extends MongoRepository<Stock, String> {
	
	@Query("{companyCode:'?0'}")
	List<Stock> findByCompanyCode(String companyCode);
	
	
	@Query("{companyCode:'?0',stockDate: { $gte: ?1, $lte: ?2 }}")
	List<Stock> fetchStock(String companyCode, Date startDate, Date endDate);
	
	
}
