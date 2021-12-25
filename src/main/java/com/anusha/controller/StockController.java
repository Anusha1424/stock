package com.anusha.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anusha.core.Stock;
import com.anusha.core.StockDTO;
import com.anusha.service.StockService;

@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockController {
	
	Logger logger = LoggerFactory.getLogger(StockController.class); 

	
	@Autowired
	private StockService stockService;
	
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<String> addStockPrice(@RequestBody Stock stock) {
		try {
			
			stockService.addStock(stock);
		}catch(Exception e) {
			logger.error("Class StockController: Failed to add stock Price");
		}
		return new ResponseEntity<>("Stock added", HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/get/{companyCode}/{startDate}/{endDate}",method = RequestMethod.GET)
	public ResponseEntity<StockDTO> fetchStockPriceList(@PathVariable(name = "companyCode") String companyCode,@PathVariable(name = "startDate") String startDate,@PathVariable(name = "endDate") String endDate) throws Exception {
		StockDTO stockDTO = null;
		try {
			stockDTO = stockService.fetchStockList(companyCode, new SimpleDateFormat("yyyy-MM-dd").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate+" 23:59"));
		} catch(Exception e) {
			logger.error("Class StockPriceController: Failed to fetchStockPrice");
			e.printStackTrace();
		}
		return new ResponseEntity<>(stockDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllByCompanyCode/{companyCode}",method = RequestMethod.GET)
	public ResponseEntity<StockDTO> fetchAllStockPriceList(@PathVariable(name = "companyCode") String companyCode) throws Exception {
		StockDTO stockDTO = null;
		try {
			stockDTO = stockService.fetchAllStockPriceList(companyCode);
		} catch(Exception e) {
			logger.error("Class StockPriceController: Failed to fetchStockPrice");
		}
		return new ResponseEntity<>(stockDTO, HttpStatus.OK);
	}
}
