package vn.alpaca.alpacajavatraininglast2021.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestParamUtil {

    public Sort getSort(Optional<String> sortBy) {
        Sort sort = Sort.unsorted();

        if (sortBy.isPresent()) {
            String[] values = sortBy.get().split("\\.");
            String criteria = values[0];
            String direction =
                    values.length > 1 ? values[1].toLowerCase() : "asc";

            sort = Sort.by(
                    direction.equals("desc") ?
                            Sort.Direction.DESC :
                            Sort.Direction.ASC,
                    criteria
            );
        }

        return sort;
    }

    public Pageable getPageable(
            Optional<Integer> pageNumber,
            Optional<Integer> pageSize,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        return pageable;
    }
}
