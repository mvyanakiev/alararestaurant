package alararestaurant.service;

import alararestaurant.domain.dtos.ordersimportdto.XmlItemImportDto;
import alararestaurant.domain.dtos.ordersimportdto.XmlItemImportRootDto;
import alararestaurant.domain.dtos.ordersimportdto.XmlOrderImportDto;
import alararestaurant.domain.dtos.ordersimportdto.XmlOrdersImportRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class OrderServiceImpl implements OrderService {


    private static final String ORDERS_XML_PATH = "/Users/milko/Documents/SoftUni/Java-Advanced/DB/Hibernate Spring/AlaraRestaurant/src/main/resources/files/orders.xml";

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_XML_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder importResult = new StringBuilder();
        XmlOrdersImportRootDto xmlOrdersImportRootDto = this.xmlParser.parseXml(XmlOrdersImportRootDto.class, ORDERS_XML_PATH);

        for (XmlOrderImportDto xmlOrderImportDto : xmlOrdersImportRootDto.getXmlOrderImportDto()) {
            if (!this.validationUtil.isValid(xmlOrderImportDto)) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Order orderEntity = this.modelMapper.map(xmlOrderImportDto, Order.class);
            Employee employeeEntity = this.employeeRepository.findByName(xmlOrderImportDto.getEmployee()).orElse(null);

            if (employeeEntity == null) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }
            orderEntity.setEmployee(employeeEntity);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            formatter = formatter.withLocale(Locale.getDefault());
            LocalDateTime date = LocalDateTime.MAX.parse(xmlOrderImportDto.getDateTime(), formatter);
            orderEntity.setDateTime(date);



            List<OrderItem> orderItems = new ArrayList<>();
            this.orderRepository.saveAndFlush(orderEntity);

            Arrays.stream(xmlOrderImportDto.getXmlItemImportRootDtos().getXmlItemImportDtos()).forEach(itemImportDto -> {

                if (!this.validationUtil.isValid(itemImportDto)) {
                    importResult.append("Invalid data format.").append(System.lineSeparator());
                    return;
                }

                Item itemEntity = this.itemRepository.findByName(itemImportDto.getName()).orElse(null);

                if (itemEntity == null) {
                    importResult.append("Invalid data format.").append(System.lineSeparator());
                    return;
                }

                OrderItem orderItemEntity = new OrderItem();
                orderItemEntity.setItem(itemEntity);
                orderItemEntity.setQuantity(itemImportDto.getQuantity());
                orderItemEntity.setOrder(orderEntity);

                orderItems.add(orderItemEntity);


            });

            orderEntity.setOrderItems(orderItems);
        this.orderItemRepository.saveAll(orderItems);

        importResult.append(String.format("Order for %s on %s/%s/%s %s:%s added",
                orderEntity.getCustomer(),
                orderEntity.getDateTime().getDayOfMonth(),
                orderEntity.getDateTime().getMonth(),
                orderEntity.getDateTime().getYear(),
                orderEntity.getDateTime().getHour(),
                orderEntity.getDateTime().getMinute()
                ))
                .append(System.lineSeparator());


        }



        return importResult.toString().trim();
    }


    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}
