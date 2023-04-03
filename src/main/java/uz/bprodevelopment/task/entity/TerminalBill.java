package uz.bprodevelopment.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.bprodevelopment.task.entity.base.BaseAuditEntity;
import uz.bprodevelopment.task.entity.base.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "terminal_bills")
@NoArgsConstructor
@AllArgsConstructor
public class TerminalBill extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long time;
    private Integer shiftId;
    private String originalMsg;
    private String responseMsg;

    private String stateCode;
    private Byte reversable;
    private String terminalId;
    private String merchantId;
    private Long orgId;
    private Long localId;

    private String type;
    private String rrn;
    private Long amount;
    private String card;

}
