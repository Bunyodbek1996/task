package uz.bprodevelopment.task.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {
    List<T> content;
    Boolean isFirst;
    Boolean isLast;
    Integer currentPage;
    Integer totalPages;
    Integer totalElements;

}
