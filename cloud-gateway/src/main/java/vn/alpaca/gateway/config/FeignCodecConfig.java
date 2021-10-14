package vn.alpaca.gateway.config;

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
    public Encoder feignFormEncoder() {
        ObjectFactory<HttpMessageConverters> messageConverters =
                HttpMessageConverters::new;
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Decoder feignDecoder() {

        ObjectFactory<HttpMessageConverters> messageConverters =
                HttpMessageConverters::new;
        return new SpringDecoder(messageConverters);
    }
}