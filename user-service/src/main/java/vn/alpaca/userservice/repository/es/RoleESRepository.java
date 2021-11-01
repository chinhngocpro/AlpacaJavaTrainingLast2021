package vn.alpaca.userservice.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.alpaca.userservice.entity.es.RoleES;

import java.util.Optional;

public interface RoleESRepository extends ElasticsearchRepository<RoleES, Integer> {

  Optional<RoleES> findByName(String name);
}
