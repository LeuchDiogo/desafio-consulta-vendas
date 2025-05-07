package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportDTO>> getReport(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr,
			@RequestParam(value = "name", required = false) String nameStr, Pageable pageable)
	{
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate = (maxDateStr == null) ? today : LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null) ? maxDate.minusYears(1L) : LocalDate.parse(minDateStr);

		String name = (nameStr == null) ? null : nameStr;
		Page<ReportDTO> dto = service.searchReportBySeller(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}
	@GetMapping("/summary")
	public ResponseEntity<List<SummaryMinDTO>> getSummary(
			@RequestParam(required = false) String minDate,
			@RequestParam(required = false) String maxDate) {

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = (maxDate == null) ? today : LocalDate.parse(maxDate);
		LocalDate min = (minDate == null) ? max.minusYears(1L) : LocalDate.parse(minDate);

		List<SummaryMinDTO> dto = service.searchBySummarySales(min, max);
		return ResponseEntity.ok(dto);
	}


}



