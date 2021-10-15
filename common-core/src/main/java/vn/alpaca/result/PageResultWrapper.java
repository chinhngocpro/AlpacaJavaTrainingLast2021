package vn.alpaca.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PageResultWrapper<S> {
    List<S> content;

    Long totalPages;

    Long totalElements;

    Boolean last;

    Long size;

    Long number;

    Long numberOfElements;

    Boolean first;

    Boolean empty;
}
