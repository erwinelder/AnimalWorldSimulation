package simulation.animal_simulation.animals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    static Collection<Arguments> provideDataForTryToDecreaseSatietyTest() {
        return List.of(
            Arguments.of(10, 10, 5, 4, true),
            Arguments.of(10, 9, 5, 5, true),
            Arguments.of(10, 10, 0, 0, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTryToDecreaseSatietyTest")
    void tryToDecreaseSatiety(
        int stepsBeforeSatietyDecrease,
        int stepsAfterSatietyDecrease,
        int satiety,
        int expectedSatiety,
        boolean expectedIsAlive
    ) {
        Animal rabbit = new Rabbit(Sex.Male, Age.Adult);
        rabbit.stepsBeforeSatietyDecrease = stepsBeforeSatietyDecrease;
        rabbit.stepsAfterSatietyDecrease = stepsAfterSatietyDecrease;
        rabbit.satiety = satiety;

        rabbit.tryToDecreaseSatiety();

        assertEquals(expectedSatiety, rabbit.satiety);
        assertEquals(expectedIsAlive, rabbit.isAlive());
    }




    static Collection<Arguments> provideDataForTryToGrowTest() {
        return List.of(
            Arguments.of(10, 10, Age.Child, Age.Adult, true),
            Arguments.of(10, 9, Age.Adult, Age.Adult, true),
            Arguments.of(10, 10, Age.Senior, Age.Senior, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTryToGrowTest")
    void tryToGrow(
        int stepsBeforeGrow,
        int stepsAfterGrow,
        Age age,
        Age expectedAge,
        boolean expectedIsAlive
    ) {
        Animal rabbit = new Rabbit(Sex.Male, Age.Adult);
        rabbit.stepsBeforeGrow = stepsBeforeGrow;
        rabbit.stepsAfterGrow = stepsAfterGrow;
        rabbit.age = age;

        rabbit.tryToGrow();

        assertEquals(expectedAge, rabbit.age);
        assertEquals(expectedIsAlive, rabbit.isAlive());
    }
}