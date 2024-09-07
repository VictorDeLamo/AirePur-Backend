package com.airepur.repository;

import com.airepur.domain.LocalitzacioEstacioDTO;
import com.airepur.domain.UsuariDTO;
import com.airepur.service.MailService;
import com.airepur.utils.Config;
import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static java.awt.SystemColor.text;

@Repository
public class UsuariRepository {

    private static final String URL = Config.URL;
    private static final String USER = Config.username;
    private static final String PWD = Config.password;

    private static final LocalitzacioEstacioRepository l = new LocalitzacioEstacioRepository();

    private UsuariDTO uDTO = null;
    private LocalitzacioEstacioDTO lDTO = null;

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    private void AssignUsuariObject(ResultSet resultSet) throws SQLException {
        uDTO = new UsuariDTO(resultSet.getString("username"), resultSet.getString("nom"),
                resultSet.getInt("edat"), resultSet.getInt("tlf"), resultSet.getString("email"),
                resultSet.getString("pwd"), resultSet.getString("idioma"), resultSet.getString("fotoperfil"),
                resultSet.getBoolean("administrador"), resultSet.getString("codiestacio"), resultSet.getBoolean("isblocked"));
    }

    public ArrayList<UsuariDTO> getUsuaris() {
        ArrayList<UsuariDTO> ja = new ArrayList<>();
        String query = "SELECT * FROM usuari";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                AssignUsuariObject(resultSet);
                ja.add(uDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ja;
    }

    public UsuariDTO getUsuari(String username) {
        String query = "SELECT * FROM usuari WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AssignUsuariObject(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uDTO;
    }

    public UsuariDTO usuariCorrecte(String username, String password, String email) {
        System.out.println("Received login request for username: " + username + ", email: " + email);

        UsuariDTO user = getUsuari(username);

        // Check if user exists
        if (user == null) {
            if ("google".equals(password)) {
                // Create a new user for Google sign-in
                System.out.println("User not found, creating new user.");
                String pass = newPass();
                String hashedPass = newPassHas(pass);
                crearUsuari(username, hashedPass, email, username, "", "", "en", "", false);
                user = getUsuari(username);
            } else {
                // User does not exist and password is not "google"
                System.out.println("Invalid username or password.");
                return null;
            }
        } else {
            // User exists
            if ("google".equals(password)) {
                // Return the existing user for Google sign-in
                System.out.println("User found, returning user.");
                return user;
            } else if (!user.getPwd().equals(password)) {
                // Invalid password
                System.out.println("Invalid username or password.");
                return null;
            }
        }

        // User authenticated successfully
        System.out.println("User authenticated successfully.");
        return user;
    }

    public String crearUsuari(String username, String password, String email, String nom, String tlf, String edat, String idioma, String urlPerfil, Boolean isBlocked) {
        String checkUsernameQuery = "SELECT COUNT(*) FROM usuari WHERE username = ?";
        String checkEmailQuery = "SELECT COUNT(*) FROM usuari WHERE email = ?";
        String checkTlfQuery = "SELECT COUNT(*) FROM usuari WHERE tlf = ?";
        String insertQuery = "INSERT INTO usuari (username, pwd, email, nom, tlf, edat, idioma, fotoperfil, isBlocked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String answer = "ok";

        try (Connection connection = getConnection()) {
            // Check for duplicate username
            try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery)) {
                checkUsernameStatement.setString(1, username);
                ResultSet rs = checkUsernameStatement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    return "username";
                }
            }

