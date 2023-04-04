package com.myblog2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadResponse {

    private List<LeadDto> content;

    private int pageNo;

    private int pageSize;

    private long totalElement;

    private long totalPage;

    private boolean last;
}
