package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {

        List<Category> cats = this.categoryRepository.exportCategoriesByCountOfItems();
        StringBuilder result = new StringBuilder();

        for (Category cat : cats) {
            result.append(String.format("Category: %s", cat.getName()))
                    .append(System.lineSeparator());

            cat.getItems().stream().forEach(item -> {
                result.append(String.format("--- Item Name: %s", item.getName()))
                        .append(System.lineSeparator());

                result.append(String.format("--- Item Price: %.2f", item.getPrice()))
                        .append(System.lineSeparator());

                result.append(System.lineSeparator());

            });


            result.append(System.lineSeparator());

        }


        return result.toString().trim();
    }
}

//List<Town> towns = this.townRepository.exportRacingTowns();
//
//        StringBuilder result = new StringBuilder();
//
//        for (Town town : towns) {
//            result.append(String.format("Name: %s", town.getName()))
//                    .append(System.lineSeparator())
//                    .append(String.format("Racers: %d", town.getRacers().size()))
//                    .append(System.lineSeparator())
//                    .append(System.lineSeparator());
//        }
//
//        return result.toString().trim();
//    }