package com.mongia.discpline.ledger.DTOs.requests;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PageableRequest {


     Integer pageNo=1;
     Integer size=10;
     String sortBy;
     // Can be -1-> descending or 1-> ascending;
     int sortOrder;
}

