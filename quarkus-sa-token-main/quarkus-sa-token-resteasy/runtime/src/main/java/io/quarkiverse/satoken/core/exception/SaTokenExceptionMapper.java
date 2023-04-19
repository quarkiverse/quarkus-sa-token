package io.quarkiverse.satoken.core.exception;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.IdTokenInvalidException;
import cn.dev33.satoken.exception.NotBasicAuthException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.NotSafeException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * SaTokenExceptionMapper
 *
 * @author nayan
 * @date 2022/10/8 11:40
 */
public class SaTokenExceptionMapper implements ExceptionMapper<SaTokenException> {
    @Override
    public Response toResponse(SaTokenException exception) {
        int code = -1;
        if (exception instanceof NotLoginException) {
            code = 401;
        } else if (exception instanceof NotRoleException) {
            code = 402;
        } else if (exception instanceof NotPermissionException) {
            code = 403;
        } else if (exception instanceof NotSafeException) {
            code = 901;
        } else if (exception instanceof IdTokenInvalidException) {
            code = 902;
        } else if (exception instanceof NotBasicAuthException) {
            code = 903;
        } else if (exception instanceof DisableServiceException) {
            code = 904;
        }
        return Response.status(Response.Status.UNAUTHORIZED.getStatusCode())
                .entity(SaResult.error(exception.getMessage()).setCode(code))
                .build();
    }
}
