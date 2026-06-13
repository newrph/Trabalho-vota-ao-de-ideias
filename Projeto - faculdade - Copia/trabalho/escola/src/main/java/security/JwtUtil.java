package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import model.Usuario;

public class JwtUtil {

    private static final String SECRET =
            "FACULDADE2026";

    public static String gerarToken(
            Usuario usuario) {

        Algorithm algorithm =
                Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withClaim("id", usuario.getId())
                .withClaim("nome", usuario.getNome())
                .withClaim("email", usuario.getEmail())
                .sign(algorithm);
    }

    public static boolean validarToken(
            String token) {

        try {

            Algorithm algorithm =
                    Algorithm.HMAC256(SECRET);

            JWT.require(algorithm)
                    .build()
                    .verify(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}