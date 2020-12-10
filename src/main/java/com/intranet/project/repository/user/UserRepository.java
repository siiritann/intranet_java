package com.intranet.project.repository.user;

import com.intranet.project.exceptions.InternalServerErrorException;
import com.intranet.project.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Long createUser(UserEntity userEntity) {
        String sql = "INSERT INTO intranetuser (username, password, email, is_admin) VALUES (:username, :password, :email, :admin)";
        Map<String, Object> paramMap = new HashMap<>();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String email = userEntity.getEmail();
        Boolean admin = userEntity.getIsAdmin();
        paramMap.put("username", username);
        paramMap.put("password", password);
        paramMap.put("email", email);
        paramMap.put("admin", admin);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
        Long userID = (Long) keyHolder.getKeys().get("id");
        if(userID == null){
            throw new InternalServerErrorException("Internal server error: intranetuser id == null");
        }
        return userID;
    }

    public Long getUserIdByUsername(String username){
        String sql = "SELECT id FROM intranetuser WHERE username = :username ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        return jdbcTemplate.queryForObject(sql, paramMap, Long.class);
    }
    public List<Long> getUserIdListByUsername(String username){
        String sql = "SELECT id FROM intranetuser WHERE username ILIKE :username ";
        String name = "%"+username+"%";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", name);
        return jdbcTemplate.queryForList(sql, paramMap, Long.class);
    }

    public UserEntity getUserById(Long id){
        String sql = "SELECT * FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<UserEntity> userList = jdbcTemplate.query(sql, paramMap, new UserRowMapper());
        if (userList.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            return userList.get(0);
        }
    }

    public UserEntity getUserEntityByUsername(String username){
        String sql = "SELECT * FROM intranetuser WHERE username = :username";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        return jdbcTemplate.queryForObject(sql, paramMap, new UserRowMapper());
    }

    public String getUsernameById(Long id){
        String sql = "SELECT username FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, String.class);
    }

    public Long getUserIdByEmail(String email) {
        String sql = "SELECT id FROM intranetuser WHERE email = :email";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", email);
        return jdbcTemplate.queryForObject(sql, paramMap, Long.class);
    }

    public List<UserEntity> getListOfUsers(){
        String sql = "SELECT * FROM intranetuser ORDER BY username";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.query(sql, paramMap, new UserRowMapper());
    }
    public List<String> getListOfUsernames() {
        String sql = "SELECT username FROM intranetuser";
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForList(sql, paramMap, String.class);
    }

    public boolean checkIfAdmin(Long id) {
        String sql = "SELECT is_admin FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, Boolean.class);
    }

    public UserEntity viewUser(Long id) {
        String sql = "SELECT * FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<UserEntity> list = jdbcTemplate.query(sql, paramMap, new UserRowMapper());
        return list.get(0);
    }

    public int updateUser(UserEntity userEntityUpdated){
        String sql = "UPDATE intranetuser SET email = :email, first_name = :firstname, " +
                "last_name = :lastname, phone = :phone, birth_date = :birthdate  WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", userEntityUpdated.getId());
        paramMap.put("email", userEntityUpdated.getEmail());
        paramMap.put("firstname", userEntityUpdated.getFirstName());
        paramMap.put("lastname", userEntityUpdated.getLastName());
        paramMap.put("phone", userEntityUpdated.getPhone());
        paramMap.put("birthdate", userEntityUpdated.getBirthDate());
        return jdbcTemplate.update(sql, paramMap);
    }

    public int updateUserPassword(Long id, String password){
        String sql = "UPDATE intranetuser SET password = :password WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("password", password);
        return jdbcTemplate.update(sql, paramMap);
    }

    public int deleteUserById(Long id){
        String sql = "DELETE FROM intranetuser WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcTemplate.update(sql, paramMap);
    }

    public byte[] getImageById(Long userId) throws SQLException, IOException {
        String sql = "SELECT picture FROM image WHERE user_id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        List<Image> resultList = jdbcTemplate.query(sql, paramMap, new ImageRowMapper());
        if(resultList.size() > 0){
            return resultList.get(0).getImage();
        } else {
            File file = new File("src/main/resources/images/avatar.jpg");
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos );
            return baos.toByteArray();
        }
    }

    public void postImage(byte[] bytes, long userId) {
        String sqlOverwrite = "UPDATE image SET picture = :bytes WHERE user_id = :userId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("bytes", bytes);

        if(jdbcTemplate.update(sqlOverwrite, paramMap) == 0){
            String sql = "INSERT INTO image (user_id, picture) VALUES (:userId, :bytes)";
            jdbcTemplate.update(sql, paramMap);
        }
    }

    public String deleteImages(Long userId) {
        String sql = "DELETE FROM image WHERE user_id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        if(jdbcTemplate.update(sql, paramMap) > 0){
            return "Image deleted";
        }
        return "Image delete failed";
    }


    public int saveUUIDToBase(Long userId, String uuid){
        String sql = "UPDATE intranetuser SET reset_uuid = :uuid WHERE id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("uuid", uuid);
        return jdbcTemplate.update(sql, paramMap);
    }

    public int removeUUID(Long userId){
        String sql = "UPDATE intranetuser SET reset_uuid = null WHERE id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        return jdbcTemplate.update(sql, paramMap);
    }


    public Long getUserIdForUUID(String uuid) {
        String sql = "SELECT id FROM intranetuser WHERE reset_uuid = :uuid";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uuid", uuid);
        try {
            return jdbcTemplate.queryForObject(sql, paramMap, Long.class);
        } catch (Exception e) {
            throw new NotFoundException("Recovery link not found");
        }
    }


    public void updateUserRole(long userId) {
        String sql = "UPDATE intranetuser SET is_admin = true WHERE id = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        jdbcTemplate.update(sql, paramMap);
    }
}
