package vn.alpaca.alpacajavatraininglast2021.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerFeatureControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerService customerService;

    private final String idCardNumberValue = "123456789";

    @Test
    void getInfo() throws Exception {
        int customerId = customerService
                .findCustomerByIdCardNumber(idCardNumberValue)
                .getId();

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/{idCardNumber}/info",
                                idCardNumberValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code").value(0))
                .andExpect(jsonPath("$.data.id").value(customerId));
    }

    @Test
    void getContractsByCustomerId() throws Exception {


        mvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/{idCardNumber}/contract",
                                idCardNumberValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code").value(0));
    }

    @Test
    void getClaimRequestsInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/{idCardNumber}/claim-request",
                                idCardNumberValue)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code").value(0));
    }

    @Test
    void getPaymentByRequestId() throws Exception {
        int claimRequestId = 3;

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/{idCardNumber}/payment/{requestId}",
                                idCardNumberValue,
                                claimRequestId
                        )
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code").value(0));
    }

    @Test @Disabled
    void createNewClaimRequest() {
    }
}