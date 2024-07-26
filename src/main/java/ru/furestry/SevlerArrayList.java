package ru.furestry;

import java.util.*;

public class SevlerArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    @java.io.Serial
    private static final long serialVersionUID = 8683452581122892189L;
    transient Object[] elementData;
    private int size;

    public SevlerArrayList() {
        this.elementData = new Object[0];
    }

    public SevlerArrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.elementData = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal initialCapacity: " + initialCapacity);
        }
    }

    public SevlerArrayList(Collection<E> initialCollection) {
        this.elementData = initialCollection.toArray();
        this.size = initialCollection.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new SevlerItr();
    }

    @Override
    public Object[] toArray() {
        return elementData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return (T[]) elementData;
    }

    private void grow(int size) {
        if (elementData.length <= size) {
            int newCapacity = elementData.length * 2 + 1;
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    @Override
    public boolean add(E e) {
        if (elementData.length == size) {
            grow(size);
        }

        elementData[size] = e;

        size += 1;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elementData[i])) {
                elementData[i] = null;

                if (i != size - 1) {
                    Object[] cleanArray = new Object[size];

                    for (int j = 0, c = 0; j < size; j++) {
                        if (elementData[j] != null) {
                            cleanArray[c] = elementData[j];

                            c += 1;
                        }
                    }

                    elementData = cleanArray;
                }

                size -= 1;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int emptyCount = size - elementData.length;
        Object[] newArray;

        if (emptyCount < c.size()) {
            newArray = new Object[c.size() - emptyCount + elementData.length];
        } else {
            newArray = new Object[size];
        }

        c.forEach(this::add);

        grow(newArray.length);

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        Object[] copy = elementData;

        for (int i = size - 1; i >= 0; i--) {
            copy[i] = null;
        }

        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    public E getFirst() {
        return (E) elementData[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index);

        Object old = elementData[index];

        elementData[index] = element;

        return (E) old;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);

        if (elementData.length == size) {
            grow(size);
        }

        Object[] copyArray = elementData.clone();

        for (int i = index; i < size; i++) {
            elementData[i + 1] = copyArray[i];
        }

        elementData[index] = element;

        size += 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);

        Object old = elementData[index];

        if (index == (size - 1)) {
            elementData[index] = null;
            size -= 1;
        } else {
            remove(old);
        }

        return (E) old;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;

        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    index = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                   index = i;
                   break;
                }
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new SevlerListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new SevlerListItr(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("fromIndex < 0. fromIndex = " + fromIndex);
        } else if (toIndex > size) {
            throw new ArrayIndexOutOfBoundsException("toIndex > size. toIndex = " + toIndex + ", size = " + size);
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex > toIndex. fromIndex = " + fromIndex + ", toIndex = " + toIndex);
        }

        Object[] subList = new Object[toIndex - fromIndex];

        for (int f = fromIndex, s = 0; f <= toIndex; f++, s++) {
            subList[s] = elementData[s];
        }

        return Arrays.asList((E[]) subList);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        if (isEmpty()) {
            return;
        }

        quickSort(0, size - 1, (Comparator<Object>) c);
    }

    private void quickSort(int low, int high, Comparator<Object> comp) {
        if (low < high) {
            int[] partition = partition(low, high, comp);
            int left = partition[0];
            int right = partition[1];

            if (low < right) {
                quickSort(low, right, comp);
            }

            if (left < high) {
                quickSort(left, high, comp);
            }
        }
    }

    private int[] partition(int low, int high, Comparator<Object> comp) {
        int[] partitions = new int[2];
        int pivotIndex = (low + high) / 2;
        Object pivot = elementData[pivotIndex];
        int i = low;
        int j = high;

        while (i <= j) {
            while (comp.compare(elementData[i], pivot) < 0) {
                i++;
            }

            while (comp.compare(elementData[j], pivot) > 0) {
                j -= 1;
            }

            if (i <= j) {
                swap(i, j);
                i += 1;
                j -= 1;
            }
        }

        partitions[0] = i;
        partitions[1] = j;

        return partitions;
    }

    private void swap(int i, int j) {
        Object temp = elementData[i];
        elementData[i] = elementData[j];
        elementData[j] = temp;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SevlerArrayList<E> clone() {
        try {
            return (SevlerArrayList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index less than 0. index = " + index);
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index greater than or equals size. index = " + index);
        }
    }

    private class SevlerItr implements Iterator<E> {
        int cursor;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= size) {
                return null;
            }

            cursor = i + 1;
            lastReturned = i;

            return (E) elementData[lastReturned];
        }
    }

    private class SevlerListItr extends SevlerItr implements ListIterator<E> {
        public SevlerListItr(int index) {
            super();

            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }
}
