package krainet.test.service.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ValidationConstants {
    public static final String IMAGE_PNG_VALUE = "image/png";
    public static final String IMAGE_JPG_VALUE = "image/jpg";
    public static final Set<String> TYPES_OF_IMAGE = Set.of(IMAGE_JPG_VALUE, IMAGE_PNG_VALUE);
    public static final String DOCUMENT_PDF_VALUE = "application/pdf";

}
