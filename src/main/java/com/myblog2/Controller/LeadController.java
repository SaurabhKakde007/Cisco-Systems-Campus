package com.myblog2.Controller;

import com.myblog2.payload.LeadDto;
import com.myblog2.payload.LeadResponse;
import com.myblog2.service.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    //http://localhost:8080/api/leads

    private LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {
        LeadDto dto = leadService.createLead(leadDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/leads?pageNo=0&pageSize=5&sortBy=firstName&sortDir=asc
    @GetMapping
    public LeadResponse getAllLeads(@RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
                                    @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
                                    @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir

    ) {

        return leadService.getAllLeads(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getLeadById(@PathVariable("id") long id) {
        LeadDto dto = leadService.getLeadById(id);
        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> UpdateLead(@RequestBody LeadDto leadDto, @PathVariable("id") long id) {

        LeadDto lead = leadService.UpdateLead(leadDto, id);

        return new ResponseEntity<>(lead, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeadById(@PathVariable("id") long id) {
        leadService.deleteLeadById(id);

        return new ResponseEntity<>("lead entity deleted!!", HttpStatus.OK);


    }
}



