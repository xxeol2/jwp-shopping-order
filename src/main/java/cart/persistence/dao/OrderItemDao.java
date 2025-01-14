package cart.persistence.dao;

import cart.persistence.entity.OrderItemEntity;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void createAll(final List<OrderItemEntity> orderItems) {
        String sql = "INSERT INTO order_item (order_id, product_id, name, price, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderItemEntity orderItem = orderItems.get(i);
                ps.setLong(1, orderItem.getOrderId());
                ps.setLong(2, orderItem.getProductId());
                ps.setString(3, orderItem.getName());
                ps.setInt(4, orderItem.getPrice());
                ps.setString(5, orderItem.getImageUrl());
                ps.setInt(6, orderItem.getQuantity());
            }

            @Override
            public int getBatchSize() {
                return orderItems.size();
            }
        });
    }

    public List<OrderItemEntity> findByOrderId(final long orderId) {
        String sql = "SELECT * FROM order_item "
                + "WHERE order_id = ?";

        return jdbcTemplate.query(sql, RowMapperHelper.orderItemRowMapper(), orderId);
    }

    public List<OrderItemEntity> findByOrderIds(final List<Long> orderIds) {
        String sql = "SELECT * FROM order_item "
                + "WHERE order_id IN (:orderIds)";

        SqlParameterSource parameters = new MapSqlParameterSource("orderIds", orderIds);
        return namedParameterJdbcTemplate.query(sql, parameters, RowMapperHelper.orderItemRowMapper());
    }
}
