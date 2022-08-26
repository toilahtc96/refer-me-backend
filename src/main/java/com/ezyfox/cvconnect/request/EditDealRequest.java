package com.ezyfox.cvconnect.request;

import com.ezyfox.cvconnect.constant.EntityStatus;
import lombok.Data;

@Data
public class EditDealRequest {
    private long dealId;
    private long agencyId;
    private long candidateId;
    private EntityStatus status;
    private long processId;
}