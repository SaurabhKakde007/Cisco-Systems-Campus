package com.myblog2.service;

import com.myblog2.entity.Lead;
import com.myblog2.payload.LeadDto;
import com.myblog2.payload.LeadResponse;

import java.util.List;

public interface LeadService {

 LeadDto createLead(LeadDto leadDto);

    LeadResponse getAllLeads(int pageNo, int pageSize, String sortBy, String sortDir);

 LeadDto getLeadById(long id);

 LeadDto UpdateLead(LeadDto leadDto, long id);

    void deleteLeadById(long id);
}
