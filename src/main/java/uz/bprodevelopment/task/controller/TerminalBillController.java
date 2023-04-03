package uz.bprodevelopment.task.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bprodevelopment.task.dto.TerminalBillDto;
import uz.bprodevelopment.task.service.TerminalBillService;

import static uz.bprodevelopment.task.config.Urls.TERMINAL_BILL_URL;


@RestController
@RequiredArgsConstructor
public class TerminalBillController {

    private final TerminalBillService service;

    @GetMapping(TERMINAL_BILL_URL + "/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok().body(service.getOne(id));
    }


    @GetMapping(TERMINAL_BILL_URL)
    public ResponseEntity<?> getList(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "rowsPerPage") Integer size,
            @RequestParam(name = "merchantId", required = false) Long merchantId,
            @RequestParam(name = "orgId", required = false) Long orgId,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok().body(service.getList(page, size, merchantId, orgId, sort));
    }

    @GetMapping(TERMINAL_BILL_URL + "/all")
    public ResponseEntity<?> getListAll(
            @RequestParam(name = "merchantId", required = false) Long merchantId,
            @RequestParam(name = "orgId", required = false) Long orgId,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok().body(service.getListAll(merchantId, orgId, sort));
    }


    @PostMapping(TERMINAL_BILL_URL)
    public ResponseEntity<?> save(@RequestBody TerminalBillDto item) {
        return ResponseEntity.ok().body(service.save(item));
    }


    @PutMapping(TERMINAL_BILL_URL)
    public ResponseEntity<?> update(@RequestBody TerminalBillDto item) {
        return ResponseEntity.ok().body(service.save(item));
    }

    @DeleteMapping(TERMINAL_BILL_URL + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

