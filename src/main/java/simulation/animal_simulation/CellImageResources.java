package simulation.animal_simulation;

import javafx.scene.image.Image;
import simulation.animal_simulation.animals.Age;

/**
 * The CellImageResources class is responsible for managing the images used in the animal simulation.
 * It includes images for different states of cells, such as empty cells, cells with grass of different quantities,
 * cells with rabbits or foxes of different ages, and cells with dead animals.
 *
 * <p>This class provides methods to get the appropriate image based on the state of the cell, such as the quantity of grass,
 * the presence of a rabbit or a fox, whether the animal is alive, and the age of the animal.</p>
 */
public class CellImageResources {

    final Image emptyCellImage = new Image("/empty_cell.png");
    final Image emptyWithChildRabbitCellImage = new Image("/empty_with_child_rabbit_cell.png");
    final Image emptyWithAdultRabbitCellImage = new Image("/empty_with_adult_rabbit_cell.png");
    final Image emptyWithSeniorRabbitCellImage = new Image("/empty_with_senior_rabbit_cell.png");
    final Image emptyWithDeadRabbitCellImage = new Image("/empty_with_dead_rabbit_cell.png");
    final Image emptyWithChildFoxCellImage = new Image("/empty_with_child_fox_cell.png");
    final Image emptyWithAdultFoxCellImage = new Image("/empty_with_adult_fox_cell.png");
    final Image emptyWithSeniorFoxCellImage = new Image("/empty_with_senior_fox_cell.png");
    final Image emptyWithDeadFoxCellImage = new Image("/empty_with_dead_fox_cell.png");
    final Image grass1CellImage = new Image("/grass_1_cell.png");
    final Image grass1WithChildRabbitCellImage = new Image("/grass_1_with_child_rabbit_cell.png");
    final Image grass1WithAdultRabbitCellImage = new Image("/grass_1_with_adult_rabbit_cell.png");
    final Image grass1WithSeniorRabbitCellImage = new Image("/grass_1_with_senior_rabbit_cell.png");
    final Image grass1WithDeadRabbitCellImage = new Image("/grass_1_with_dead_rabbit_cell.png");
    final Image grass1WithChildFoxCellImage = new Image("/grass_1_with_child_fox_cell.png");
    final Image grass1WithAdultFoxCellImage = new Image("/grass_1_with_adult_fox_cell.png");
    final Image grass1WithSeniorFoxCellImage = new Image("/grass_1_with_senior_fox_cell.png");
    final Image grass1WithDeadFoxCellImage = new Image("/grass_1_with_dead_fox_cell.png");
    final Image grass2CellImage = new Image("/grass_2_cell.png");
    final Image grass2WithChildRabbitCellImage = new Image("/grass_2_with_child_rabbit_cell.png");
    final Image grass2WithAdultRabbitCellImage = new Image("/grass_2_with_adult_rabbit_cell.png");
    final Image grass2WithSeniorRabbitCellImage = new Image("/grass_2_with_senior_rabbit_cell.png");
    final Image grass2WithDeadRabbitCellImage = new Image("/grass_2_with_dead_rabbit_cell.png");
    final Image grass2WithChildFoxCellImage = new Image("/grass_2_with_child_fox_cell.png");
    final Image grass2WithAdultFoxCellImage = new Image("/grass_2_with_adult_fox_cell.png");
    final Image grass2WithSeniorFoxCellImage = new Image("/grass_2_with_senior_fox_cell.png");
    final Image grass2WithDeadFoxCellImage = new Image("/grass_2_with_dead_fox_cell.png");
    final Image grass3CellImage = new Image("/grass_3_cell.png");
    final Image grass3WithChildRabbitCellImage = new Image("/grass_3_with_child_rabbit_cell.png");
    final Image grass3WithAdultRabbitCellImage = new Image("/grass_3_with_adult_rabbit_cell.png");
    final Image grass3WithSeniorRabbitCellImage = new Image("/grass_3_with_senior_rabbit_cell.png");
    final Image grass3WithDeadRabbitCellImage = new Image("/grass_3_with_dead_rabbit_cell.png");
    final Image grass3WithChildFoxCellImage = new Image("/grass_3_with_child_fox_cell.png");
    final Image grass3WithAdultFoxCellImage = new Image("/grass_3_with_adult_fox_cell.png");
    final Image grass3WithSeniorFoxCellImage = new Image("/grass_3_with_senior_fox_cell.png");
    final Image grass3WithDeadFoxCellImage = new Image("/grass_3_with_dead_fox_cell.png");
    final Image grass4CellImage = new Image("/grass_4_cell.png");
    final Image grass4WithChildRabbitCellImage = new Image("/grass_4_with_child_rabbit_cell.png");
    final Image grass4WithAdultRabbitCellImage = new Image("/grass_4_with_adult_rabbit_cell.png");
    final Image grass4WithSeniorRabbitCellImage = new Image("/grass_4_with_senior_rabbit_cell.png");
    final Image grass4WithDeadRabbitCellImage = new Image("/grass_4_with_dead_rabbit_cell.png");
    final Image grass4WithChildFoxCellImage = new Image("/grass_4_with_child_fox_cell.png");
    final Image grass4WithAdultFoxCellImage = new Image("/grass_4_with_adult_fox_cell.png");
    final Image grass4WithSeniorFoxCellImage = new Image("/grass_4_with_senior_fox_cell.png");
    final Image grass4WithDeadFoxCellImage = new Image("/grass_4_with_dead_fox_cell.png");
    final Image thickVegetation5CellImage = new Image("/thick_vegetation_5_cell.png");
    final Image thickVegetation5WithChildRabbitCellImage = new Image("/thick_vegetation_5_with_child_rabbit_cell.png");
    final Image thickVegetation5WithAdultRabbitCellImage = new Image("/thick_vegetation_5_with_adult_rabbit_cell.png");
    final Image thickVegetation5WithSeniorRabbitCellImage = new Image("/thick_vegetation_5_with_senior_rabbit_cell.png");
    final Image thickVegetation5WithDeadRabbitCellImage = new Image("/thick_vegetation_5_with_dead_rabbit_cell.png");
    final Image thickVegetation5WithChildFoxCellImage = new Image("/thick_vegetation_5_with_child_fox_cell.png");
    final Image thickVegetation5WithAdultFoxCellImage = new Image("/thick_vegetation_5_with_adult_fox_cell.png");
    final Image thickVegetation5WithSeniorFoxCellImage = new Image("/thick_vegetation_5_with_senior_fox_cell.png");
    final Image thickVegetation5WithDeadFoxCellImage = new Image("/thick_vegetation_5_with_dead_fox_cell.png");
    final Image thickVegetation6CellImage = new Image("/thick_vegetation_6_cell.png");
    final Image thickVegetation6WithChildRabbitCellImage = new Image("/thick_vegetation_6_with_child_rabbit_cell.png");
    final Image thickVegetation6WithAdultRabbitCellImage = new Image("/thick_vegetation_6_with_adult_rabbit_cell.png");
    final Image thickVegetation6WithSeniorRabbitCellImage = new Image("/thick_vegetation_6_with_senior_rabbit_cell.png");
    final Image thickVegetation6WithDeadRabbitCellImage = new Image("/thick_vegetation_6_with_dead_rabbit_cell.png");
    final Image thickVegetation6WithChildFoxCellImage = new Image("/thick_vegetation_6_with_child_fox_cell.png");
    final Image thickVegetation6WithAdultFoxCellImage = new Image("/thick_vegetation_6_with_adult_fox_cell.png");
    final Image thickVegetation6WithSeniorFoxCellImage = new Image("/thick_vegetation_6_with_senior_fox_cell.png");
    final Image thickVegetation6WithDeadFoxCellImage = new Image("/thick_vegetation_6_with_dead_fox_cell.png");
    final Image thickVegetation7CellImage = new Image("/thick_vegetation_7_cell.png");
    final Image thickVegetation7WithChildRabbitCellImage = new Image("/thick_vegetation_7_with_child_rabbit_cell.png");
    final Image thickVegetation7WithAdultRabbitCellImage = new Image("/thick_vegetation_7_with_adult_rabbit_cell.png");
    final Image thickVegetation7WithSeniorRabbitCellImage = new Image("/thick_vegetation_7_with_senior_rabbit_cell.png");
    final Image thickVegetation7WithDeadRabbitCellImage = new Image("/thick_vegetation_7_with_dead_rabbit_cell.png");
    final Image thickVegetation7WithChildFoxCellImage = new Image("/thick_vegetation_7_with_child_fox_cell.png");
    final Image thickVegetation7WithAdultFoxCellImage = new Image("/thick_vegetation_7_with_adult_fox_cell.png");
    final Image thickVegetation7WithSeniorFoxCellImage = new Image("/thick_vegetation_7_with_senior_fox_cell.png");
    final Image thickVegetation7WithDeadFoxCellImage = new Image("/thick_vegetation_7_with_dead_fox_cell.png");
    final Image thickVegetation8CellImage = new Image("/thick_vegetation_8_cell.png");
    final Image thickVegetation8WithChildRabbitCellImage = new Image("/thick_vegetation_8_with_child_rabbit_cell.png");
    final Image thickVegetation8WithAdultRabbitCellImage = new Image("/thick_vegetation_8_with_adult_rabbit_cell.png");
    final Image thickVegetation8WithSeniorRabbitCellImage = new Image("/thick_vegetation_8_with_senior_rabbit_cell.png");
    final Image thickVegetation8WithDeadRabbitCellImage = new Image("/thick_vegetation_8_with_dead_rabbit_cell.png");
    final Image thickVegetation8WithChildFoxCellImage = new Image("/thick_vegetation_8_with_child_fox_cell.png");
    final Image thickVegetation8WithAdultFoxCellImage = new Image("/thick_vegetation_8_with_adult_fox_cell.png");
    final Image thickVegetation8WithSeniorFoxCellImage = new Image("/thick_vegetation_8_with_senior_fox_cell.png");
    final Image thickVegetation8WithDeadFoxCellImage = new Image("/thick_vegetation_8_with_dead_fox_cell.png");
    final Image thickVegetation9CellImage = new Image("/thick_vegetation_9_cell.png");
    final Image thickVegetation9WithChildRabbitCellImage = new Image("/thick_vegetation_9_with_child_rabbit_cell.png");
    final Image thickVegetation9WithAdultRabbitCellImage = new Image("/thick_vegetation_9_with_adult_rabbit_cell.png");
    final Image thickVegetation9WithSeniorRabbitCellImage = new Image("/thick_vegetation_9_with_senior_rabbit_cell.png");
    final Image thickVegetation9WithDeadRabbitCellImage = new Image("/thick_vegetation_9_with_dead_rabbit_cell.png");
    final Image thickVegetation9WithChildFoxCellImage = new Image("/thick_vegetation_9_with_child_fox_cell.png");
    final Image thickVegetation9WithAdultFoxCellImage = new Image("/thick_vegetation_9_with_adult_fox_cell.png");
    final Image thickVegetation9WithSeniorFoxCellImage = new Image("/thick_vegetation_9_with_senior_fox_cell.png");
    final Image thickVegetation9WithDeadFoxCellImage = new Image("/thick_vegetation_9_with_dead_fox_cell.png");
    final Image thickVegetation10CellImage = new Image("/thick_vegetation_10_cell.png");
    final Image thickVegetation10WithChildRabbitCellImage = new Image("/thick_vegetation_10_with_child_rabbit_cell.png");
    final Image thickVegetation10WithAdultRabbitCellImage = new Image("/thick_vegetation_10_with_adult_rabbit_cell.png");
    final Image thickVegetation10WithSeniorRabbitCellImage = new Image("/thick_vegetation_10_with_senior_rabbit_cell.png");
    final Image thickVegetation10WithDeadRabbitCellImage = new Image("/thick_vegetation_10_with_dead_rabbit_cell.png");
    final Image thickVegetation10WithChildFoxCellImage = new Image("/thick_vegetation_10_with_child_fox_cell.png");
    final Image thickVegetation10WithAdultFoxCellImage = new Image("/thick_vegetation_10_with_adult_fox_cell.png");
    final Image thickVegetation10WithSeniorFoxCellImage = new Image("/thick_vegetation_10_with_senior_fox_cell.png");
    final Image thickVegetation10WithDeadFoxCellImage = new Image("/thick_vegetation_10_with_dead_fox_cell.png");
    final Image burrowCellImage = new Image("/burrow_cell.png");

