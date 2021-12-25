package com.anusha.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.anusha.core.Stock;
import com.anusha.core.StockDTO;
import com.anusha.dao.StockDao;

@Service
public class StockService {
	
	@Autowired
	private StockDao stockDao;
	
	public List<Stock> getStocksByCompanyCode(String companyCode) throws Exception{
		List<Stock> stockList = new ArrayList<Stock>();
		try {
			stockList = stockDao.findByCompanyCode(companyCode);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return stockList;
	}
	
	public void addStock(Stock stock) throws Exception{
		stock.setStockDate(new Date());
		stockDao.save(stock);
	}
	
	public StockDTO fetchStockList(String companyCode, Date startDate, Date endDate) throws Exception{
		StockDTO stockPriceDTO = new StockDTO();

		try {
			
		List<Stock> stockPriceList = stockDao.fetchStock(companyCode, startDate, endDate);

		if(!CollectionUtils.isEmpty(stockPriceList)) {
			DoubleSummaryStatistics stats = stockPriceList.stream()
	                .mapToDouble((x) -> x.getPrice()!=null ? x.getPrice() : 0)
	                .summaryStatistics();
			stockPriceDTO.setAverage(stats.getAverage());
			stockPriceDTO.setMin(stats.getMin());
			stockPriceDTO.setMax(stats.getMax());
		}
		stockPriceDTO.setStockList(stockPriceList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stockPriceDTO;
	}
	
	public StockDTO fetchAllStockPriceList(String companyCode) throws Exception{
		List<Stock> stockList = new ArrayList<Stock>();
		StockDTO stockPriceDTO = new StockDTO();
		try {
			stockList = stockDao.findByCompanyCode(companyCode);
			
			if(!CollectionUtils.isEmpty(stockList)) {
				DoubleSummaryStatistics stats = stockList.stream()
		                .mapToDouble((x) ->  x.getPrice()!=null ? x.getPrice() : 0)
		                .summaryStatistics();
				stockPriceDTO.setAverage(stats.getAverage());
				stockPriceDTO.setMin(stats.getMin());
				stockPriceDTO.setMax(stats.getMax());
			}
			stockPriceDTO.setStockList(stockList);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return stockPriceDTO;
	}
	
	

	
	
}
