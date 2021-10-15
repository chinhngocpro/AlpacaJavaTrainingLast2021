package config.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import vn.alpaca.commonsecurity.client.SecurityUserClient;
import vn.alpaca.commonsecurity.object.SecurityAuthorityDetail;
import vn.alpaca.commonsecurity.object.ServiceUserDetail;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.result.PageResultWrapper;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ServiceUserConfig {

    @Autowired
    SecurityUserClient userClient;

    @Bean
    public ServiceUserDetail getUserClient() {
        try {
            SuccessResponse<PageResultWrapper<SecurityAuthorityDetail>> authorityPage = userClient.getAuthorities();
            List<SecurityAuthorityDetail> securityAuthorityDetails = authorityPage.getData().getContent();
            List<String> authorities = securityAuthorityDetails.stream().map(SecurityAuthorityDetail::getPermissionName).collect(Collectors.toList());

            ServiceUserDetail serviceUserDetail = new ServiceUserDetail();
            serviceUserDetail.setPermissions(authorities);

            return serviceUserDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return  new ServiceUserDetail();
        }
    }
}
