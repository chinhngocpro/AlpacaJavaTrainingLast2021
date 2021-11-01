package vn.alpaca.security.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCodecConfig {

  @Bean
  public Encoder feignEncoder() {
    ObjectFactory<HttpMessageConverters> factory = HttpMessageConverters::new;
    return new SpringFormEncoder(new SpringEncoder(factory));
  }

  @Bean
  Decoder feignDecoder() {
    ObjectFactory<HttpMessageConverters> factory = HttpMessageConverters::new;
    return new SpringDecoder(factory);
  }
}
