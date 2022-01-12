package Backend.Repo;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@WritingConverter
public class StringToDocumentConverter implements Converter<String, Document> {

    @Override
    public Document convert(@Nullable String string) {
        if (string == null) return null;

        Document document = new Document();
        document.put("Test", string);
        return document;
    }
}
