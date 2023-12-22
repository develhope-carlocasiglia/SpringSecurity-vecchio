package co.develhope.LoginDemo.order.services;

import co.develhope.LoginDemo.order.entities.Order;
import co.develhope.LoginDemo.order.repositories.OrdersRepository;
import co.develhope.LoginDemo.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Order save(Order orderInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (orderInput == null)return null;
        orderInput.setId(null);
        orderInput.setCreatedAt(LocalDateTime.now());
        orderInput.setCreatedBy(user);
        return ordersRepository.save(orderInput);
    }

    public Order update(Long id, Order orderInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (orderInput == null)return null;
        orderInput.setId(id);
        orderInput.setUpdatedAt(LocalDateTime.now());
        orderInput.setUpdatedBy(user);
        return ordersRepository.save(orderInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return false;
        return order.get().getCreatedBy().getId() == user.getId();
    }
}
