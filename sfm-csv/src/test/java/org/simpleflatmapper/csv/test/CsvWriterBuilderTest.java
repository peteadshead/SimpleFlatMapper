package org.simpleflatmapper.csv.test;

import org.junit.Test;
import org.simpleflatmapper.csv.CsvColumnKey;
import org.simpleflatmapper.csv.CsvWriterBuilder;
import org.simpleflatmapper.test.beans.DbObject;
import org.simpleflatmapper.map.Mapper;
import org.simpleflatmapper.map.property.DateFormatProperty;
import org.simpleflatmapper.map.property.EnumOrdinalFormatProperty;
import org.simpleflatmapper.map.property.FieldMapperColumnDefinition;
import org.simpleflatmapper.map.MapperConfig;

import static org.junit.Assert.*;

public class CsvWriterBuilderTest {

    @Test
    public void testWriteCsvOnDbObject() throws Exception {
        MapperConfig<CsvColumnKey,FieldMapperColumnDefinition<CsvColumnKey>> config =
                MapperConfig.<CsvColumnKey>fieldMapperConfig();
        CsvWriterBuilder<DbObject> builder = CsvWriterBuilder.newBuilder(DbObject.class);

        Mapper<DbObject, Appendable> mapper =
                builder.addColumn("id")
                        .addColumn("name")
                        .addColumn("email")
                        .addColumn("creation_time", new DateFormatProperty("dd/MM/yyyy HH:mm:ss"))
                        .addColumn("type_ordinal", new EnumOrdinalFormatProperty())
                        .addColumn("type_name")
                        .mapper();

        DbObject dbObject = CsvWriterTest.newDbObject();

        assertEquals("13,name,email,06/06/2015 17:46:23,1,type3\r\n", mapper.map(dbObject).toString());

        dbObject.setEmail("email,e\" ");
        assertEquals("13,name,\"email,e\"\" \",06/06/2015 17:46:23,1,type3\r\n", mapper.map(dbObject).toString());

    }

}