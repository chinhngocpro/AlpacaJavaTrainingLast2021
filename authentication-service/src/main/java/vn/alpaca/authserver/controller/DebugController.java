package vn.alpaca.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
@RequiredArgsConstructor
//@RequestMapping(value = "/debug", produces = MediaType.APPLICATION_JSON_VALUE)
public class DebugController {
    final FilterChainProxy filterChainProxy;

    @GetMapping("/filter-chain")
    public Map<Integer, Map<Integer, String>> filterChainDebug() {
        return getSecurityFilterChainProxy();
    }

    public Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy(){
        Map<Integer, Map<Integer, String>> filterChains= new HashMap<Integer, Map<Integer, String>>();
        int i = 1;
        for(SecurityFilterChain secfc :  this.filterChainProxy.getFilterChains()){
            //filters.put(i++, secfc.getClass().getName());
            Map<Integer, String> filters = new HashMap<Integer, String>();
            int j = 1;
            for(Filter filter : secfc.getFilters()){
                filters.put(j++, filter.getClass().getName());
            }
            filterChains.put(i++, filters);
        }
        return filterChains;
    }
}
