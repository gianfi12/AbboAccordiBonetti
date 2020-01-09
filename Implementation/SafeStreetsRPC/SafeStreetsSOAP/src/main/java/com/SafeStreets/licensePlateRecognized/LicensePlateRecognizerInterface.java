package com.SafeStreets.licensePlateRecognized;

import com.SafeStreets.exceptions.PlateNotRecognizedException;

import java.awt.image.BufferedImage;

/**
 * This class represent the License plate recognizer that is used to verify the plate number provided by the users in their reports
 */
public interface LicensePlateRecognizerInterface {

    /**
     * This method returns an instance of an object that implements the methods exposed by this interface
     * @return Is an instance of an object that provide this interface
     */
    static LicensePlateRecognizerInterface getInstance() { return new LicensePlateRecognizer();}

    /**
     * This method checks that the plate number provided by the user is the same as the plate of the vehicle in the photo
     * @param image Is the Image with the vehicle that committed the violation
     * @param plateNumber Is the plate number provided by the user
     * @throws PlateNotRecognizedException Is thrown if the plate cannot be recognized
     * @return Is a boolean that indicates if the plate correspond with the one in the image
     */
    boolean recognize(BufferedImage image, String plateNumber) throws PlateNotRecognizedException;
}
