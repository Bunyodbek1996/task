package uz.bprodevelopment.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@SuppressWarnings("ALL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalBillDto {

    private Long id;
    private Long time;
    private Integer shiftId;
    private Map originalMsg;
    private Map responseMsg;

    private String stateCode;
    private Boolean reversable;
    private String terminalId;
    private String merchantId;
    private Long orgId;
    private Long localId;

    private String type;
    private String rrn;
    private Long amount;
    private String card;

}
