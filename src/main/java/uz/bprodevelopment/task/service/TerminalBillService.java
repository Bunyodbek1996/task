package uz.bprodevelopment.task.service;


import org.springframework.data.domain.Page;
import uz.bprodevelopment.task.dto.TerminalBillDto;
import uz.bprodevelopment.task.entity.TerminalBill;
import uz.bprodevelopment.task.util.CustomPage;

import java.util.List;

public interface TerminalBillService {

    TerminalBillDto getOne(Long id);

    List<TerminalBillDto> getListAll(
            Long merchantId,
            Long orgId,
            String sort
    );

    CustomPage<TerminalBillDto> getList(
            Integer page,
            Integer size,
            Long merchantId,
            Long orgId,
            String sort
    );

    TerminalBillDto save(TerminalBillDto item);

    void delete(Long id);

}
