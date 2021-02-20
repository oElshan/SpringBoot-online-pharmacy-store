package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isha.store.entity.OrderItem;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {



    List<OrderItem> findAll();

   OrderItem save(OrderItem orderItem);

     @Modifying
    @Query(value = "delete from order_item as oi where oi.id=:productId and oi.id_order=:orderId ",nativeQuery = true)
    void deleteByIdAndClientOrder_Id(@Param("productId") long productId, @Param("orderId") long orderId);

    void deleteOrderItemByIdAndClientOrderId(Long id, Long orderId);

}
