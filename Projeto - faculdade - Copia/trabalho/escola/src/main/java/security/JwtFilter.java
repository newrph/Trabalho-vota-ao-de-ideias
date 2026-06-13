package security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/api/*")
public class JwtFilter
        implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException,
            ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse resp =
                (HttpServletResponse) response;

        String uri =
                req.getRequestURI();

        if (uri.contains("/auth/login")) {

            chain.doFilter(
                    request,
                    response);

            return;
        }

        String auth =
                req.getHeader(
                        "Authorization");

        if (auth == null ||
                !auth.startsWith("Bearer ")) {

            resp.sendError(401);

            return;
        }

        String token =
                auth.replace(
                        "Bearer ",
                        "");

        if (!JwtUtil.validarToken(token)) {

            resp.sendError(401);

            return;
        }

        chain.doFilter(
                request,
                response);
    }
}