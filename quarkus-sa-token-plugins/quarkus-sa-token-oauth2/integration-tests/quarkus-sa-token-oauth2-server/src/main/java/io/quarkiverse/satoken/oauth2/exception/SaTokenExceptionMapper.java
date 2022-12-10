package io.quarkiverse.satoken.oauth2.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;

@Provider
public class SaTokenExceptionMapper implements ExceptionMapper<SaTokenException> {
    @Override
    public Response toResponse(SaTokenException exception) {
        String message = exception.getMessage();
        return Response.ok(SaResult.error(message)).build();
    }
}
