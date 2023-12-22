package co.develhope.LoginDemo.order.repositories;

import co.develhope.LoginDemo.order.entities.Order;
import co.develhope.LoginDemo.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedBy(User user);
}
