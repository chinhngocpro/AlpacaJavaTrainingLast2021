package vn.alpaca.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.alpaca.commonsecurity.object.ServiceUserDetail;
import vn.alpaca.userservice.object.entity.Authority;
import vn.alpaca.userservice.service.AuthorityService;

@Configuration
public class ServiceUserConfig {

    @Autowired
    AuthorityService authorityService;

    @Bean
    public ServiceUserDetail getUserClient() {
        try {
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.unsorted());
            Page<Authority> authorityPage = authorityService.findAll(pageable);

            ServiceUserDetail serviceUserDetail = new ServiceUserDetail();
            serviceUserDetail.setPermissions(authorityPage.map(Authority::getPermissionName).getContent());

            return serviceUserDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceUserDetail();
        }
    }
}
