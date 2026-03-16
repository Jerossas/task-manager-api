package com.dunnwr.taskmanagerapi.springdata.service;

import com.dunnwr.taskmanagerapi.services.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder bcrypt;

    public PasswordEncoderImpl(BCryptPasswordEncoder bcrypt){
        this.bcrypt = bcrypt;
    }

    @Override
    public String encode(String rawString) {
        return this.bcrypt.encode(rawString);
    }
}
