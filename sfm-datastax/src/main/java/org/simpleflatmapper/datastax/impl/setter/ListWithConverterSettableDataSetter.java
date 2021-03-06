package org.simpleflatmapper.datastax.impl.setter;

import com.datastax.driver.core.SettableByIndexData;
import org.simpleflatmapper.reflect.Setter;
import org.simpleflatmapper.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ListWithConverterSettableDataSetter<I, O> implements Setter<SettableByIndexData<?>, List<I>> {
    private final int index;
    private final Converter<I, O> converter;

    public ListWithConverterSettableDataSetter(int index, Converter<I, O> converter) {
        this.index = index;
        this.converter = converter;
    }

    @Override
    public void set(SettableByIndexData<?> target, List<I> value) throws Exception {
        if (value == null) {
            target.setToNull(index);
        } else {
            List<O> list = new ArrayList<O>(value.size());
            for(int i = 0; i < value.size(); i++) {
                list.add(converter.convert(value.get(i)));
            }
            target.setList(index, list);
        }
    }
}
