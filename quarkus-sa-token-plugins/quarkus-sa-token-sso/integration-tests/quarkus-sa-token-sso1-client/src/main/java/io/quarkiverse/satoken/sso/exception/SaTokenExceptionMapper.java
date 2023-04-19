package io.quarkiverse.satoken.sso.exception;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SaTokenExceptionMapper implements ExceptionMapper<SaTokenException> {
    @Override
    public Response toResponse(SaTokenException exception) {
        String message = exception.getMessage();
        return Response.ok(SaResult.error(message)).build();
    }
}
