package uz.bprodevelopment.task.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.bprodevelopment.task.dto.TerminalBillDto;
import uz.bprodevelopment.task.entity.TerminalBill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class TerminalBillMapper {


    public static TerminalBill toEntity(TerminalBillDto dto){

        TerminalBill terminalBill = new TerminalBill();

        terminalBill.setId(dto.getId());
        terminalBill.setTime(dto.getTime());
        terminalBill.setShiftId(dto.getShiftId());

        ObjectMapper mapper = new ObjectMapper();
        String originalMessage;
        try {
            originalMessage = mapper.writeValueAsString(dto.getOriginalMsg());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        terminalBill.setOriginalMsg(originalMessage);

        String responseMessage;
        try {
            responseMessage = mapper.writeValueAsString(dto.getResponseMsg());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        terminalBill.setResponseMsg(responseMessage);

        terminalBill.setStateCode(dto.getStateCode());
        terminalBill.setReversable((byte) (dto.getReversable() ? 1 : 0));
        terminalBill.setTerminalId(dto.getTerminalId());
        terminalBill.setMerchantId(dto.getMerchantId());
        terminalBill.setOrgId(dto.getOrgId());
        terminalBill.setLocalId(dto.getLocalId());
        terminalBill.setType(dto.getType());
        terminalBill.setRrn(dto.getRrn());
        terminalBill.setAmount(dto.getAmount());
        terminalBill.setCard(dto.getCard());

        return terminalBill;
    }

    public static TerminalBillDto toDto(TerminalBill item){

        TerminalBillDto dto = new TerminalBillDto();
        ObjectMapper mapper = new ObjectMapper();

        dto.setId(item.getId());
        dto.setTime(item.getTime());
        dto.setShiftId(item.getShiftId());

        Map originalMessageMap;
        try {
            originalMessageMap = mapper.readValue(item.getOriginalMsg(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        dto.setOriginalMsg(originalMessageMap);

        Map responseMessageMap;
        try {
            responseMessageMap = mapper.readValue(item.getResponseMsg(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        dto.setResponseMsg(responseMessageMap);


        dto.setStateCode(item.getStateCode());
        dto.setReversable(item.getReversable() == 1);
        dto.setTerminalId(item.getTerminalId());
        dto.setMerchantId(item.getMerchantId());
        dto.setOrgId(item.getOrgId());
        dto.setLocalId(item.getLocalId());
        dto.setType(item.getType());
        dto.setRrn(item.getRrn());
        dto.setAmount(item.getAmount());
        dto.setCard(item.getCard());

        return dto;
    }

    public static List<TerminalBillDto> toDtoList(List<TerminalBill> items) {
        List<TerminalBillDto> dtoList = new ArrayList<>();
        items.forEach(item -> {
            dtoList.add(toDto(item));
        });
        return dtoList;
    }

}
