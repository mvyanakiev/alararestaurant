package alararestaurant.service;

import alararestaurant.domain.dtos.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ITEMS_JSON_PATH = "/Users/milko/Documents/SoftUni/Java-Advanced/DB/Hibernate Spring/AlaraRestaurant/src/main/resources/files/items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, OrderItemRepository orderItemRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_JSON_PATH);
    }

    @Override
    public String importItems(String items) {

        ItemImportDto[] itemImportDtos = this.gson.fromJson(items, ItemImportDto[].class);
        StringBuilder importResult = new StringBuilder();

        for (ItemImportDto itemImportDto : itemImportDtos) {

            if (!this.validationUtil.isValid(itemImportDto)) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }


            if (this.itemRepository.findByName(itemImportDto.getName()).orElse(null) != null) {
                importResult.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Category categoryEntity = this.categoryRepository.findByName(itemImportDto.getCategory()).orElse(null);
            Category newCategory = new Category();

            if (categoryEntity == null) {
                newCategory.setName(itemImportDto.getCategory());
                this.categoryRepository.saveAndFlush(newCategory);
            } else {
                newCategory = categoryEntity;
            }

            Item itemEntity = this.modelMapper.map(itemImportDto, Item.class);
            itemEntity.setCategory(newCategory);


            this.itemRepository.saveAndFlush(itemEntity);

            importResult.append(String.format("Record %s successfully imported.", itemEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
