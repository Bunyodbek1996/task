package uz.bprodevelopment.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.bprodevelopment.task.entity.TerminalBill;
import uz.bprodevelopment.task.entity.base.User;

public interface TerminalBillRepo extends JpaRepository<TerminalBill, Long>,
        JpaSpecificationExecutor<TerminalBill> {}
