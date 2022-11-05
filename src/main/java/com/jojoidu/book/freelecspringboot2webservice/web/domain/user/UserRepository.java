package com.jojoidu.book.freelecspringboot2webservice.web.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //optional null 일 수 도 있는 객체 값을 감싸는 wrapper class
    Optional<User> findByEmail(String email);

}
