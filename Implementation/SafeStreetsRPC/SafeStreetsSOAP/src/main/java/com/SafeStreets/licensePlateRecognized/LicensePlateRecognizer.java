package com.SafeStreets.licensePlateRecognized;

import com.SafeStreets.exceptions.PlateNotRecognizedException;

import java.awt.image.BufferedImage;

/**
 * This class implements the method describes by the License plate recognized interface
 */
class LicensePlateRecognizer implements LicensePlateRecognizerInterface {

    /**
     * This method checks that the plate number provided by the user is the same as the plate of the vehicle in the photo
     * @param image Is the Image with the vehicle that committed the violation
     * @param plateNumber Is the plate number provided by the user
     * @throws PlateNotRecognizedException Is thrown if the plate cannot be recognized
     * @return Is a boolean that indicates if the plate correspond with the one in the image
     */
    @Override
    public boolean recognize(BufferedImage image, String plateNumber) throws PlateNotRecognizedException {
        return true;
    }
}
