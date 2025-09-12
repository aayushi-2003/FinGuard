package com.oracle.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.model.Debt;
import com.oracle.model.PaymentSchedule;
import com.oracle.service.DebtService;
import com.oracle.model.DebtStatus;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/debt")
public class DebtAPI {
	@Autowired
	private DebtService debtService;
	
	@GetMapping("test")
	public String test() {
		return "Text";
	}
	
	@PostMapping
	public Debt addNewProduct(@RequestBody Debt p) {
		return debtService.addDebtService(p);
	}
	
	@GetMapping("/{id}")
	public Debt searchById(@PathVariable("id") int id) {
		return debtService.searchDebtById(id);
	}
	
	@PutMapping("/{id}")
	public Debt updateProduct(@RequestBody Debt p, @PathVariable("id") int id) {
		return debtService.updateDebtService(p, id);
	}
	
	 
	
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
		debtService.deleteDebt(id);
	}
	
	@GetMapping("/all")
	public List<Debt> getAllProducts(){
		return debtService.allDebtService();
	}
	
	@GetMapping("searchbyschedule")
	public List<Debt> searchbyPaymentSchedule(@RequestParam("name") PaymentSchedule name){
		return debtService.searchByPaymentSchedule(name);
	}
	
	@GetMapping("searchbystatus")
	public List<Debt> searchbyDebtStatus(@RequestParam("status") DebtStatus status){
		return debtService.searchByDebtStatus(status);
	}


}




