package ptit.edu.vn.bookshop.service.impl;

import org.springframework.stereotype.Service;
import ptit.edu.vn.bookshop.domain.constant.OrderStatusEnum;
import ptit.edu.vn.bookshop.dto.response.dashboard.OrderStatusStatisticResponse;
import ptit.edu.vn.bookshop.dto.response.dashboard.OverviewResponse;
import ptit.edu.vn.bookshop.dto.response.dashboard.RevenueByMonthResponse;
import ptit.edu.vn.bookshop.repository.BookRepository;
import ptit.edu.vn.bookshop.repository.OrderRepository;
import ptit.edu.vn.bookshop.repository.UserRepository;
import ptit.edu.vn.bookshop.service.AdminDashboardService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    public AdminDashboardServiceImpl(UserRepository userRepository, BookRepository bookRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OverviewResponse getOverview() {
        long totalUsers = this.userRepository.count();
        long inactiveUsers = this.userRepository.countUserInActive();
        long totalBooks = this.bookRepository.count();
        long outOfStockBooks = this.bookRepository.countBookOutOfStock();
        long totalOrders = this.orderRepository.count();
        BigDecimal totalPrice = this.orderRepository.getTotalPrice();
        OverviewResponse overviewResponse = new OverviewResponse();
        overviewResponse.setTotalUsers(totalUsers);
        overviewResponse.setInactiveUsers(inactiveUsers);
        overviewResponse.setTotalBooks(totalBooks);
        overviewResponse.setOutOfStockBooks(outOfStockBooks);
        overviewResponse.setTotalOrders(totalOrders);
        overviewResponse.setTotalRevenue(totalPrice);
        return overviewResponse;
    }

    @Override
    public List<RevenueByMonthResponse> getRevenueByMonth() {
        List<Object[]> results = this.orderRepository.getRevenueByMonth();
        List<RevenueByMonthResponse> revenueByMonth = new ArrayList<>();
        for (Object[] row : results) {
            if (row[0] == null || row[1] == null || row[2] == null) {
                continue;
            }
            int year = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            BigDecimal totalRevenue = (BigDecimal) row[2];
            revenueByMonth.add(new RevenueByMonthResponse(year, month, totalRevenue));
        }
        return revenueByMonth;
    }

    @Override
    public List<OrderStatusStatisticResponse> getOrderStatusStatistic() {
        List<Object[]> results = this.orderRepository.getOrderStatusStatistic();
        List<OrderStatusStatisticResponse> list = new ArrayList<>();
        for (Object[] row : results) {
            OrderStatusEnum orderStatus = (OrderStatusEnum) row[0];
            Long count = ((Number) row[1]).longValue();
            list.add(new OrderStatusStatisticResponse(orderStatus, count));
        }
        return list;
    }
}
