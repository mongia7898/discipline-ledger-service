package com.mongia.discpline.ledger.CommonUtils.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LedgerResponse<T> {
    private Boolean success=Boolean.TRUE;
    private String message;
    private T data;

    public LedgerResponse(Boolean success, String message, T data){
        super();
        this.data=data;
        this.message=message;
        this.success=success;
    }

    public LedgerResponse(Boolean success, String message){
        super();
        this.success=success;
        this.message=message;
    }
}
