package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

public final class UserSpecification {

    public static
    Specification<User> hasNameContaining(String fullName) {
            return (root, query, cb) -> cb.like(root.get("fullName"), fullName);
    }
}
