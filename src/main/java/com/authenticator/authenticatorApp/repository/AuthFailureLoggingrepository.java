package com.authenticator.authenticatorApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.authenticator.authenticatorApp.entity.AuthFailureDTO;

@Repository
public interface AuthFailureLoggingrepository extends CrudRepository<AuthFailureDTO, Long> {

}
