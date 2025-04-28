package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummaryMinDTO> searchBySummarySales(LocalDate minDate, LocalDate maxDate) {
			return repository.searchBySummarySales(minDate, maxDate);
	}

	public Page<ReportDTO> searchByReport(LocalDate date, Pageable pageable){
		return repository.searchSalesReport(date, pageable);
	}

	public Page<ReportDTO> searchReportBySeller(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		return repository.searchSaleSellerName(minDate, maxDate, name, pageable);
	}
}
