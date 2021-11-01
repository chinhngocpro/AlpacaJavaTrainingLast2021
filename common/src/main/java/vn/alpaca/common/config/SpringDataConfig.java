package vn.alpaca.common.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

public class SpringDataConfig {

  @Bean
  public RestHighLevelClient restClient() {
    final ClientConfiguration configuration =
        ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .withBasicAuth("elastic", "123456")
            .build();
    return RestClients.create(configuration).rest();
  }

  @Bean(name = "elasticsearchTemplate")
  public ElasticsearchOperations esTemplate() {
    return new ElasticsearchRestTemplate(restClient());
  }

  @Bean(destroyMethod = "shutdown")
  @ConditionalOnMissingBean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer().setClientName("master").setAddress("redis://127.0.0.1:6379");

    return Redisson.create(config);
  }

  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient client) {
    return new RedissonConnectionFactory(client);
  }
}
