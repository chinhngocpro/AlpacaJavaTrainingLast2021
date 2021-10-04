package vn.alpaca.alpacajavatraininglast2021.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
