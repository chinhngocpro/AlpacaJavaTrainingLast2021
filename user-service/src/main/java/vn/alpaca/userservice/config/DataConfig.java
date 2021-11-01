package vn.alpaca.userservice.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.alpaca.common.config.SpringDataConfig;

@Configuration("springDataClient")
@EnableCaching
@EnableJpaRepositories(basePackages = "vn.alpaca.userservice.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "vn.alpaca.userservice.repository.es")
public class DataConfig extends SpringDataConfig {

  @Override
  public RestHighLevelClient restClient() {
    return super.restClient();
  }

  @Override
  public ElasticsearchOperations esTemplate() {
    return super.esTemplate();
  }

  @Override
  public RedissonClient redissonClient() {
    return super.redissonClient();
  }

  @Override
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient client) {
    return super.redissonConnectionFactory(client);
  }
}
