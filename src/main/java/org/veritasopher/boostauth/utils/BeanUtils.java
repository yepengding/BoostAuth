package org.veritasopher.boostauth.utils;

import com.google.gson.Gson;
import org.veritasopher.boostauth.core.exception.InternalException;

/**
 * Bean Utils
 *
 * @author Yepeng Ding
 */
public class BeanUtils {

    /**
     * Copy properties from a bean to another
     *
     * @param source          source object
     * @param destinationType destination object type
     * @return a new object::destinationType
     */
    public static <E, T> T copyBean(E source, Class<T> destinationType) {

        if (source != null) {
            try {
                Gson gson = new Gson();
                String copyData = gson.toJson(source);
                return gson.fromJson(copyData, destinationType);

            } catch (Exception e) {
                throw new InternalException("unable to parse; source object may not have the same properties as destination.");
            }
        } else {
            throw new InternalException("unable to parse the object that is NULL");
        }
    }

}
