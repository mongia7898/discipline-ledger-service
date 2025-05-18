package com.mongia.discpline.ledger.DTOs.requests;

import lombok.Data;

@Data
public class PageableRequest {


     private Integer pageNo=1;
     private Integer size=10;
     private String sortBy="id";
     // Can be -1-> descending or 1-> ascending;
     private Boolean ascending=Boolean.FALSE;
}