    public Image getRabbitCellImage(boolean isAlive, Age age, int grassQuantity) {
        return switch (grassQuantity) {
            case 1:
                yield isAlive ? switch (age) {
                    case Child -> grass1WithChildRabbitCellImage;
                    case Adult -> grass1WithAdultRabbitCellImage;
                    case Senior -> grass1WithSeniorRabbitCellImage;
                } : grass1WithDeadRabbitCellImage;
            case 2:
                yield isAlive ? switch (age) {
                    case Child -> grass2WithChildRabbitCellImage;
                    case Adult -> grass2WithAdultRabbitCellImage;
                    case Senior -> grass2WithSeniorRabbitCellImage;
                } : grass2WithDeadRabbitCellImage;
            case 3:
                yield isAlive ? switch (age) {
                    case Child -> grass3WithChildRabbitCellImage;
                    case Adult -> grass3WithAdultRabbitCellImage;
                    case Senior -> grass3WithSeniorRabbitCellImage;
                } : grass3WithDeadRabbitCellImage;
            case 4:
                yield isAlive ? switch (age) {
                    case Child -> grass4WithChildRabbitCellImage;
                    case Adult -> grass4WithAdultRabbitCellImage;
                    case Senior -> grass4WithSeniorRabbitCellImage;
                } : grass4WithDeadRabbitCellImage;
            case 5:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation5WithChildRabbitCellImage;
                    case Adult -> thickVegetation5WithAdultRabbitCellImage;
                    case Senior -> thickVegetation5WithSeniorRabbitCellImage;
                } : thickVegetation5WithDeadRabbitCellImage;
            case 6:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation6WithChildRabbitCellImage;
                    case Adult -> thickVegetation6WithAdultRabbitCellImage;
                    case Senior -> thickVegetation6WithSeniorRabbitCellImage;
                } : thickVegetation6WithDeadRabbitCellImage;
            case 7:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation7WithChildRabbitCellImage;
                    case Adult -> thickVegetation7WithAdultRabbitCellImage;
                    case Senior -> thickVegetation7WithSeniorRabbitCellImage;
                } : thickVegetation7WithDeadRabbitCellImage;
            case 8:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation8WithChildRabbitCellImage;
                    case Adult -> thickVegetation8WithAdultRabbitCellImage;
                    case Senior -> thickVegetation8WithSeniorRabbitCellImage;
                } : thickVegetation8WithDeadRabbitCellImage;
            case 9:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation9WithChildRabbitCellImage;
                    case Adult -> thickVegetation9WithAdultRabbitCellImage;
                    case Senior -> thickVegetation9WithSeniorRabbitCellImage;
                } : thickVegetation9WithDeadRabbitCellImage;
            case 10:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation10WithChildRabbitCellImage;
                    case Adult -> thickVegetation10WithAdultRabbitCellImage;
                    case Senior -> thickVegetation10WithSeniorRabbitCellImage;
                } : thickVegetation10WithDeadRabbitCellImage;
            default:
                yield isAlive ? switch (age) {
                    case Child -> emptyWithChildRabbitCellImage;
                    case Adult -> emptyWithAdultRabbitCellImage;
                    case Senior -> emptyWithSeniorRabbitCellImage;
                } : emptyWithDeadRabbitCellImage;
        };
    }

    public Image getFoxCellImage(boolean isAlive, Age age, int grassQuantity) {
        return switch (grassQuantity) {
            case 1:
                yield isAlive ? switch (age) {
                    case Child -> grass1WithChildFoxCellImage;
                    case Adult -> grass1WithAdultFoxCellImage;
                    case Senior -> grass1WithSeniorFoxCellImage;
                } : grass1WithDeadFoxCellImage;
            case 2:
                yield isAlive ? switch (age) {
                    case Child -> grass2WithChildFoxCellImage;
                    case Adult -> grass2WithAdultFoxCellImage;
                    case Senior -> grass2WithSeniorFoxCellImage;
                } : grass2WithDeadFoxCellImage;
            case 3:
                yield isAlive ? switch (age) {
                    case Child -> grass3WithChildFoxCellImage;
                    case Adult -> grass3WithAdultFoxCellImage;
                    case Senior -> grass3WithSeniorFoxCellImage;
                } : grass3WithDeadFoxCellImage;
            case 4:
                yield isAlive ? switch (age) {
                    case Child -> grass4WithChildFoxCellImage;
                    case Adult -> grass4WithAdultFoxCellImage;
                    case Senior -> grass4WithSeniorFoxCellImage;
                } : grass4WithDeadFoxCellImage;
            case 5:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation5WithChildFoxCellImage;
                    case Adult -> thickVegetation5WithAdultFoxCellImage;
                    case Senior -> thickVegetation5WithSeniorFoxCellImage;
                } : thickVegetation5WithDeadFoxCellImage;
            case 6:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation6WithChildFoxCellImage;
                    case Adult -> thickVegetation6WithAdultFoxCellImage;
                    case Senior -> thickVegetation6WithSeniorFoxCellImage;
                } : thickVegetation6WithDeadFoxCellImage;
            case 7:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation7WithChildFoxCellImage;
                    case Adult -> thickVegetation7WithAdultFoxCellImage;
                    case Senior -> thickVegetation7WithSeniorFoxCellImage;
                } : thickVegetation7WithDeadFoxCellImage;
            case 8:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation8WithChildFoxCellImage;
                    case Adult -> thickVegetation8WithAdultFoxCellImage;
                    case Senior -> thickVegetation8WithSeniorFoxCellImage;
                } : thickVegetation8WithDeadFoxCellImage;
            case 9:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation9WithChildFoxCellImage;
                    case Adult -> thickVegetation9WithAdultFoxCellImage;
                    case Senior -> thickVegetation9WithSeniorFoxCellImage;
                } : thickVegetation9WithDeadFoxCellImage;
            case 10:
                yield isAlive ? switch (age) {
                    case Child -> thickVegetation10WithChildFoxCellImage;
                    case Adult -> thickVegetation10WithAdultFoxCellImage;
                    case Senior -> thickVegetation10WithSeniorFoxCellImage;
                } : thickVegetation10WithDeadFoxCellImage;
            default:
                yield isAlive ? switch (age) {
                    case Child -> emptyWithChildFoxCellImage;
                    case Adult -> emptyWithAdultFoxCellImage;
                    case Senior -> emptyWithSeniorFoxCellImage;
                } : emptyWithDeadFoxCellImage;
        };
    }

    public Image getGrassCellImageViewBasedOnQuantity(int quantity) {
        return switch (quantity) {
            case 1 -> grass1CellImage;
            case 2 -> grass2CellImage;
            case 3 -> grass3CellImage;
            case 4 -> grass4CellImage;
            case 5 -> thickVegetation5CellImage;
            case 6 -> thickVegetation6CellImage;
            case 7 -> thickVegetation7CellImage;
            case 8 -> thickVegetation8CellImage;
            case 9 -> thickVegetation9CellImage;
            case 10 -> thickVegetation10CellImage;
            default -> emptyCellImage;
        };
    }

}
