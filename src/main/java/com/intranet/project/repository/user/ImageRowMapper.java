package com.intranet.project.repository.user;

import org.springframework.jdbc.core.RowMapper;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRowMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet resultSet, int i) throws SQLException {
        Image image = new Image();
        try {
            image.setImage(resultSet.getBinaryStream("picture").readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
