package vn.alpaca.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public final class ExtractParam {
    public static Sort getSort(Optional<String> sortBy) {
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

    public static Pageable getPageable(
            Optional<Integer> page,
            Optional<Integer> limit,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);

        if (page.isPresent()) {
            pageable = PageRequest.of(page.get(), limit.orElse(5));
        }

        return pageable;
    }
}
