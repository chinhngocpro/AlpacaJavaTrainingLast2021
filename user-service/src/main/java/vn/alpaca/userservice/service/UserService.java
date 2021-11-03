package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.UserFilter;
import vn.alpaca.common.dto.request.UserRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.userservice.entity.es.UserES;
import vn.alpaca.userservice.entity.jpa.Role;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.repository.es.UserESRepository;
import vn.alpaca.userservice.repository.jpa.RoleJpaRepository;
import vn.alpaca.userservice.repository.jpa.UserJpaRepository;
import vn.alpaca.userservice.repository.jpa.spec.UserSpec;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserJpaRepository userJpaRepo;
    private final RoleJpaRepository roleJpaRepo;
    private final UserESRepository userEsRepo;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final ElasticsearchRestTemplate restTemplate;
    private final IndexCoordinates index = IndexCoordinates.of("users");

    @PostConstruct
    protected void validateData() {
        long jpaCount = userJpaRepo.count();
        long esCount = userEsRepo.count();
        if (esCount != jpaCount) {
            log.info("ON LOAD USER DATA FROM JPA TO ES...");
            userEsRepo.deleteAll();
            userEsRepo.saveAll(
                    userJpaRepo.findAll().stream()
                               .map(userMapper::userToUserES)
                               .collect(Collectors.toList()));
        }
    }

    public Page<User> findAll(UserFilter filter) {
        Page<User> users;

        BoolQueryBuilder query = QueryBuilders.boolQuery();

        if (!ObjectUtils.isEmpty(filter.getUsername())) {
            query.should(wildcardQuery("username", "*" + filter.getUsername() + "*"));
        }
        if (!ObjectUtils.isEmpty(filter.getFullName())) {
            query.should(matchQuery("full_name", filter.getFullName()));
        }
        if (!ObjectUtils.isEmpty(filter.getIdCardNumber())) {
            query.should(termQuery("id_card_number", filter.getIdCardNumber()));
        }
        if (!ObjectUtils.isEmpty(filter.getPhoneNumber())) {
            query.should(termQuery("phone_numbers", filter.getPhoneNumber()));
        }
        if (!ObjectUtils.isEmpty(filter.getAddress())) {
            query.should(matchQuery("address", filter.getAddress()));
        }
        if (!ObjectUtils.isEmpty(filter.getFrom()) && !ObjectUtils.isEmpty(filter.getTo())) {
            query.should(
                    rangeQuery("date_of_birth")
                            .format("date_option_time")
                            .gte(filter.getFrom())
                            .lte(filter.getTo()));
        } else if (!ObjectUtils.isEmpty(filter.getFrom()) && ObjectUtils.isEmpty(filter.getTo())) {
            query.should(rangeQuery("date_of_birth").format("date_option_time").gte(filter.getFrom()));
        } else if (ObjectUtils.isEmpty(filter.getFrom()) && !ObjectUtils.isEmpty(filter.getTo())) {
            query.should(rangeQuery("date_of_birth").format("date_option_time").lte(filter.getTo()));
        }
        if (!ObjectUtils.isEmpty(filter.getGender())) {
            query.should(matchQuery("gender", filter.getGender()));
        }
        if (!ObjectUtils.isEmpty(filter.getActive())) {
            query.should(matchQuery("active", filter.getActive()));
        }

        NativeSearchQuery searchQuery =
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(filter.getPagination().getPageAndSort())
                        .build();
        SearchHits<UserES> hits = restTemplate.search(searchQuery, UserES.class, index);

        if (hits.hasSearchHits()) {
            SearchPage<UserES> page =
                    SearchHitSupport.searchPageFor(hits, filter.getPagination().getPageAndSort());
            users =
                    new PageImpl<>(
                            page.getContent().stream()
                                .map(SearchHit::getContent)
                                .map(userMapper::userESToUser)
                                .collect(Collectors.toList()),
                            page.getPageable(),
                            page.getTotalElements());
            log.info("FOUND: " + users);
        } else {
            Specification<User> specification = UserSpec.getUserSpec(filter);
            users = userJpaRepo.findAll(specification, filter.getPagination().getPageAndSort());
            log.info("USE DB: " + users);
        }

        return users;
    }

    public User findById(int id) {
        User user;

        Optional<UserES> optional = userEsRepo.findById(id);
        if (optional.isPresent()) {
            user = userMapper.userESToUser(optional.get());
        } else {
            user = userJpaRepo
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found user with id " + id));
        }

        return user;
    }

    public User findByUsername(String username) {
        User user;

        Optional<UserES> optional = userEsRepo.findByUsername(username);
        if (optional.isPresent()) {
            user = userMapper.userESToUser(optional.get());
        } else {
            user = userJpaRepo
                    .findByUsername(username)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Not found user with username " + username));
        }

        return user;
    }

    public User create(UserRequest requestData) {
        User user = userMapper.userRequestToUser(requestData);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleJpaRepo
                .findById(requestData.getRoleId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Not found role with id " + requestData.getRoleId()));
        user.setRole(role);

        userJpaRepo.save(user);

        return user;
    }

    public User update(int userId, UserRequest requestData) {
        User user = findById(userId);

        userMapper.updateUser(user, requestData);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleJpaRepo
                .findById(requestData.getRoleId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Not found role with id " + requestData.getRoleId()));
        user.setRole(role);

        userJpaRepo.save(user);

        return user;
    }

    public void activate(int userId) {
        User user = findById(userId);
        user.setActive(true);
        userJpaRepo.save(user);
    }

    public void deactivate(int userId) {
        User user = findById(userId);
        user.setActive(false);
        userJpaRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }
}
