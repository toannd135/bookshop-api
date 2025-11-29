package ptit.edu.vn.bookshop.service;

import org.springframework.data.domain.Pageable;
import ptit.edu.vn.bookshop.dto.request.OrderCreateRequestDTO;
import ptit.edu.vn.bookshop.dto.request.OrderUpdateRequestDTO;
import ptit.edu.vn.bookshop.dto.request.UpdateStatusRequestDTO;
import ptit.edu.vn.bookshop.dto.response.OrderResponseDTO;
import ptit.edu.vn.bookshop.dto.response.page.OrderPageResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderCreateRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(OrderUpdateRequestDTO orderRequestDTO, Long id);
    void deleteOrder(Long id);
    OrderResponseDTO getOrder(Long id);
    OrderPageResponseDTO getAllOrders(Pageable pageable, String[] orders);
    OrderResponseDTO updateOrderStatus(UpdateStatusRequestDTO updateStatusRequestDTO, Long id);
    OrderPageResponseDTO getAllAdminOrders();

}
