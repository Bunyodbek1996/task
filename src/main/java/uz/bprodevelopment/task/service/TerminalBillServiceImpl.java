package uz.bprodevelopment.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.bprodevelopment.task.dto.TerminalBillDto;
import uz.bprodevelopment.task.entity.TerminalBill;
import uz.bprodevelopment.task.entity.base.User;
import uz.bprodevelopment.task.mapper.TerminalBillMapper;
import uz.bprodevelopment.task.repo.TerminalBillRepo;
import uz.bprodevelopment.task.spec.BaseSpec;
import uz.bprodevelopment.task.spec.SearchCriteria;
import uz.bprodevelopment.task.util.CustomPage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TerminalBillServiceImpl implements TerminalBillService {

    private final TerminalBillRepo repo;


    @Override
    public TerminalBillDto getOne(Long id) {
        TerminalBill item = repo.getReferenceById(id);
        return TerminalBillMapper.toDto(item);
    }

    @Override
    public List<TerminalBillDto> getListAll(Long merchantId, Long orgId, String sort) {

        BaseSpec<TerminalBill> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", 0));
        Specification<TerminalBill> spec = Specification.where(spec1);

        if (merchantId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("merchantId", ":", merchantId)));

        if (orgId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("orgId", ":", orgId)));

        if (sort == null) sort = "id";

        List<TerminalBill> items = repo.findAll(spec, Sort.by(sort).descending());

        return TerminalBillMapper.toDtoList(items);

    }

    @Override
    public CustomPage<TerminalBillDto> getList(Integer page, Integer size, Long merchantId, Long orgId, String sort) {

        BaseSpec<TerminalBill> spec1 = new BaseSpec<>(new SearchCriteria("id", ">", 0));
        Specification<TerminalBill> spec = Specification.where(spec1);

        if (merchantId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("merchantId", ":", merchantId)));

        if (orgId != null)
            spec = spec.and(new BaseSpec<>(new SearchCriteria("orgId", ":", orgId)));

        if (sort == null) sort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<TerminalBill> items = repo.findAll(spec, pageable);

        return new CustomPage<>(
                TerminalBillMapper.toDtoList(items.getContent()),
                items.isFirst(),
                items.isLast(),
                items.getNumber(),
                items.getNumber(),
                items.getNumberOfElements()
        );
    }

    @Override
    public TerminalBillDto save(TerminalBillDto item) {
        TerminalBill terminalBill = repo.save(TerminalBillMapper.toEntity(item));
        return TerminalBillMapper.toDto(terminalBill);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