            // Check for duplicate email
            try (PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailQuery)) {
                checkEmailStatement.setString(1, email);
                ResultSet rs = checkEmailStatement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    return "email";
                }
            }

            // Check for duplicate tlf
            if (!tlf.isEmpty()) {
                try (PreparedStatement checkTlfStatement = connection.prepareStatement(checkTlfQuery)) {
                    checkTlfStatement.setInt(1, Integer.parseInt(tlf));
                    ResultSet rs = checkTlfStatement.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        return "telefono";
                    }
                }
            }

            // Insert the new user if no duplicates are found
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.setString(4, nom);
                insertStatement.setString(8, urlPerfil);
                insertStatement.setBoolean(9, isBlocked);
                if (!tlf.equals("")) {
                    insertStatement.setInt(5, Integer.valueOf(tlf));
                } else {
                    insertStatement.setNull(5, java.sql.Types.INTEGER);
                }
                if (!edat.equals("")) {
                    insertStatement.setInt(6, Integer.valueOf(edat));
                } else {
                    insertStatement.setNull(6, java.sql.Types.INTEGER);
                }
                insertStatement.setString(7, idioma);
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            answer = "error";
        }

        return answer;
    }

    public boolean usuariRemove(String username) {
        boolean ok = true;
        String query = "DELETE FROM usuari WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            ok = false;
        }
        return ok;
    }

    public boolean toggleUserBlock(String username, boolean isBlocked) {
        boolean ok = true;
        UsuariDTO u = getUsuari(username);
        if (!u.getIsBlocked()) {
            MailService correo = new MailService();
            correo.blockUser(u.getEmail());
        }
        String query = "UPDATE usuari SET isBlocked = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isBlocked);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            ok = false;
        }
        return ok;
    }

    public String updatePass(String email) {
        String query = "UPDATE usuari SET pwd = ? WHERE email = ?";
        String pwd = newPass();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassHas(pwd));
            statement.setString(2, email);
            int rows = statement.executeUpdate();
            if (rows == 0) pwd = "correo";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    private String newPass() {
        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        final String NUMBERS = "0123456789";
        final String CHAR_ALL = CHAR_LOWER + CHAR_UPPER + NUMBERS;
        StringBuilder pass = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; 10 > i; ++i) {
            int randomIndex = random.nextInt(CHAR_ALL.length());
            pass.append(CHAR_ALL.charAt(randomIndex));
        }

        return pass.toString();
    }

    public static String newPassHas(String input) {
        try {
            // Obtener una instancia del algoritmo de hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar el algoritmo de hash a la cadena de entrada
            byte[] hashBytes = digest.digest(input.getBytes());

            // Convertir el array de bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar el caso en el que el algoritmo no está disponible
            e.printStackTrace();
            return null;
        }
    }

    public String assignarCodiStacio(String username, String codi) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET codiestacio = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codi);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "codigo";
        }
        return verificacion;
    }

    public String newName(String username, String name) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET nom = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String newYourPass(String username, String pass) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET pwd = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pass);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String newEdat(String username, String edat) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET edat = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(edat));
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String newTel(String username, String tel) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET tlf = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(tel));
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String newEmail(String username, String email) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET email = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String newIdioma(String username, String idioma) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET idioma = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, idioma);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }

    public String adminToken(String username, String password) {
        String query = "SELECT u.administrador FROM usuari u WHERE u.username = ? and u.pwd = ?";
        String verificacion = "noAdmin";
        boolean admin = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin = resultSet.getBoolean("administrador");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        if (admin) {
            verificacion = updateToken(username);
        }
        return verificacion;
    }

    private String updateToken(String username) {
        String token = createToken();
        String query = "UPDATE usuari SET token = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) token = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            token = "error";
        }
        return token;
    }

    private String createToken() {
        UUID uuid = UUID.randomUUID();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuid.toString().getBytes());
    }

    public String newFotoPerfil(String username, String urlPerfil) {
        String verificacion = "ok";
        String query = "UPDATE usuari SET fotoperfil = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, urlPerfil);
            statement.setString(2, username);
            int rows = statement.executeUpdate();
            if (rows == 0) verificacion = "error";
        } catch (SQLException e) {
            e.printStackTrace();
            verificacion = "error";
        }
        return verificacion;
    }
}
