package com.myblog2.service.impl;

import com.myblog2.entity.Lead;
import com.myblog2.exception.ResponseNotFoundException;
import com.myblog2.payload.LeadDto;
import com.myblog2.payload.LeadResponse;
import com.myblog2.repository.LeadRepository;
import com.myblog2.service.LeadService;
import org.hibernate.sql.Delete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {



    private LeadRepository leadRepository;

    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public LeadDto createLead(LeadDto leadDto) {
        //Dto to entity
       Lead lead= mapToEntity(leadDto);
           Lead newLead= leadRepository.save(lead);

           //Entity to Dto
           LeadDto newLeadDto=mapToDto(newLead);

        return newLeadDto;
    }

    @Override
    public LeadResponse getAllLeads(int pageNo, int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Page<Lead> leads = leadRepository.findAll(pageable);

        List<Lead> content = leads.getContent();

        List<LeadDto> leadDtos = content.stream().map(lead -> mapToDto(lead)).collect(Collectors.toList());

        LeadResponse leadResponse=new LeadResponse();

        leadResponse.setContent(leadDtos);
        leadResponse.setPageNo(leads.getTotalPages());
        leadResponse.setPageSize(leads.getSize());
        leadResponse.setTotalElement(leads.getTotalElements());
        leadResponse.setTotalPage(leads.getTotalPages());
        leadResponse.setLast(leads.isLast());

        return leadResponse;


    }

    @Override
    public LeadDto getLeadById(long id) {
        Lead leads = leadRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("lead", "id", id));
        return mapToDto(leads);
    }

    @Override
    public LeadDto UpdateLead(LeadDto leadDto, long id) {
        Lead lead = leadRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("lead", "id", id));

        lead.setFirstName(leadDto.getFirstName());
        lead.setLastName(leadDto.getLastName());
        lead.setEmail(leadDto.getEmail());
        lead.setMobile(lead.getMobile());

        Lead updateLead = leadRepository.save(lead);

        return mapToDto(updateLead);

    }

    @Override
    public void deleteLeadById(long id) {

        Lead lead = leadRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("lead", "id", id));

        leadRepository.delete(lead);

    }

    Lead mapToEntity(LeadDto leadDto) {
        Lead lead = new Lead();
        lead.setFirstName(leadDto.getFirstName());
        lead.setLastName(leadDto.getLastName());
        lead.setEmail(leadDto.getEmail());
        lead.setMobile(leadDto.getMobile());
        return lead;

    }
    LeadDto mapToDto(Lead lead){
        LeadDto leadDto=new LeadDto();

        leadDto.setId(lead.getId());
        leadDto.setFirstName(lead.getFirstName());
        leadDto.setLastName(lead.getLastName());
        leadDto.setEmail(lead.getEmail());
        leadDto.setMobile(lead.getMobile());
        return leadDto;


    }
}
