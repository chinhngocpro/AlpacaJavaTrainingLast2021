package vn.alpaca.userservice.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.alpaca.userservice.entity.es.UserES;

import java.util.Optional;

public interface UserESRepository extends ElasticsearchRepository<UserES, Integer> {

  Optional<UserES> findByUsername(String username);
}
