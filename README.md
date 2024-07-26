# Вторая домашняя работа - сделать свою реализацию [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html).

## Установка:
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'ru.furestry:SevlerArrayList:1.0.1'
}
```

### Maven

```xml
<dependency>
    <groupId>ru.furestry</groupId>
    <artifactId>SevlerArrayList</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Использование

### В классе реализованы методы:

```java
int getSize();

boolean isEmpty();

boolean contains(Object o);

Object[] toArray();

T[] toArray(T[] array);

boolean add(E e);

boolean add(int index, E e);

boolean addAll(Collection<? extends E> collection);

boolean remove(Object o);

void clear();

E get(int index);

E getFirst();

E set(int index, E e);

int indexOf(Object o);

int lastIndexOf(Object o);

void sort();

Iterator<E> iterator();

Object clone(); //только для объектов типа SevlerArrayList
```

Сортировка реализована по [алгоритму быстрой сортировки](https://ru.wikipedia.org/wiki/%D0%91%D1%8B%D1%81%D1%82%D1%80%D0%B0%D1%8F_%D1%81%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0)
