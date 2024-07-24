package com.pard.admlong_be.domain.test.service;

import com.pard.admlong_be.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TestService {
    public ResponseDTO testService() {
        List<String> testlist = new ArrayList<>();
        testlist.add("test1");
        testlist.add("test2");
        return new ResponseDTO(true, "nothing happened", testlist);
    }
}
