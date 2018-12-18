package ga.eatup.partner.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import ga.eatup.partner.domain.SalesVO;
import ga.eatup.partner.service.SalesService;
import lombok.Setter;
import lombok.extern.java.Log;


@RequestMapping("/partner/*")
@Controller
@Log
public class SalesController {

	private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

	@Setter(onMethod_=@Autowired)
	private SalesService service;
	
	@GetMapping(value = "sales")
	public String Sales(Locale locale, Model model) {
		log.info("sales page....");
		return "/partner/sales";
	}
	
	@RequestMapping(value = "salesList", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String dailyList(Locale locale, Model model) {
		Gson gson = new Gson();
		List<SalesVO> list = service.getDailySales();
		
		return gson.toJson(list);
	}
	
	@RequestMapping(value = "weeklyList", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String weeklyList(Locale locale, Model model) {
		Gson gson = new Gson();
		List<SalesVO> list = service.getWeeklySales();
		
		return gson.toJson(list);
	}
	
	@RequestMapping(value = "monthlyList", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String monthlyList(Locale locale, Model model) {
		Gson gson = new Gson();
		List<SalesVO> list = service.getMonthlySales();
		
		return gson.toJson(list);
	}
	
	@GetMapping(value = "dailydata/menu/{month}", produces="application/json")
	public ResponseEntity<List<SalesVO>> getdailydata(@PathVariable("month") int month){
		return new ResponseEntity<>(service.getDailytableData(month),HttpStatus.OK);
	}
	
	@GetMapping(value = "dailydata/date/{month}", produces="application/json")
	public ResponseEntity<List<SalesVO>> getdailydata_date(@PathVariable("month") int month){
		return new ResponseEntity<>(service.getDailytableData_date(month),HttpStatus.OK);
	}
	
	@GetMapping(value = "weeklydata/menu/{month}", produces="application/json")
	public ResponseEntity<List<SalesVO>> getweeklydata(@PathVariable("month") int month){
		return new ResponseEntity<>(service.getWeeklytableData(month),HttpStatus.OK);
	}
	
	@GetMapping(value = "weeklydata/date/{month}", produces="application/json")
	public ResponseEntity<List<SalesVO>> getweeklydata_date(@PathVariable("month") int month){
		return new ResponseEntity<>(service.getWeeklytableData_date(month),HttpStatus.OK);
	}
	
	
//	@RequestMapping(value = "dailydata", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
//	public @ResponseBody String dailydata(Locale locale, Model model) {
//		
//		Gson gson = new Gson();
//		List<SalesVO> list = service.getDailytableData();
//		
//		return gson.toJson(list);
//	}	

//	@RequestMapping(value = "dailydata_date", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
//	public @ResponseBody String dailydata_date(Locale locale, Model model) {
//		Gson gson = new Gson();
//		List<SalesVO> list = service.getDailytableData_date();
//		
//		return gson.toJson(list);
//	}
//	
//	@RequestMapping(value = "weeklydata", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
//	public @ResponseBody String weeklydata(Locale locale, Model model) {
//		Gson gson = new Gson();
//		List<SalesVO> list = service.getWeeklytableData();
//		
//		return gson.toJson(list);
//	}
//	
//	@RequestMapping(value = "weeklydata_date", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
//	public @ResponseBody String weeklydata_date(Locale locale, Model model) {
//		Gson gson = new Gson();
//		List<SalesVO> list = service.getWeeklytableData_date();
//		
//		return gson.toJson(list);
//	}
	
	@RequestMapping(value = "monthlydata/menu", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String monthlydata(Locale locale, Model model) {
		Gson gson = new Gson();
		List<SalesVO> list = service.getMonthlytableData();
		
		return gson.toJson(list);
	}
	
	@RequestMapping(value = "monthlydata/date", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String monthlydata_date(Locale locale, Model model) {
		Gson gson = new Gson();
		List<SalesVO> list = service.getMonthlytableData_date();
		
		return gson.toJson(list);
	}

	
}