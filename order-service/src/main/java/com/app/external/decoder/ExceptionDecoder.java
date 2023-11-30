package com.app.external.decoder;

import com.app.exception.ExceptionResponse;
import com.app.exception.ExternalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;

    @Override
    public Exception decode(String s, Response response) {
        try {
            ExceptionResponse exceptionResponse = mapper.readValue(response.body().asInputStream(),
                    ExceptionResponse.class);

            throw new ExternalException(exceptionResponse.getErrorMessage());
        } catch (IOException e) {
            throw new RuntimeException("Internal server error");
        }
    }
}
