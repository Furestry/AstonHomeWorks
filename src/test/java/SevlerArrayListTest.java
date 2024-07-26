import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.furestry.SevlerArrayList;

import java.util.*;

public class SevlerArrayListTest {
    private static final int ARRAY_CAPACITY = 100_000;
    private static final int REMOVE_CAPACITY = ARRAY_CAPACITY / 100;
    private static final int ADD_CAPACITY = ARRAY_CAPACITY / 50;
    private static final int LIMIT = 50_000;

    private static final List<String> ourList = new SevlerArrayList<>();
    private static final List<String> validList = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        Random random = new Random();

        for (int i = 0; i < ARRAY_CAPACITY; i++) {
            int randInt = random.nextInt(-LIMIT, LIMIT);

            ourList.add("STR" + randInt);
            validList.add("STR" + randInt);
        }
    }

    @Test
    public void ourListEqualsValidList() {
        Assertions.assertEquals(validList, ourList);
    }

    @Test
    public void testRemoveByObject() {
        Random random = new Random();
        int startObject = random.nextInt(-LIMIT/2, LIMIT/2);

        while (!validList.contains("STR" + startObject)) {
            startObject = random.nextInt(-LIMIT/2, LIMIT/2);
        }

        for (int i = 0; i <= REMOVE_CAPACITY; i ++) {
            ourList.remove("STR" + (startObject + i));
            validList.remove("STR" + (startObject + i));
        }

        Assertions.assertEquals(validList.size(), ourList.size());
        Assertions.assertEquals(validList, ourList);
    }

    @Test
    public void testRemoveByIndex() {
        Random random = new Random();
        List<Integer> removeIndexes = new ArrayList<>();

        for (int i = 0; i < REMOVE_CAPACITY; i++) {
            int randInt = random.nextInt(0, validList.size() / 10);

            removeIndexes.add(randInt);
        }

        removeIndexes.forEach(e -> {
            ourList.remove(e.intValue());
            validList.remove(e.intValue());
        });

        Assertions.assertEquals(validList, ourList);
    }

    @Test
    public void testAddByIndex() {
        Random random = new Random();

        for (int i = 0; i < ADD_CAPACITY; i++) {
            int randIndex = random.nextInt(0, ARRAY_CAPACITY / 2);

            ourList.add(randIndex, "STR" + randIndex);
            validList.add(randIndex, "STR" + randIndex);
        }

        Assertions.assertEquals(validList, ourList);
        Assertions.assertEquals(validList.size(), ourList.size());
    }

    @Test
    public void testAdd() {
        for (int i = 0; i < ADD_CAPACITY; i++) {
            ourList.add("STR" + (i * 10));
            validList.add("STR" + (i * 10));
        }

        Assertions.assertEquals(validList, ourList);
        Assertions.assertEquals(validList.size(), ourList.size());
    }

    @Test
    public void testAddAll() {
        List<String> addStrings = new ArrayList<>();

        for (int i = 0; i < ADD_CAPACITY; i++) {
            addStrings.add("STR" + (i * 15));
        }

        ourList.addAll(addStrings);
        validList.addAll(addStrings);

        Assertions.assertEquals(validList, ourList);
        Assertions.assertEquals(validList.size(), ourList.size());
    }

    @Test
    public void testEmpty() {
        Assertions.assertEquals(validList.isEmpty(), ourList.isEmpty());
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(validList.getFirst(), ourList.getFirst());

        for (int i = 0; i < REMOVE_CAPACITY; i ++) {
            Assertions.assertEquals(validList.get(i), ourList.get(i));
        }
    }

    @Test
    public void testSort() {
        ourList.sort(Comparator.naturalOrder());
        validList.sort(Comparator.naturalOrder());

        Assertions.assertEquals(validList, ourList);

        ourList.sort(Comparator.reverseOrder());
        validList.sort(Comparator.reverseOrder());

        Assertions.assertEquals(validList, ourList);
    }

    @Test
    public void testClear() {
        ourList.clear();
        validList.clear();

        Assertions.assertEquals(validList, ourList);
        Assertions.assertTrue(ourList.isEmpty());
    }

    @Test
    public void testContains() {
        List<String> addStrings = new ArrayList<>();

        for (int i = 0; i < ADD_CAPACITY; i++) {
            addStrings.add("STR" + (i * 15));
        }

        addStrings.forEach(e -> Assertions.assertEquals(validList.contains(e), ourList.contains(e)));
    }

    @Test
    public void testSet() {
        for (int i = 0; i < ADD_CAPACITY; i++) {
            ourList.set(i * 13, "STR" + (i * 10));
            validList.set(i * 13, "STR" + (i * 10));
        }

        Assertions.assertEquals(validList, ourList);
        Assertions.assertEquals(validList.size(), ourList.size());
    }

    @Test
    public void testIndexOf() {
        String notContains = "STRSTR";
        String contains = validList.get(4);

        Assertions.assertEquals(validList.indexOf(contains), ourList.indexOf(contains));
        Assertions.assertEquals(validList.indexOf(notContains), ourList.indexOf(notContains));
    }

    @Test
    public void testLastIndexOf() {
        String notContains = "STRSTR";
        String contains = validList.get(2);

        Assertions.assertEquals(validList.lastIndexOf(contains), ourList.lastIndexOf(contains));
        Assertions.assertEquals(validList.lastIndexOf(notContains), ourList.lastIndexOf(notContains));
    }

    @Test
    public void testSubList() {
        int start = validList.size() / 1000;
        int end = validList.size() / 1000 * 2;
        List<String> ourSubList = ourList.subList(start, end);
        List<String> validSubList = validList.subList(start, end);

        Assertions.assertEquals(validSubList, ourSubList);
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = ourList.iterator();
        int limit = ARRAY_CAPACITY / 200;
        int count = 0;

        while (count < limit) {
            Assertions.assertTrue(iterator.hasNext());
            Assertions.assertEquals(ourList.get(count), iterator.next());

            count++;
        }
    }
}
