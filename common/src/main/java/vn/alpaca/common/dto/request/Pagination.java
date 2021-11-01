package vn.alpaca.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

@Data
@NoArgsConstructor
public class Pagination {

    @JsonProperty("page")
    private int pageNumber = 0;

    @JsonProperty("limit")
    private int pageSize = 10000;

    @JsonProperty("sort-by")
    private String sortBy;

    public Pagination(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pageable getPageAndSort() {
        Sort sort = Sort.unsorted();

        if (!ObjectUtils.isEmpty(sortBy)) {
            String[] values = sortBy.split("\\.");
            String criteria = values[0];
            String direction = values.length > 1
                    ? values[1].toLowerCase() : "asc";

            sort = Sort.by(direction.equals("desc")
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC,
                    criteria);
        }

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
